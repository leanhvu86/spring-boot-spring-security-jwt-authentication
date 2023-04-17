package com.trunggame.security.services.impl;

import com.trunggame.repository.UserRepository;
import com.trunggame.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void deleteUserByIds(List ids) {
        userRepository.deleteUserByIds(ids);
    }
}
