package com.technologyos.auth.repositories;

import com.technologyos.commons.entities.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<GrantedPermission, Long> {}
