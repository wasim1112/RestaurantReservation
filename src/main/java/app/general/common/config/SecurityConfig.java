package app.general.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {




    String[] alwaysOpenApis = new String[]{
            "/api/reservation/**","/api/role/**","/api/user/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, alwaysOpenApis).permitAll().
                antMatchers(HttpMethod.GET, alwaysOpenApis).permitAll().and()
                .cors();
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(alwaysOpenApis)
                .hasRole("admin")
                .antMatchers(alwaysOpenApis)
                .hasRole("admin")
                .and()
                .formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/reservation/**","/api/role/**","/api/user/**","/swagger-ui.html/**");
    }


    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private void successHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        response.setStatus(HttpStatus.OK.value());
    }


    private void loginAndLogoutSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        response.setStatus(HttpStatus.OK.value());
    }

    private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        // should not do anything
    }

}
