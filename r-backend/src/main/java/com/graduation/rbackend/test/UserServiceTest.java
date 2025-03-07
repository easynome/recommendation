//package com.graduation.rbackend.test;
//
//import com.graduation.rbackend.entity.User;
//import com.graduation.rbackend.repository.UserRepository;
//import com.graduation.rbackend.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private UserService userService;
//
//    void serUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testSaveUser() {
//        //模拟用户数据
//        User user=new User(1L,"Alice","Alice123","Alice@gmail.com");
//
//        //模拟Repository返回值
//        when(userRepository.save(user)).thenReturn(user);
//
//        //调用Service方法
//        User result=userService.saveUser(user);
//
//        //验证结果
//        assertEquals(user,result);
//        assertEquals("Alice",result.getUsername());
//        //验证Repository方法调用次数
//        verify(userRepository,times(1)).save(user);
//
// }
//
//}
