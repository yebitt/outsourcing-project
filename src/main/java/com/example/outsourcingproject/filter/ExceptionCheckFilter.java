package com.example.outsourcingproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExceptionCheckFilter implements Filter {

    // 롬복으로 대체 가능
    private static  final Logger log = LoggerFactory.getLogger(ExceptionCheckFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("요청 URI: {}", httpServletRequest.getRequestURI());

        // 다음 필터 호출
        chain.doFilter(request, response);
    }
}
