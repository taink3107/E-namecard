package com.example.redissession.dto;

import com.example.redissession.domain.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private long id;
    private String name;

    public static RoleDTO from(Role role){
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
