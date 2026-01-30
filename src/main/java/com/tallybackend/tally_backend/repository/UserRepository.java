package com.tallybackend.tally_backend.repository;

import com.tallybackend.tally_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

    User findByUsername(String username);
}
