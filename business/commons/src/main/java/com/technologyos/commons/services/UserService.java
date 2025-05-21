package com.technologyos.commons.services;

import com.technologyos.commons.dtos.signup.RegisteredUser;
import com.technologyos.commons.dtos.signup.UserRequest;
import com.technologyos.commons.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

   Page<RegisteredUser> findAll(Pageable pageable);

   User registerCustomer(UserRequest userRequest);

   Optional<User> findCustomerByUsername(String username);
}
