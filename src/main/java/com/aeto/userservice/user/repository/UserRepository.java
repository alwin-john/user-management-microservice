package com.aeto.userservice.user.repository;

import com.aeto.userservice.user.entity.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUserName(String userName);

    UserInfo findByUserId(long userId);

    UserInfo save(UserInfo userInfo);

    UserInfo deleteById(long userId);

    List<UserInfo> findByAccountId(long accountId);
}
