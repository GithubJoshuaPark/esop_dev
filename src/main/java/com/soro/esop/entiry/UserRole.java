package com.soro.esop.entiry;


import com.soro.esop.entiry.pk.UserRolePk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserRolePk.class)
@Table(name = "user_role")
public class UserRole {
    
    @Id @Column(name="user_id" , nullable=false ) private Long userId;
    @Id @Column(name="role_id" , nullable=false ) private Long roleId;
}
