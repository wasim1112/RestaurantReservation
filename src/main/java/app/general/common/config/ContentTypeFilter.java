package app.general.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import app.general.common.request.MutableHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ContentTypeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MutableHttpServletRequest requestWithContentType = new MutableHttpServletRequest(request);
        if(request.getRequestURI().contains("/api")){
            requestWithContentType.putHeader("Content-Type", "application/json");
            requestWithContentType.putHeader("Accept", "application/json");
        }
        filterChain.doFilter(requestWithContentType, response);
    }

}
