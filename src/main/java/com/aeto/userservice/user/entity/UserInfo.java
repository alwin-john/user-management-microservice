package com.aeto.userservice.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info", schema = "account")
@Builder
@Getter
@Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_generator")
    @SequenceGenerator(name = "userId_generator", sequenceName = "userId_seq", allocationSize = 50)
    private long userId;

    private String userName;

    private String password;

    private String mobileNo;

    private String userRole;

    private long accountId;

    private LocalDateTime lastLogin;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime createdDate;

    //default constructor
    public UserInfo() {

    }

    public UserInfo(long userId, String userName, String password, String mobileNo, String roleId,
                    long accountId, LocalDateTime lastLogin, LocalDateTime createdDate) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.userRole = roleId;
        this.accountId = accountId;
        this.lastLogin = lastLogin;
        this.createdDate = createdDate;
    }
}
