package com.trunggame.security.services;

import com.trunggame.models.User;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    void deleteUserByIds(List ids);

    Boolean sendEmailRegister(User user) throws MessagingException;

}
