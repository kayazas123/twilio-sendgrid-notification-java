package com.hardik.ballista.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.ballista.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Boolean existsByEmailId(String emailId);

	Boolean existsByContactNumber(String contactNumber);

}
