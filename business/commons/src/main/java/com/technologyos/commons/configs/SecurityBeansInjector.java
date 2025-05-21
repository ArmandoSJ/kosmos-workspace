package com.technologyos.commons.configs;

import com.technologyos.commons.exceptions.ObjectNotFoundException;
import com.technologyos.commons.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeansInjector {

   private final UserRepository userRepository;

   /**
    * Configures the authentication manager.
    */
   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return configuration.getAuthenticationManager();
   }

   /**
    * Defines the authentication provider using DAO strategy and bcrypt encoding.
    */
   @Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
      authenticationStrategy.setPasswordEncoder(this.passwordEncoder());
      authenticationStrategy.setUserDetailsService(this.userDetailsService());
      return authenticationStrategy;
   }

   /**
    * Configures the password encoder to use BCrypt.
    */
   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   /**
    * Custom user details service to retrieve user by username.
    */
   @Bean
   public UserDetailsService userDetailsService() {
      return username -> userRepository.findByUsername(username)
         .orElseThrow(() -> new ObjectNotFoundException("User not found with username: " + username));
   }
}
