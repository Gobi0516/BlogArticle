package com.gobi.blog.services;

import com.gobi.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
