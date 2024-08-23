package com.soro.esop.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRolePk implements Serializable {
    @Id @Column(name="user_id", nullable=false) private Long userId;
    @Id @Column(name="role_id", nullable=false) private Long roleId;
}
