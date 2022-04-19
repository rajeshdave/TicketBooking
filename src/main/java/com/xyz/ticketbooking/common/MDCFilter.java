package com.xyz.ticketbooking.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

/**
 * Add unique Request id in MDC Context for each of the REST request.
 * This will get printed in each of the log statement automatically.
 * <p>
 * Also send back this unique Request id in response header which can be used later for troubleshooting any request.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Order(2)
@Slf4j
public class MDCFilter extends OncePerRequestFilter {
    private String responseHeader = "Logging-Token";
    private String mdcTokenKey = "MDCFilter.UUID";

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            Long startTime = Instant.now().toEpochMilli();
            final String token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            MDC.put(mdcTokenKey, token);
            response.addHeader(responseHeader, token);
            chain.doFilter(request, response);
            Long endTime = Instant.now().toEpochMilli();
            log.info(
                    "Response status code : {} and Elapsed Time : {} milliseconds",
                    response.getStatus(),
                    (endTime - startTime));
        } finally {
            MDC.clear();
        }
    }

}
