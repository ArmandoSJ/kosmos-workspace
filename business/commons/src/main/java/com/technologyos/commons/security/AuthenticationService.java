package com.technologyos.commons.security;

import com.technologyos.commons.dtos.auth.AuthenticationRequest;
import com.technologyos.commons.dtos.auth.AuthenticationResponse;
import com.technologyos.commons.dtos.signup.RegisteredUser;
import com.technologyos.commons.dtos.signup.UserRequest;
import com.technologyos.commons.entities.JwtToken;
import com.technologyos.commons.entities.User;
import com.technologyos.commons.exceptions.ObjectNotFoundException;
import com.technologyos.commons.repositories.JwtTokenRepository;

import com.technologyos.commons.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

   private final UserService userService;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;
   private final JwtTokenRepository jwtTokenRepository;

   public RegisteredUser registerCustomer(UserRequest newUser) {
      User user = userService.registerCustomer(newUser);
      String jwt = jwtService.generateToken(user, generateExtraClaims(user));
      saveUserToken(user, jwt);
      return RegisteredUser.builder()
         .userId(user.getUserId())
         .name(user.getName())
         .username(user.getUsername())
         .role(user.getRole().getName())
         .jwt(jwt)
         .build();
   }

   private Map<String, Object> generateExtraClaims(User user) {
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("name", user.getName());
      extraClaims.put("role", user.getRole().getName());
      extraClaims.put("authorities", user.getAuthorities());
      return extraClaims;
   }

   public AuthenticationResponse login(AuthenticationRequest autRequest) {
      Authentication authentication = new UsernamePasswordAuthenticationToken(
         autRequest.getUsername(), autRequest.getPassword()
      );

      authenticationManager.authenticate(authentication);

      UserDetails user = userService.findCustomerByUsername(autRequest.getUsername()).get();
      String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));
      saveUserToken((User) user, jwt);

      AuthenticationResponse authRsp = new AuthenticationResponse();
      authRsp.setJwt(jwt);

      return authRsp;
   }

   private void saveUserToken(User user, String jwt) {
      JwtToken jwtToken = JwtToken.builder()
         .token(jwt)
         .user(user)
         .expiration(jwtService.extractExpiration(jwt))
         .isValid(true)
         .build();
      jwtTokenRepository.save(jwtToken);
   }

   public boolean validateToken(String jwt) {
      try{
         JwtToken token = jwtTokenRepository.findByToken(jwt)
            .orElseThrow(() -> new ObjectNotFoundException("Token not found: " + jwt));

         if (!token.isValid() || token.getExpiration().before(new Date())) {
            return false;
         }

         jwtService.extractUsername(token.getToken());
         return true;
      }catch (Exception e){
         log.warn("Invalid JWT: {}", e.getMessage());
         return false;
      }
   }

   public User findLoggedInUser() {
      UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
      String username = (String) auth.getPrincipal();

      return userService.findCustomerByUsername(username)
         .orElseThrow(() -> new ObjectNotFoundException("User not found. username: " + username));
   }

   public void logout(HttpServletRequest request) {
      String jwt = jwtService.extractJwtFromRequest(request);

      if(!StringUtils.hasText(jwt)) return;

      Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);

      if(token.isPresent() && token.get().isValid()){
         token.get().setValid(false);
         jwtTokenRepository.save(token.get());
      }
   }
}
