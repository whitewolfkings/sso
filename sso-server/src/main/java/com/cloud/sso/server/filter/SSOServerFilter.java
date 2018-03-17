package com.cloud.sso.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.sso.server.JVMCache;
/**
 * sso-server接受请求
 * @author mmk
 *
 */
public class SSOServerFilter implements Filter {
     //初始化服务
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}
     //由容器每次请求/响应对通过链由于客户端请求一个资源的链
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) servletRequest;
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        String service = request.getParameter("service");
	        //String ticket = request.getParameter("ticket");
	        Cookie[] cookies = request.getCookies();
	        String username = "";
	        
	      //判断用户是否已经登陆认证中心
	        if (null != cookies) {
	            for (Cookie cookie : cookies) { 
	                if ("sso".equals(cookie.getName())) {
	                    username = cookie.getValue();              
	                    break;
	                }
	            }
	        }

//	        if (null == service && null != ticket) {
//	            filterChain.doFilter(servletRequest, servletResponse);
//	            return;
//	        }
	        
	      //实现一处登录处处登录
	        if (null != username && !"".equals(username)) {
	            long time = System.currentTimeMillis();
	            //生成认证凭据--ticket
	            String ticket = username + time;
	            JVMCache.TICKET_AND_NAME.put(ticket, username);
	            StringBuilder url = new StringBuilder();
	            url.append(service);
	            
	            if (0 <= service.indexOf("?")) {
	                url.append("&");
	            } else {
	                url.append("?");
	            }
	          //返回给用户一个认证的凭据--ticket
	            url.append("ticket=").append(ticket);
	            response.sendRedirect(url.toString());
	            
	        } else {
	            filterChain.doFilter(servletRequest, servletResponse);
	        }
	    }
		
	
    //销毁服务
	public void destroy() {
	
		
	}

}
