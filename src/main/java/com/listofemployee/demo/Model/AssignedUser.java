package com.listofemployee.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assigned_user")
public class AssignedUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "building")
    private String building;

    @Column(name = "room")
    private String roomNumber;

    @Column(name = "work_group")
    private String workGroup;

    // One-to-One relationship with Asset
    @OneToOne(mappedBy = "assignedUser")
    private Asset asset;

}
