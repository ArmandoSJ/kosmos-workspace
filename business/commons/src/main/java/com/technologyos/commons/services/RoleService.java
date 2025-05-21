package com.technologyos.commons.services;

import com.technologyos.commons.entities.Role;

import java.util.Optional;

public interface RoleService {
   Optional<Role> findDefaultRole();
}
