package com.example.redissession.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String user_name;
    private String password;
    private String token;
    @ManyToOne
    private Role role;
}
