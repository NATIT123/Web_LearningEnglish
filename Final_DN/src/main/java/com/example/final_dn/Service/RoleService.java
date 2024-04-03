package com.example.final_dn.Service;

import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;

public interface RoleService {

    public Iterable<Role> getAllRoles();
    public Role saveRole(Role role);

    public Role findByName(String name);

    public void deleteRole(Long id);
}
