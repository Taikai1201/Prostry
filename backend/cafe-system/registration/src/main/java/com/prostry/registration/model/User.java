package com.prostry.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "prostry")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
    private String email;


    // Implement UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // no authorities are granted as we don't have different roles
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement based on your application's requirements
    }
}
