package com.example.shopapp.Service;

import com.example.shopapp.Dtos.UserDTO;
import com.example.shopapp.Model.User;

public interface IUserService {
    User createUser(UserDTO user);
    String login(String phoneNumber, String password);

}
