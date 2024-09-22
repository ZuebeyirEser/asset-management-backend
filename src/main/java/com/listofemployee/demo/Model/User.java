package com.listofemployee.demo.Model;

import com.listofemployee.demo.Model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
            strategy = GenerationType.AUTO,
            generator = "user_sequence"
    )
    private Integer id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
