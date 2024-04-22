package org.example.spring_aop_example.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.example.spring_aop_example.controller.AccountController;
import org.example.spring_aop_example.enums.RoleEnum;
import org.example.spring_aop_example.message.ErrorMessage;
import org.example.spring_aop_example.model.dto.AccountDTO;
import org.example.spring_aop_example.model.entity.AccountEntity;
import org.example.spring_aop_example.model.entity.RoleEntity;
import org.example.spring_aop_example.repository.AccountRepository;
import org.example.spring_aop_example.repository.RoleRepository;
import org.example.spring_aop_example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> authenticated(AccountDTO accountDTO, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(accountDTO.getName(),accountDTO.getPassword());
        userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        try {
            Authentication authentication = authenticationManager.authenticate(userAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception ex){
            ErrorMessage errorMessage = new ErrorMessage(404,"");
            return errorMessage.getResponse();
        }

        return ResponseEntity.ok(accountDTO);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ResponseEntity<?> register(AccountDTO accountDTO) {
        try {
            RoleEntity role = roleRepository.findByName(RoleEnum.USER.name()).orElseThrow(
                    ()-> new NullPointerException("USER role is null")
            );
            AccountEntity account = new AccountEntity();
            account.setName(accountDTO.getName());
            account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(role);
            account.setRoles(roles);
            account = accountRepository.save(account);
            return ResponseEntity.ok(accountDTO);
        }catch (Exception e){
            ErrorMessage errorMessage = new ErrorMessage(401,"");
            return errorMessage.getResponse();
        }
    }
}
