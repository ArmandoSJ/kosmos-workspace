package com.technologyos.commons.configs;

import com.technologyos.commons.configs.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class HttpSecurityConfig {
   private final AuthenticationProvider daoAuthProvider;
   private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;
   private final AccessDeniedHandler accessDeniedHandler;
   private final JwtAuthenticationFilter jwtAuthenticationFilter;

   private static final String[] NO_AUTH_LIST = {
      "/v3/api-docs",
      "/configuration/ui",
      "/swagger-resources",
      "/configuration/security",
      "/webjars/**",
      "/login",
      "/h2-console/**"
   };

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
         .cors(withDefaults())
         .csrf(AbstractHttpConfigurer::disable)
         .sessionManagement(sesMagConfig -> sesMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .authenticationProvider(daoAuthProvider)
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
         .authorizeHttpRequests( authReqConfig -> {
            authReqConfig.requestMatchers(NO_AUTH_LIST).permitAll();
            authReqConfig.anyRequest().access(authorizationManager);
         })
         .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
         .build();
   }

   /**
    * CORS configuration to allow all origins and methods (adjust as needed for production).
    */
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(List.of("*"));
      config.setAllowedMethods(List.of("*"));
      config.setAllowedHeaders(List.of("*"));
      config.setAllowCredentials(true);

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
   }
}
