package com.r00ta.telematics.platform.authentication;

import java.util.Optional;

import com.r00ta.telematics.platform.users.models.User;

public interface IAuthService {

    String generateToken(String userId);

    Optional<User> getUserByEmail(String email);
}
