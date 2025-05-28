package com.example.shopapp.Service;

import com.example.shopapp.Dtos.UserDTO;
import com.example.shopapp.Model.Role;
import com.example.shopapp.Model.User;
import com.example.shopapp.Repository.RoleRepository;
import com.example.shopapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
                .build();
        Role role = roleRepository.findById(user.getRoleId().longValue()).orElseThrow(() -> new RuntimeException("Role not found"));
        newUser.setRole(role);
        if(user.getFacebookAccountId() != null || user.getGoogleAccountId() != null){
            String password = user.getPassword();
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
