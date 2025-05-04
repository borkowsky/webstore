package net.rewerk.webstore.configuration.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;
import java.util.Map;

public abstract class RequestMatcher {
    private final String API_VERSION_PREFIX = "/api/v1";

    protected boolean process(HttpServletRequest request, Map<HttpMethod, List<String>> mapping) {
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        String requestURI = request.getRequestURI();
        if (requestURI == null) return false;
        if (!mapping.containsKey(httpMethod)) return false;
        List<String> paths = mapping.get(httpMethod);
        return paths.stream()
                .filter(path -> AntPathRequestMatcher.antMatcher(API_VERSION_PREFIX + path).matches(request))
                .findAny()
                .orElse(null) != null;
    }
}
