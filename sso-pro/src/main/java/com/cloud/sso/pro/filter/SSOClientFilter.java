package com.cloud.sso.pro.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * PRO系统发送请求到单点服务器
 * @author mmk
 *
 */
public class SSOClientFilter implements Filter{
     //初始化服务
	public void init(FilterConfig filterConfig) throws ServletException {
	
		
	}
     //由容器每次请求/响应对通过链由于客户端请求一个资源的链
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) servletRequest;
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        HttpSession session = request.getSession();
	        String username = (String) session.getAttribute("username");
	        String ticket = request.getParameter("ticket");
	        String url = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");

	        if (null == username) {
	            if (null != ticket && !"".equals(ticket)) {
	                PostMethod postMethod = new PostMethod("http://localhost:8081/sso-server/ticket");
	                postMethod.addParameter("ticket", ticket);
	                HttpClient httpClient = new HttpClient();
	                try {
	                    httpClient.executeMethod(postMethod);
	                    username = postMethod.getResponseBodyAsString();
	                    postMethod.releaseConnection();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                if (null != username && !"".equals(username)) {
	                    session.setAttribute("username", username);
	                    filterChain.doFilter(request, response);
	                } else {
	                    response.sendRedirect("http://localhost:8081/sso-server/index.jsp?service=" + url);
	                }
	            } else {
	                response.sendRedirect("http://localhost:8081/sso-server/index.jsp?service=" + url);
	            }
	        } else {
	            filterChain.doFilter(request, response);
	        }
	    }

		
	
    //销毁服务
	public void destroy() {
		
		
	}

}
