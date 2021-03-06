package com.cloud.sso.server.servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cloud.sso.server.JVMCache;
/**
 * 登录的控制器
 * @author mmk
 *
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -3170191388656385924L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");

        //判断用户名和密码是否正确
        if ("admin".equals(username) && "1111".equals(password)) {
            Cookie cookie = new Cookie("sso", username);
            cookie.setPath("/");
            response.addCookie(cookie);

            long times = System.currentTimeMillis();
            //生成认证凭据--ticket
            String ticket = username + times;
            JVMCache.TICKET_AND_NAME.put(ticket, username);

            if (null != service) {
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
                response.sendRedirect("/sso-server/index.jsp");
            }
            
        } else {
            response.sendRedirect("/sso-server/index.jsp?service=" + service);
        }
    }

}