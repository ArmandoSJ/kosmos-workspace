package com.technologyos.auth.controllers;

import com.technologyos.commons.dtos.signup.RegisteredUser;
import com.technologyos.commons.dtos.signup.UserRequest;
import com.technologyos.commons.security.AuthenticationService;
import com.technologyos.commons.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

   private final AuthenticationService authenticationService;
   private final UserService userService;

   @GetMapping
   public ResponseEntity<Page<RegisteredUser>> findAll(Pageable pageable){
      Page<RegisteredUser> doctorsPage = userService.findAll(pageable);

      if(doctorsPage.hasContent()){
         return ResponseEntity.ok(doctorsPage);
      }

      return ResponseEntity.notFound().build();
   }

   @PostMapping
   public ResponseEntity<RegisteredUser> registerCustomer(@RequestBody @Valid UserRequest userRequest){
      RegisteredUser registeredUser = authenticationService.registerCustomer(userRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
   }
}
