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
    private boolean adminStructure;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "purchase_data")
    private Date purchaseDate;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "warranty_date")
    private Date warrantyDate;

    @Column(name = "building")
    private Date building;

    @Column(name = "room")
    private String roomNumber;

    @Column(name = "work_group")
    private String roomNumber;

    @Column(name = "assigned_user_full_name")
    private String assignedUserFullName;

    @Column(name = "assigned_user_job_title")
    private String assignedUserTitle;

    @Column(name = "assigned_user_email")
    private String assignedUserEmail;


}
