package net.rewerk.webstore.configuration.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleAnonymousRequestMatcher extends net.rewerk.webstore.configuration.matcher.RequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        Map<HttpMethod, List<String>> mapping = new ConcurrentHashMap<>();
        mapping.put(HttpMethod.GET, List.of(
                "/categories/**",
                "/products/**",
                "/brands/**"
        ));
        return super.process(request, mapping);
    }
}
