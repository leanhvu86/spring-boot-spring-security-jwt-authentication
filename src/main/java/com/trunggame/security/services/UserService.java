package com.trunggame.security.services;

import com.trunggame.dto.SignupRequestDTO;
import com.trunggame.dto.ValidateRequestDTO;
import com.trunggame.models.User;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    void deleteUserByIds(List ids);

    void activeUserByIds(List ids);

    Boolean sendEmailRegister(User user) throws MessagingException;

    Boolean sendEmailForget(User user) throws MessagingException;

    Boolean sendEmailRegister() throws MessagingException;

    String validatePhoneAndEmail(ValidateRequestDTO signupRequestDTO);

    Boolean forgetPassword(ValidateRequestDTO signupRequestDTO) throws MessagingException;

    String RandGeneratedStr(int l);

    Boolean sendEmailOrderSuccessful(Integer type,String fullName, String email,String orderCode,String url) throws MessagingException;
}
