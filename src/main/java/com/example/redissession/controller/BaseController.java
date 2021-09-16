package com.example.redissession.controller;

import com.example.redissession.config.IpAddressUtil;
import com.example.redissession.config.JwtTokenUtil;
import com.example.redissession.domain.Account;
import com.example.redissession.dto.AccountDTO;
import com.example.redissession.dto.JwtReponseDTO;
import com.example.redissession.service.AccountService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableAutoConfiguration
@RestController
@CrossOrigin("*")

public class BaseController {

    @Autowired
    AccountService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IpAddressUtil ipAddressUtil;

    @PostMapping("/save")
    public ResponseEntity<AccountDTO> save(@RequestBody AccountDTO dto, @AuthenticationPrincipal AccountDTO dto2) {
        Account account = service.save(dto);
        return new ResponseEntity<>(AccountDTO.from(account), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtReponseDTO> login(@RequestBody AccountDTO dto, HttpServletRequest request) throws Exception {
        String ipAddress = ipAddressUtil.getIpAddress(request);
        authenticate(dto.getUser_name(), dto.getPassword());
        final UserDetails userDetails = service
                .loadUserByUsername(dto.getUser_name());
        final String token = jwtTokenUtil.generateToken(userDetails, ipAddress);
        Account account = service.findByName(dto.getUser_name());
        String tokenAccount = account.getToken();
        if (token != null && !tokenAccount.isEmpty()) {
            try{
                if (!jwtTokenUtil.isTokenExpired(tokenAccount)) {
                    throw new RuntimeException("Current Account is logged!");
                }
            }catch (ExpiredJwtException e){
                account.setToken("");
            }

        }
        account.setToken(token);
        service.save(account);


        JwtReponseDTO jwtReponse = new JwtReponseDTO();
        jwtReponse.setToken(token);
        return new ResponseEntity<>(jwtReponse, HttpStatus.OK);
    }

    @PostMapping("test")
    public String hello() {
        return "Welcome!";
    }


    @GetMapping("/refresh")
    public String refreshToken() {
        return jwtTokenUtil.refreshToken("xxx");
    }

    @PostMapping("/sign-out")
    public String lougOut(HttpServletRequest request) {
        String jwtToken = null;
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            String x = jwtTokenUtil.getUsernameFromToken(jwtToken);
            Account account = service.findByName(x);
            account.setToken("");
            service.save(account);
            return "Đăng xuất thành công";
        }


        return jwtToken;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("Tài khoản bị khóa", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Tài khoản mật khẩu không chính xác", e);
        }
    }
}
