package com.zam.prueba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", unique = true, length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = true)
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name= "is_enabled")
    private boolean isEnabled;

    @Column(name= "account_no_expired")
    private boolean accountNoExpired;

    @Column(name= "account_no_locked")
    private boolean accountNoLocked;

    @Column(name= "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roleList;

}
