package net.rewerk.webstore.configuration;

import jakarta.servlet.DispatcherType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.configuration.matcher.RoleAnonymousRequestMatcher;
import net.rewerk.webstore.configuration.matcher.RoleUserRequestMatcher;
import net.rewerk.webstore.configuration.pointer.EntityViewLevelMapping;
import net.rewerk.webstore.configuration.pointer.ViewLevel;
import net.rewerk.webstore.filter.JWTAuthFilter;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.service.entity.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {
    private final UserService userService;
    private final JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(req -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of(
                            "POST", "GET", "OPTIONS", "DELETE", "HEAD", "PATCH", "TRACE"
                    ));
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(req -> req
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers("/api/v*/auth/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(HttpMethod.HEAD).permitAll()
                                .requestMatchers(new RoleAnonymousRequestMatcher()).permitAll()
                                .requestMatchers(new RoleUserRequestMatcher()).authenticated()
                                .anyRequest().hasAuthority("ADMINISTRATOR")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @RestControllerAdvice
    static class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

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
}
