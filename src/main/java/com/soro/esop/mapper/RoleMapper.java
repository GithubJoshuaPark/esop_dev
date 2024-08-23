package com.soro.esop.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soro.esop.entity.Role;
import com.soro.esop.model.RoleDto;

@Component
public class RoleMapper {
    public static RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    public static List<RoleDto> toDto(List<Role> roles) {
        if(roles == null) {
            return null;
        }
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(toDto(role));
        }
        return roleDtos;
    }
    
    public static Role toEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

    public static List<Role> toEntity(List<RoleDto> roleDtos) {
        if(roleDtos == null) {
            return null;
        }
        List<Role> roles = new ArrayList<>();
        for (RoleDto roleDto : roleDtos) {
            roles.add(toEntity(roleDto));
        }
        return roles;
    }
}
