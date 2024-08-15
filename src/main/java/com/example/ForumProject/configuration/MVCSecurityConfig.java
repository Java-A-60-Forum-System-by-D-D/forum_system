package com.example.ForumProject.configuration;


import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.services.implementations.ForumServiceDetails;
import com.example.ForumProject.utility.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;


@Configuration

public class MVCSecurityConfig {

    private final RSAKeyProperties keys;
    private final String rememberMeKey;
    private final PasswordEncoder passwordEncoder;
    private final ForumServiceDetails forumServiceDetails;
    private final CustomEntryPoint customEntryPoint;


    @Autowired
    public MVCSecurityConfig(RSAKeyProperties keys, @Value("${forum.remember.me.key}") String rememberMeKey, PasswordEncoder passwordEncoder, ForumServiceDetails forumServiceDetails, CustomEntryPoint customEntryPoint) {
        this.keys = keys;
        this.rememberMeKey = rememberMeKey;
        this.passwordEncoder = passwordEncoder;
        this.forumServiceDetails = forumServiceDetails;
        this.customEntryPoint = customEntryPoint;
    }

    @Bean("mvcAuthenticationManager")
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(forumServiceDetails)
               .passwordEncoder(passwordEncoder);
        return builder.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/swagger-ui/**","/v3/api-docs/", "/api/"))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
                    auth.requestMatchers("/", "/home", "/home/**", "/login", "/register", "/error").permitAll();
                    auth.requestMatchers("/api/**").permitAll();
                    auth.anyRequest().authenticated();

                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .rememberMe(
                        rememberMe -> {
                            rememberMe.key(rememberMeKey)
                                      .rememberMeParameter("rememberme")
                                      .rememberMeCookieName("rememberme");
                        }
                )
                .logout(
                        logout -> {
                            logout.logoutUrl("/logout")
                                  .logoutSuccessUrl("/home")
                                  .invalidateHttpSession(true)
                                  .clearAuthentication(true)
                                  .permitAll();
                        })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customEntryPoint))


                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );


        return http.build();
    }


    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/login?error=true");
        return handler;
    }


    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/home");
        return handler;
    }


//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setSameSite("None");
//        serializer.setUseSecureCookie(true); // Ensure cookies are sent over HTTPS
//        return serializer;
//    }


}