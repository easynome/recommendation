//package com.graduation.rbackend.service;
//
//import com.graduation.rbackend.entity.User;
//import com.graduation.rbackend.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * 处理与用户相关的业务逻辑
// */
//@Service
//public class UserService {
//    private UserRepository userRepository;
//
//    /**
//     * 查找所有用户
//     * @return
//     */
//
//    public List<User> findAllUsers(){
//        return userRepository.findAll();
//    }
//
//    /**
//     * 保存用户
//     * @param user
//     * @return
//     */
//    public User saveUser(User user){
//        return userRepository.save(user);
//    }
//
//    /**
//     * 根据用户名查找用户
//     *
//     * @param username
//     * @return
//     */
//    public Optional<User> findByUserName(String username){
//        return userRepository.findByUsername(username);
//    }
//
//    /**
//     * 根据角色查找用户
//     *
//     * @param role
//     * @return
//     */
//    public Optional<User> findByRole(String role){
//        return userRepository.findByRole(role);
//    }
//
//   /**
//     * 根据ID删除用户
//     * @param id
//     */
//    public void deleteUser(Long id){
//        userRepository.deleteById(id);
//    }
//
//    public User findById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//}
