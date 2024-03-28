package com.furreverhome.Furrever_Home.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    /**
     * Filters incoming requests to set appropriate CORS headers.
     * @param servletRequest The servlet request.
     * @param servletResponse The servlet response.
     * @param filterChain The filter chain to proceed with.
     * @throws IOException If an I/O error occurs while processing the request.
     * @throws ServletException If an error occurs while processing the request.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<String, String > map = new HashMap<>();
        String originHeader = request.getHeader("origin");
        // Set CORS headers in the response
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST , GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }

    /**
     * Initializes the filter.
     * @param filterConfig The filter configuration.
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Destroys the filter.
     */
    @Override
    public  void destroy() {
    }
}
