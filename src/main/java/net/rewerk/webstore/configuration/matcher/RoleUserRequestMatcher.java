package net.rewerk.webstore.configuration.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleUserRequestMatcher extends net.rewerk.webstore.configuration.matcher.RequestMatcher implements RequestMatcher {
    public boolean matches(HttpServletRequest request) {
        Map<HttpMethod, List<String>> mapping = new ConcurrentHashMap<>();
        List<HttpMethod> allowedHttpMethods = List.of(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.DELETE
        );
        List<String> allowedURIs = List.of(
                "/me/**",
                "/favorites/**",
                "/basket/**"
        );
        for (HttpMethod allowedHttpMethod : allowedHttpMethods) {
            mapping.put(allowedHttpMethod, allowedURIs);
        }
        return super.process(request, mapping);
    }
}
