package com.soro.esop.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.Role;
import com.soro.esop.repository.RoleRepository;
import com.soro.esop.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? null : roles;
    }

    @Override
    public Role save(Role Role) {
        return roleRepository.save(Role);
    }

    @Override
    public Role update(Role Role) {
        return roleRepository.save(Role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
    
}
