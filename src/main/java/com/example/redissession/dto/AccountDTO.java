package com.example.redissession.dto;


import com.example.redissession.domain.Account;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class AccountDTO implements Serializable {
    private String user_name;
    private String password;
    private long id;
    private RoleDTO role;

    public static AccountDTO from(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUser_name(account.getUser_name());
      //  dto.setRole(RoleDTO.from(account.getRole()));
        return dto;
    }
}
