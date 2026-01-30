package com.tallybackend.tally_backend.repository;

import com.tallybackend.tally_backend.entity.Type.ProviderType;
import com.tallybackend.tally_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

//    User findByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, ProviderType providerType);
}
