package com.r00ta.telematics.platform.authentication;

import com.r00ta.telematics.platform.users.models.User;

public interface IAuthService {
    String generateToken(String userId);

    User getUserByEmail(String email);
}
