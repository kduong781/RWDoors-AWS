/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();

            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute("user") != null)
                    || reqURI.indexOf("/public/") >= 0
                    || reqURI.contains("javax.faces.resource")
                    || reqURI.indexOf("/index.xhtml") >= 0
                    || reqURI.indexOf("/about.xhtml") >= 0
                    || reqURI.indexOf("/contactUs.xhtml") >= 0
                    || reqURI.indexOf("/gallery.xhtml") >= 0) {
                chain.doFilter(request, response);
            } else if (reqURI.indexOf("/welcome.xhtml") >= 0 || 
                    reqURI.indexOf("/changeOrder.xhtml") >= 0 ||
                    reqURI.indexOf("/addClient.xhtml") >= 0 ||
                    reqURI.indexOf("/add_order.xhtml") >= 0 ||
                    reqURI.indexOf("/edit_client.xhtml") >= 0 ||
                    reqURI.indexOf("/edit_order.xhtml") >= 0 ||
                    reqURI.indexOf("/listClients.xhtml") >= 0) {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
            } else {
                //System.out.println("STRING HERE: "+reqt.getContextPath());
                resp.sendRedirect(reqt.getContextPath() + "/index.xhtml");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void destroy() {

    }

}
