package com.technologyos.commons.repositories;

import com.technologyos.commons.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {
   @Query("SELECT o FROM Operation o where o.permitAll = true")
   List<Operation> findByPublicAccess();

   Optional<Operation> findByName(String operation);
}
