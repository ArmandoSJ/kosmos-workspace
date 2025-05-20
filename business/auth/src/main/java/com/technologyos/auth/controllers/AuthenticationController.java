package com.technologyos.auth.controllers;

import com.technologyos.commons.dtos.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

   @PostMapping("/authenticate")
   public ResponseEntity<String> authenticate(){
      Test test = new Test();
      test.setElement("asda");
      System.out.println(test.getElement());
      return ResponseEntity.ok("Validacion");
   }
}
