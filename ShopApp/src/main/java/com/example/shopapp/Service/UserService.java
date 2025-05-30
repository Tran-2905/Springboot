package com.example.shopapp.Service;

import com.example.shopapp.Component.JWTTokenUtil;
import com.example.shopapp.Dtos.UserDTO;
import com.example.shopapp.Model.Role;
import com.example.shopapp.Model.User;
import com.example.shopapp.Repository.RoleRepository;
import com.example.shopapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JWTTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO user) {
        String phoneNumber = user.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new RuntimeException("User with phone number " + phoneNumber + " already exists");
        }
        User newUser = User.builder()
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth() != null ? java.sql.Date.valueOf(user.getDateOfBirth()) : null)
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .isActive(true)
                .build();
        Role role = roleRepository.findById(user.getRoleId().longValue()).orElseThrow(() -> new RuntimeException("Role not found"));
        newUser.setRole(role);
        if(user.getFacebookAccountId() == null || user.getGoogleAccountId() == null){
            String password = user.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Invalid username or password");
        }
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountId() == null || existingUser.getGoogleAccountId() == null){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new RuntimeException("Invalid password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(phoneNumber, password,existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
