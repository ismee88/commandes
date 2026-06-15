package com.polytech.commandes.filter;

import com.polytech.commandes.entity.RequestLog;
import com.polytech.commandes.repository.RequestLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestLoggerFilter
        extends OncePerRequestFilter {

    private final RequestLogRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        LocalDateTime debut = LocalDateTime.now();

        RequestLog log = new RequestLog();

        log.setUri(request.getRequestURI());
        log.setMethod(request.getMethod());
        log.setIp(request.getRemoteAddr());
        log.setDateRequete(debut);
        log.setUserAgent(
                request.getHeader("User-Agent")
        );
        log.setQueryString(
                request.getQueryString()
        );

        repository.save(log);

        filterChain.doFilter(request, response);

        log.setStatus(response.getStatus());

        Duration duree =
                Duration.between(
                        debut,
                        LocalDateTime.now()
                );

        log.setDuree(
                duree.toMillis()
        );

        repository.save(log);
    }
}
