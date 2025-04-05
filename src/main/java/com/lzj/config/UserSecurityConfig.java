package com.lzj.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.mapper.UserMapper;
import com.lzj.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

    private final UserMapper userMapper;

    @Autowired
    public UserSecurityConfig(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("尝试登录的用户名: " + username); // 添加日志
            User user = userMapper.selectOne(
                    new QueryWrapper<User>().eq("username", username));
            if (user == null) {
                System.out.println("用户不存在: " + username); // 添加日志
                throw new UsernameNotFoundException("用户不存在");
            }
            String role = "ROLE_" + (user.getIsAdmin() == 1 ? "ADMIN" : "USER");
            System.out.println("角色: " + role);
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform-login") // 明确指定登录处理地址
                        .defaultSuccessUrl("/u/index", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                )
                .csrf(csrf -> csrf.disable()) // 明确禁用 CSRF
                // 启用 Remember Me 记住我，功能
                .rememberMe(rememberMe -> rememberMe
                        .key("your-secure-key-here") // 必须设置一个安全密钥
                        .tokenValiditySeconds(86400) // 令牌有效期（秒），默认 14 天
                );
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
