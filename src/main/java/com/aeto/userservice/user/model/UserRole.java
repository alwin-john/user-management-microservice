package com.aeto.userservice.user.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserRole {

    @Id
    private Long roleId;

    private String roleName;

    private String roleDesc;

    private Date createdDate;
}
