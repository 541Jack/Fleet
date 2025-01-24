package com.fleet.repository;

import com.fleet.domain.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //derived query
    User findByEmail(String email);

    //Custom query
    @Query("select u from User u where u.name Like %:name%")
    List<User> searchByName(@Param("name") String name);
}
