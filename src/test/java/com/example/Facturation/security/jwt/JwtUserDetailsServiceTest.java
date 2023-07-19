package com.example.Facturation.security.jwt;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.example.Facturation.domain.Role;
import com.example.Facturation.domain.User;
import com.example.Facturation.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void loadUserByUsername() {
        //given
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singleton(role));
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // When
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        // Then
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(username, userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Assertions.assertNotNull(authorities);
        Assertions.assertEquals(1, authorities.size());
        Assertions.assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    public void testLoadUserByUsername_NonExistingUser() {
        // Given
        String username = "nonexistinguser";
        given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());

        // When and Then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username),
                "Expected UsernameNotFoundException to be thrown");
    }

}