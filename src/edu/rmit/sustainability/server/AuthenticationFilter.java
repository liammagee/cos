package edu.rmit.sustainability.server;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 20/05/11
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationFilter implements Filter {
    private FilterConfig config = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = config;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // pass the request on
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        this.config = null;
    }
}
