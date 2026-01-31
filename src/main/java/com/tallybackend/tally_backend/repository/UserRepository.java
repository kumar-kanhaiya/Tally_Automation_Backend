package com.tallybackend.tally_backend.repository;

import com.tallybackend.tally_backend.entity.Type.ProviderType;
import com.tallybackend.tally_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

//    User findByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderName(String providerId, ProviderType providerName);
}
