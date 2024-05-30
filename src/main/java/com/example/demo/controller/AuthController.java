package com.example.demo.controller;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.domain.Usuario;
import com.example.demo.infraSecurity.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = jwtService.generateToken((Usuario) auth.getPrincipal());

        Usuario user = (Usuario) auth.getPrincipal();

        return ResponseEntity.ok(new AuthDTO(token, user.getRole(), user.getId()));
    }

    @GetMapping("me")
    public ResponseEntity getMe (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }
}
