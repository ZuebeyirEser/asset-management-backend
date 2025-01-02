package com.listofemployee.demo.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an Employee entity in the database.
 * It is annotated with JPA annotations for persistence and Lombok annotations for boilerplate code reduction.
 */

@Entity
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id")
    private String emailId;

    // Many Employees can belong to one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
