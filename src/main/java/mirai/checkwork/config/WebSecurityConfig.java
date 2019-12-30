package mirai.checkwork.config;

import mirai.checkwork.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsServiceImpl) // Cung cáp userservice cho spring security
                .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/login", "/register").permitAll() // Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
            .antMatchers("/css/**", "/images/**", "/fonts/**", "/js/**", "/vendors/**").permitAll()
            .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
            .and()
            .formLogin() // Cho phép người dùng xác thực bằng form login
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error=true")
            .permitAll() // Tất cả đều được truy cập vào địa chỉ này
            .and()
            .logout() // Cho phép logout
            .permitAll();
    }
}
