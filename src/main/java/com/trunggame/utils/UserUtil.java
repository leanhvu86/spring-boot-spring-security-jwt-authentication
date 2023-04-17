//package com.trunggame.utils;
//
//import com.trunggame.models.User;
//import com.trunggame.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserUtil {
//
//    @Autowired
//    UserRepository userRepository;
//
//    public  User currentUserInfo() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        var currentUser = userRepository.findByUsername(userDetails.getUsername());
//        return currentUser.get();
//    }
//
//}
