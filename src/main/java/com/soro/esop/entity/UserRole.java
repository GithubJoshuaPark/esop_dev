package com.soro.esop.entity;


import com.soro.esop.entity.pk.UserRolePk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
