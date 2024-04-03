package com.example.final_dn.Repository;

import com.example.final_dn.Model.Role;
import com.example.final_dn.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(String name);

}
