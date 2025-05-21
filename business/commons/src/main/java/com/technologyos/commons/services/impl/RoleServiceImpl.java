package com.technologyos.commons.services.impl;

import com.technologyos.commons.entities.Role;
import com.technologyos.commons.repositories.RoleRepository;
import com.technologyos.commons.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
   @Autowired
   private RoleRepository roleRepository;

   @Value("${security.default.role}")
   private String defaultRole;

   @Override
   public Optional<Role> findDefaultRole() {
      return roleRepository.findByName(defaultRole);
   }
}
