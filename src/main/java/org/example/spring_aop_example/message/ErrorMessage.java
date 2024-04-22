package org.example.spring_aop_example.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int status;
    private String message;

    public ResponseEntity<?> getResponse(){
        return ResponseEntity.status(this.status).body(this.message.getBytes(StandardCharsets.UTF_8));
    }
}
