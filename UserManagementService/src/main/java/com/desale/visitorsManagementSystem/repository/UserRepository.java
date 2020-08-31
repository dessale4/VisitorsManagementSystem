package com.desale.visitorsManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desale.visitorsManagementSystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
