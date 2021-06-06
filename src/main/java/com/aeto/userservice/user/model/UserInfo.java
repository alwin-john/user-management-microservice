package com.aeto.userservice.user.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "user_info", schema = "account")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userId_generator")
    @SequenceGenerator(name="userId_generator", sequenceName = "userId_seq", allocationSize=50)
    private Long userId;

    private String userName;

    private String password;

    private String mobileNo;

    private Long roleId;

    private Long accountId;

    private Date lastLogin;

    private Date createdDate;
}
