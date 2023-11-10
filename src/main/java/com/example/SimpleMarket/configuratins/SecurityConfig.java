package com.example.SimpleMarket.configuratins;


import com.example.SimpleMarket.security.AuthProviderImpl;
import com.example.SimpleMarket.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProviderImpl authProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService) //устанавливаем сервис указателем как нужно подгружать юзеров
//                .passwordEncoder(passwordEncoder()); //указываем как будет происходить расшифровка паролей
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception { //конфигурация доступных/недоступных url
//        http
//                .authorizeRequests()
//                .anyRequest().permitAll();
//                /*.authorizeRequests()
//                .antMatchers("/", "/product/**", "/images/**", "/registration", "/user/**", "/static/**", "/login")//всегда доступные url
//                .permitAll()
//                .anyRequest().authenticated() //здесь уже требуется аутентификация
//                .and()
//                .formLogin()//конфигурируем форму входа
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();*/
//    }