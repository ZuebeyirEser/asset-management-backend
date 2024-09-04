package com.listofemployee.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/* @Data is a convenient shortcut annotation that bundles the features of
@ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
together:*/
@Data
/*The @Builder annotation is part of Project Lombok, a library that reduces
boilerplate code in Java. Project Lombok achieves this by
generating common code constructs, such as getters, setters,
constructors, and builders, during compile time. The @Builder
annotation, specifically, generates a builder pattern for a class,
allowing you to create instances of the class with a fluent and expressive API.*/
@Builder
// @NoArgsConstructor will generate a constructor with no parameters.
@NoArgsConstructor
/*@AllArgsConstructor generates a constructor with 1 parameter for
each field in your class. Fields marked with @NonNull result in
null checks on those parameters. */
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
