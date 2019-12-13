package com.stackroute.authentication.repository;


import com.stackroute.authentication.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<UserDetails, Long> {
    @Query("SELECT u FROM UserDetails u WHERE u.email = ?1")
    UserDetails findByEmail(String email);
    Boolean existsUserDetailsByEmail(String email);


}
