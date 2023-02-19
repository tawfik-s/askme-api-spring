package com.tawfeek.askme.repository;

import com.tawfeek.askme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.userName = :name")
    Optional<List<User>> findByUserName( @Param("name")String userName);

    Optional<User> findByEmail(String email);
}
