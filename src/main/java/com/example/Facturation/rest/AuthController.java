package com.example.Facturation.rest;

import com.example.Facturation.domain.User;
import com.example.Facturation.dto.JWTAuthResonseDTO;
import com.example.Facturation.dto.LoginDTO;
import com.example.Facturation.dto.RegisterDTO;
import com.example.Facturation.exception.MessageResponseException;
import com.example.Facturation.repository.UserRepository;
import com.example.Facturation.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResonseDTO> login(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        String token = jwtTokenProvider.generateJwtToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JWTAuthResonseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponseException> register(@RequestBody RegisterDTO registerDTO) {

        // Check 1: username
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseException("Error: Username is already taken!"));
        }

        // Check 2: email
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseException("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encoder.encode(registerDTO.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseException("Se registró con éxito"));
    }


}
