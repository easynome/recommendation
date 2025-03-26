package com.graduation.rbackend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 1, message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 1, message = "密码不能为空")
    private String password;

    /**
     * 带参构造函数，用于初始化用户名和密码。
     *
     * @param username 用户名
     * @param password 密码
     * @throws IllegalArgumentException 如果用户名或密码为空
     */
    public LoginRequestDTO(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        this.username = username;
        this.password = password;
    }
}
