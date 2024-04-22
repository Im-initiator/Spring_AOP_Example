package org.example.spring_aop_example.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.spring_aop_example.model.dto.AccountDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> authenticated(AccountDTO accountDTO, HttpServletRequest request);
    ResponseEntity<?> register(AccountDTO accountDTO);
}
