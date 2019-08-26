package com.jesus.user.web.controller.resource.controller;

import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.user.domain.role.Role;
import com.jesus.user.modules.resource.service.IResourceService;
import com.jesus.user.modules.role.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ResourceController
 *
 * @author Jesus[zhoubing_ssr@163.com]
 * at      2019/8/2 8:51
 */
@RestController
@RequestMapping("/resource/v1/web")
@Slf4j
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/generateRoutes")
    public Response generateRoutes(Long id){

        try {
            if(CommonUtil.isNotNull(id)){
                Role role = roleService.findRoleById(id);
                if(CommonUtil.isNotNull(role)
                        && !CommonUtil.isEmpty(role.getResourceIds())){

                    //获取用户权限列表
                    List<String> resourceList = resourceService.getResourceAll(role.getResourceIds());

                    if(CommonUtil.isNotNull(resourceList))
                        return Response.ok(resourceList);
                }
            }
        } catch (Exception e) {
            log.error("查询用户权限异常:{}",e.getMessage());
            e.printStackTrace();
            return Response.error("链接异常 , 联系管理员或重新尝试");
        }

        return Response.fail("用户权限过低");
    }
}
