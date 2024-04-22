package org.example.spring_aop_example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.spring_aop_example.model.dto.AccountDTO;
import org.example.spring_aop_example.model.entity.RoleEntity;
import org.example.spring_aop_example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody AccountDTO account){
        return accountService.authenticated(account,request);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody AccountDTO accountDTO){
       return accountService.register(accountDTO);
    }
}
