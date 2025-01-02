package com.listofemployee.demo.Model;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents an asset entity in the database.
 * It is annotated with JPA annotations for persistence and Lombok annotations for boilerplate code reduction.
 */

@Entity
@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asset")
public class Asset {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "random_access_memory")
    private String ram;

    @Column(name = "storage_capacity")
    private String storageCapacity;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(name = "licence_of_OS")
    private String licenceOfOS;

    @Column(name = "pc_architecture")
    private String pcArchitecture;

    @Column(name = "host_name")
    private String hostName;

    // this shows if OS has admin user or not
    @Column(name = "is_admin_structure_setup")
    private String isAdminStructureThere;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "purchase_date")
    private String purchaseDate;

    @Column(name = "warranty_date")
    private String warrantyDate;

    // Many Employees can belong to one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // One-to-One relationship with AssignedUser
    @OneToOne
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    private AssignedUser assignedUser;

}
