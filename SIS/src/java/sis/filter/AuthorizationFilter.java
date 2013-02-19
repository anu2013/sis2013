/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.filter;

import sis.model.Users;
import sis.controller.UserController;
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

/**
 * This filter protects all pages within the admin, teacher and student folders
 * If a xhtml or jsp is requested then the page first checks to see if the user is
 * authenticated and if not redirects them to the home page (login page)
 * 
 *
 * @author Anupama Karumudi
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/faces/admin/*", "/admin/*", "/faces/teacher/*", "/teacher/*", "/faces/student/*", "/student/*"})
public class AuthorizationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        UserController userController = (UserController) req.getSession().getAttribute("userController");
        boolean isAutherized = true;
        
        if (userController == null || !userController.isLoggedIn()) {
            isAutherized = false;
        } else {
            String uri = req.getRequestURI();
            if(uri != null){
                Users loggedinUser = userController.getUser();
                if(null != loggedinUser) {
                    if( (uri.indexOf("/admin/") >= 0 && !loggedinUser.getIsAdmin()) 
                            || (uri.indexOf("/teacher/") >= 0 && !loggedinUser.getIsTeacher())
                            || (uri.indexOf("/student/") >= 0 && !loggedinUser.getIsStudent())
                            ){
                        isAutherized = false;
                    }
                }
            }
        }
        if(isAutherized){
            chain.doFilter(request, response);
        }else{
            res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }
    
}
