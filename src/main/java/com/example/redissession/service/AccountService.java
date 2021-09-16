package com.example.redissession.service;

import com.example.redissession.domain.Account;
import com.example.redissession.domain.Role;
import com.example.redissession.dto.AccountDTO;
import com.example.redissession.repository.AccountRepository;
import com.example.redissession.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository repository;

    public Account findById(Long id) {
        Account account = repository.getById(id);
        return account;
    }

    public List<Account> findAll() {
        List<Account> accounts = repository.findAll();
        return accounts;
    }

    public Account save(AccountDTO dto) {
        Account account = new Account();
        // account.setId(dto.getId());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setUser_name(dto.getUser_name());
        account.setRole(roleRepository.getById(1L));
        return repository.save(account);
    }

    public Account save(Account account) {
        return repository.save(account);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = findByName(s);
        if (account == null) {
            throw new UsernameNotFoundException("xxx");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
        return new User(account.getUser_name(), account.getPassword(), authorities);
    }

    public Account findByName(String user_name) {
        Account account = repository.findByName(user_name);
        return account;
    }

//    public Account login(AccountDTO dto){
//        Account account = findByName(dto.getUser_name());
//        if(account == null){
//            throw new UsernameNotFoundException("xxx");
//        }
//        if(passwordEncoder.encode(dto.getPassword()).matches())
//        return account;
//    }
}
