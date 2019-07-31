package com.jesus.user.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jesus.common.base.redis.service.RedisService;
import com.jesus.common.base.redis.service.impl.IRedisService;
import com.jesus.common.utils.encrypt.EncryptUtil;
import com.jesus.user.shiro.data.CipherFreeHashedCredentialsMatcher;
import com.jesus.user.shiro.data.CustomWebSessionManager;
import com.jesus.common.base.redis.model.RedisOptions;
import com.jesus.user.shiro.filter.KickoutSessionFilter;
import com.jesus.user.shiro.filter.LoginAccountFilter;
//import com.jesus.user.shiro.filter.StatelessAuthcFilter;
import com.jesus.user.shiro.filter.StatelessAuthcFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
@Slf4j
public class ShiroConfiguration {


	@Resource
	private RedisOptions redisOptions;

	/**
	 * 自定义过滤器
	 * @return 过滤器配置
	 */
	@Bean
	public LoginAccountFilter loginAccountFilter() {
		return new LoginAccountFilter();
	}

	/**
	 * Session 失效处理
	 * @return 过滤器配置
	 */
	@Bean
	public StatelessAuthcFilter statelessAuthcFilter() {
		return new StatelessAuthcFilter();
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO(redisManager()));
		return sessionManager;
	}

	@Bean
	public KickoutSessionFilter kickoutSessionFilter(){
		RedisOptions redisOptions = new RedisOptions();
		KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
		//使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
		//这里我们还是用之前shiro使用的ehcache实现的cacheManager()缓存管理
		//也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
		kickoutSessionFilter.setCacheManager(cacheManager(redisManager()));
		//用于根据会话ID，获取会话进行踢出操作的；
		kickoutSessionFilter.setSessionManager(sessionManager());
		//是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
		kickoutSessionFilter.setKickoutAfter(false);
		//同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
		kickoutSessionFilter.setMaxSession(1);
		//被踢出后重定向到的地址；
		kickoutSessionFilter.setKickoutUrl("/jesus/doLogin?kickout=1");
		return kickoutSessionFilter;
	}

	/**
	 * 配置过滤器
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		log.debug("ShiroConfiguration.shiroFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//自定义过滤器
		LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
		filtersMap.put("loginAccount", loginAccountFilter());
		filtersMap.put("statelessAuthcFilter", statelessAuthcFilter());
		filtersMap.put("kickoutSessionFilter", kickoutSessionFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);

		// 拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器,其中的具体代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/user/v1/web/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "loginAccount,statelessAuthcFilter,kickoutSessionFilter,authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/user/v1/web/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;

	}

	@Bean
	public RedisService redisService(){
		return new IRedisService();
	}

	@Bean("shiroRedisManager")
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(redisOptions.getHost());
		redisManager.setPort(redisOptions.getPort());
		redisManager.setTimeout(redisOptions.getTimeout());
		redisManager.setPassword(redisOptions.getPassword());
		return redisManager;
	}

	@Bean("shiroRedisCacheManager")
	public RedisCacheManager cacheManager(RedisManager redisManager) {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager);
		redisCacheManager.setPrincipalIdFieldName("username");
		redisCacheManager.setExpire(1800);
		return redisCacheManager;
	}

	@Bean
	public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager);
		return redisSessionDAO;
	}

	/**
	 * coolie模板对象
	 * 	对应 customWebSessionManager() ---> SimpleCookie
	 * 	涉及到的行为：添加、删除SessionId到Cookie、读取Cookie获得SessionId
	 * @return
	 */
	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		// 提升cookie安全性，防止xss攻击
		cookie.setHttpOnly(true);
		//maxAge=-1表示浏览器关闭时失效此Cookie
		cookie.setMaxAge(-1);
		return cookie;
	}

	/**
	 * session 会话管理器
	 * @param cacheManager 缓存管理器
	 * @param simpleCookie cookie信息
	 * @param sessionDAO   RedisSessionDAO
	 * @return
	 */
	@Bean
	public SessionManager customWebSessionManager(RedisCacheManager cacheManager, SimpleCookie simpleCookie,
												  SessionDAO sessionDAO) {
		CustomWebSessionManager sessionManager = new CustomWebSessionManager();

		//session 过期时间 (毫秒)  默认180000 = 30分钟
		sessionManager.setGlobalSessionTimeout(1800000);
		//删除无效session 默认为true
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setCacheManager(cacheManager);
		// 重定向时去掉SessionId
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		sessionManager.setSessionIdCookie(simpleCookie);
		sessionManager.setSessionDAO(sessionDAO);
		return sessionManager;
	}


	// 自定义限定匹配器
	@Bean
	public CipherFreeHashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
		CipherFreeHashedCredentialsMatcher hashedCredentialsMatcher = new CipherFreeHashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("SHA-1");
		hashedCredentialsMatcher.setHashIterations(1024);
		return hashedCredentialsMatcher;
	}

	@Bean
	public ShiroRealm shiroRealm(CipherFreeHashedCredentialsMatcher matcher, RedisCacheManager cacheManager) {
		ShiroRealm realm = new ShiroRealm();
		realm.setCredentialsMatcher(matcher);
		realm.setCachingEnabled(true);
//		realm.setCachingEnabled(false); //去除缓存 由自定义CacheAspect控制
		realm.setAuthenticationCachingEnabled(true);
		realm.setAuthenticationCacheName("authenticationCache");
		realm.setAuthorizationCachingEnabled(true);
		realm.setAuthorizationCacheName("authorizationCache");

		// CacheManager 必须设置在 setAuthorizationCacheName 之后，不然setAuthorizationCacheName设置值不生效
		realm.setCacheManager(cacheManager);
		return realm;
	}

	/**
	 * cookie管理对象
	 * @return
	 */
	@Bean
	public CookieRememberMeManager cookieRememberMeManager() {
		CookieRememberMeManager manager = new CookieRememberMeManager();

		//设置过期时间
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(-1);
		manager.setCookie(cookie);

		// KeyGenerator keygen = KeyGenerator.getInstance("AES");
		// SecretKey deskey = keygen.generateKey();
		// System.out.println(Base64.encodeToString(deskey.getEncoded()));

		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		byte[] decode = Base64.decode(EncryptUtil.key);
		manager.setCipherKey(decode);
		return manager;
	}

	@Bean
	public SecurityManager securityManager(SessionManager sessionManager, ShiroRealm shiroRealm,
										   RedisCacheManager cacheManager, CookieRememberMeManager rememberMeManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(cacheManager);
		//记住我  功能
		securityManager.setRememberMeManager(rememberMeManager);
		return securityManager;
	}

	@Bean // 交由Spring代理
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	@Bean // Shiro 注解支持
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}



}