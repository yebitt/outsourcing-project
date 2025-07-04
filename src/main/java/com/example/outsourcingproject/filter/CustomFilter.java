package com.example.outsourcingproject.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // 요청 전 처리

        chain.doFilter(request, response); // 다음 필터/서블릿으로 요청 전달

        // 응답 후 처리
    }
}
