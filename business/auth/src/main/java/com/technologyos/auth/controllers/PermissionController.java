package com.technologyos.auth.controllers;

import com.technologyos.commons.dtos.permissions.PermissionRequest;
import com.technologyos.commons.dtos.permissions.PermissionResponse;
import com.technologyos.auth.services.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

   @Autowired
   private PermissionService permissionService;

   @GetMapping
   public ResponseEntity<Page<PermissionResponse>> findAll(Pageable pageable){
      Page<PermissionResponse> permissions = permissionService.findAll(pageable);

      if(permissions.hasContent()){
         return ResponseEntity.ok(permissions);
      }

      return ResponseEntity.notFound().build();
   }

   @GetMapping("/{permissionId}")
   public ResponseEntity<PermissionResponse> findPermissionById(@PathVariable Long permissionId){
      Optional<PermissionResponse> permission = permissionService.findPermissionById(permissionId);

      return permission.map(ResponseEntity::ok)
         .orElseGet(() -> ResponseEntity.notFound().build());
   }

   @PostMapping
   public ResponseEntity<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest permissionRequest){
      PermissionResponse permission = permissionService.createPermission(permissionRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(permission);
   }

   @DeleteMapping("/{permissionId}")
   public ResponseEntity<PermissionResponse> deletePermissionById(@PathVariable Long permissionId){
      PermissionResponse permission = permissionService.deletePermissionById(permissionId);
      return ResponseEntity.ok(permission);
   }
}
