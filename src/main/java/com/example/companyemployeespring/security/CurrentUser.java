package com.example.companyemployeespring.security;

import com.example.companyemployeespring.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private Employee user;

    public CurrentUser(Employee user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getPosition().name()));
        this.user = user;
    }

    public Employee getUser() {
        return user;
    }
}
