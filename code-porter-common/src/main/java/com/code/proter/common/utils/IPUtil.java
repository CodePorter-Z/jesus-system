package com.code.proter.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * IP工具类
 * 
 * 
 */
public class IPUtil {

	/**
	 * 获取登录用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) throws SocketException {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = getRealIp();
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}


    /**
     * @return 获取本机IP
     * @throws SocketException
     */
    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces =
                NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(":")) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(":")) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    /**
	 * 通过IP获取地址(需要联网，调用淘宝的IP库)
	 * 
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) throws IOException {
	    String localHost = getRealIp();
		if (ip.equals(localHost)) {
			ip = localHost;
		}
		String info = "";
        URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
        HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
        htpcon.setRequestMethod("GET");
        htpcon.setDoOutput(true);
        htpcon.setDoInput(true);
        htpcon.setUseCaches(false);

        InputStream in = htpcon.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuffer temp = new StringBuffer();
        String line = bufferedReader.readLine();
        while (line != null) {
            temp.append(line).append("\r\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        JSONObject obj = (JSONObject) JSON.parse(temp.toString());
        if (obj.getIntValue("code") == 0) {
            JSONObject data = obj.getJSONObject("data");
            info += data.getString("country") + " ";
            info += data.getString("region") + " ";
            info += data.getString("city") + " ";
            info += data.getString("isp");
        }
		return info;
	}

}