package com.zam.prueba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employer")
public class EmployerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "employer_email", unique = true)
    private String employerEmail;

    @Column(name = "employer_phone_number")
    private String employerPhoneNumber;

}
