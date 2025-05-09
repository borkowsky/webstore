package net.rewerk.webstore.configuration.advice;

import lombok.NonNull;
import net.rewerk.webstore.configuration.pointer.EntityViewLevelMapping;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.model.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SecurityJsonViewRestControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(
            @NonNull MappingJacksonValue bodyContainer,
            @NonNull MediaType contentType,
            @NonNull MethodParameter returnType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
                Collection<? extends GrantedAuthority> authorities
                        = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
                List<Class<?>> jsonViews = authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(User.Role::valueOf)
                        .map(EntityViewLevelMapping.MAPPING::get)
                        .collect(Collectors.toList());
                if (jsonViews.size() == 1) {
                    bodyContainer.setSerializationView(jsonViews.getFirst());
                    return;
                }
                throw new IllegalArgumentException("Ambiguous @JsonView declaration for roles "
                        + authorities.stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
            } else {
                bodyContainer.setSerializationView(ViewLevel.RoleAnonymous.class);
            }
        }
    }
}