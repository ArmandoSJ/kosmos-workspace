package com.technologyos.commons.services.impl;

import com.technologyos.commons.dtos.signup.RegisteredUser;
import com.technologyos.commons.dtos.signup.UserRequest;
import com.technologyos.commons.entities.Role;
import com.technologyos.commons.entities.User;
import com.technologyos.commons.exceptions.InvalidPasswordException;
import com.technologyos.commons.exceptions.ObjectNotFoundException;
import com.technologyos.commons.repositories.UserRepository;
import com.technologyos.commons.services.RoleService;
import com.technologyos.commons.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final RoleService roleService;

   @Override
   public Page<RegisteredUser> findAll(Pageable pageable) {
      Page<User> userPage = userRepository.findAll(pageable);

      List<RegisteredUser> content = userPage.getContent().stream()
         .map(user -> RegisteredUser.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .name(user.getName())
            .role(user.getRole().getName())
            .build())
         .collect(Collectors.toList());

      return new PageImpl<>(content, pageable, userPage.getTotalElements());
   }

   @Override
   public User registerCustomer(UserRequest userRequest) {
      validatePassword(userRequest);
      User user = new User();
      user.setUsername(userRequest.getUsername());
      user.setName(userRequest.getName());
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      Role defaultRole = roleService.findDefaultRole()
         .orElseThrow(() -> new ObjectNotFoundException("Role not found. Default Role"));
      user.setRole(defaultRole);
      return userRepository.save(user);
   }

   @Override
   public Optional<User> findCustomerByUsername(String username) {
      return userRepository.findByUsername(username);
   }

   private void validatePassword(UserRequest userRequest) {
      String password = userRequest.getPassword();
      String repeatedPassword = userRequest.getRepeatedPassword();

      List<Map.Entry<Boolean, String>> rules = List.of(
         Map.entry(!StringUtils.hasText(password), "Password must not be empty"),
         Map.entry(!StringUtils.hasText(repeatedPassword), "Repeated password must not be empty"),
         Map.entry(!password.equals(repeatedPassword), "Passwords do not match"),
         Map.entry(password.length() < 8, "Password must be at least 8 characters long"),
         Map.entry(!password.matches(".*[A-Z].*"), "Password must contain at least one uppercase letter"),
         Map.entry(!password.matches(".*[a-z].*"), "Password must contain at least one lowercase letter"),
         Map.entry(!password.matches(".*\\d.*"), "Password must contain at least one number"),
         Map.entry(!password.matches(".*[!@#$%^&*()_+\\-\\[\\]{}|;':\",.<>/?].*"), "Password must contain at least one special character")
      );

      rules.stream()
         .filter(Map.Entry::getKey)
         .findFirst()
         .ifPresent(rule -> {
            throw new InvalidPasswordException(rule.getValue());
         });
   }
}
