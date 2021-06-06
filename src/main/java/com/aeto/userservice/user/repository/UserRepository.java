package com.aeto.userservice.user.repository;

import com.aeto.userservice.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

    UserInfo save(UserInfo userInfo);
}
