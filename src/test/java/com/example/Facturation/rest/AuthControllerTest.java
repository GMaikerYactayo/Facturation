package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.dto.LoginDTO;
import com.example.Facturation.dto.RegisterDTO;
import com.example.Facturation.repository.UserRepository;
import com.example.Facturation.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Login - Success")
    void login() throws Exception {
        // Given
        String username = "testuser";
        String password = "testpassword";
        String token = "testtoken";

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        given(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authentication);

        given(jwtTokenProvider.generateJwtToken(authentication)).willReturn(token);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDTO)));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(token));
    }

    @Test
    @DisplayName("Register - Success")
    void register() throws Exception {
        // Given
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "testpassword";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(username);
        registerDTO.setEmail(email);
        registerDTO.setPassword(password);

        given(userRepository.existsByUsername(username)).willReturn(false);
        given(userRepository.existsByEmail(email)).willReturn(false);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerDTO)));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Se registró con éxito"));
    }

    @Test
    @DisplayName("Register - Username Already Taken")
    public void testRegister_UsernameAlreadyTaken() throws Exception {
        // Given
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "testpassword";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(username);
        registerDTO.setEmail(email);
        registerDTO.setPassword(password);

        given(userRepository.existsByUsername(username)).willReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerDTO)));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
    }

    @Test
    @DisplayName("Register - Email Already in Use")
    public void testRegister_EmailAlreadyInUse() throws Exception {
        // Given
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "testpassword";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(username);
        registerDTO.setEmail(email);
        registerDTO.setPassword(password);

        given(userRepository.existsByUsername(username)).willReturn(false);
        given(userRepository.existsByEmail(email)).willReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerDTO)));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Error: Email is already in use!"));
    }
}