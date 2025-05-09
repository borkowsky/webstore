package net.rewerk.webstore.configuration.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class RoleUserRequestMatcher extends net.rewerk.webstore.configuration.matcher.RequestMatcher implements RequestMatcher {
    public boolean matches(HttpServletRequest request) {
        Map<HttpMethod, List<String>> mapping = new ConcurrentHashMap<>();
        List<String> allowedAllURIs = List.of(
                "/me/**",
                "/favorites/**",
                "/basket/**"
        );
        List<String> allowedGetURIs = List.of(
                "/orders/**",
                "/payments/**"
        );
        List<String> allowedPostURIs = List.of(
                "/orders/**",
                "/payments/**"
        );
        mapping.put(HttpMethod.GET, Stream.concat(
                allowedAllURIs.stream(),
                allowedGetURIs.stream()
        ).toList());
        mapping.put(HttpMethod.POST, Stream.concat(
                allowedAllURIs.stream(),
                allowedPostURIs.stream()
        ).toList());
        mapping.put(HttpMethod.PATCH, allowedAllURIs);
        mapping.put(HttpMethod.DELETE, allowedAllURIs);
        return super.process(request, mapping);
    }
}
