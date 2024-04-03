package com.example.final_dn.Service;

import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import com.example.final_dn.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        roleRepository.save(role);
        return role;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void deleteRole(Long id) {
       roleRepository.deleteById(id);
    }
}
