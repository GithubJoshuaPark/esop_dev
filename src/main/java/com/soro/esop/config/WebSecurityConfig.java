package com.soro.esop.config;

import com.soro.esop.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soro.esop.filter.CustomAuthentication401Filter;
import com.soro.esop.filter.JwtRequestFilter;
import com.soro.esop.handler.AccessDenied403Handler;
import com.soro.esop.handler.Authentication401Handler;
import com.soro.esop.handler.JwtAuthenticationSuccessHandler;
import com.soro.esop.handler.JwtLogoutHandler;
import com.soro.esop.service.CustomUserDetailService;
import com.soro.esop.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable the method security (register the MethodSecurityConfiguration to use)
@RequiredArgsConstructor
public class WebSecurityConfig {

    // 패스워드 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    // 데이터베이스 연결
    //private final DataSource dataSource;

    private final JwtRequestFilter              jwtRequestFilter;              // JWT 필터
    private final CustomAuthentication401Filter customAuthentication401Filter; // 인증 필터
    private final Authentication401Handler      authentication401Handler;      // 인증 실패 핸들러
    private final AccessDenied403Handler        accessDenied403Handler;        // 접근 거부 핸들러
    private final CustomUserDetailService       userDetailsService;            // 사용자 정보 서비스
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    private static final String[] AUTH_WHITELIST = {
        "/",
        "/api/auth/login",  // for /api/auth/login testing
        "/account/login",
        "/account/register",
        "/css/**",
        "/js/**",
        "/images/**",
    };

    @Value("${jwt.expiration}") // 1_800_000 # 30 mins
    private Long expiration;

    @Value("${jwt.refreshExpire}") // 432_000_000 # 5 days in milliseconds
    private Long refreshExpiration;

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
            .csrf(csrf -> csrf.disable()) // CSRF 보안 설정, disable: CSRF 설정 비활성화
            .cors(cors -> cors.disable()) // CORS 설정, disable: CORS 설정 비활성화
			.authorizeHttpRequests((requests) -> requests
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
			)
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customAuthentication401Filter,
                             UsernamePasswordAuthenticationFilter.class)
            .formLogin((form) -> form
                .loginPage("/account/login") //  a filter that intercepts POST requests to "/account/login"
                .loginProcessingUrl("/account/login")
                .successHandler(new JwtAuthenticationSuccessHandler(jwtUtil, expiration, refreshExpiration, tokenRepository))
                //.defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                   .logoutUrl("/account/logout")
                   .addLogoutHandler(new JwtLogoutHandler(tokenRepository, jwtUtil))
                   .logoutSuccessHandler((request, response, authentication) -> {
                        response.sendRedirect("/account/login?logout");
                   })
                   .permitAll()
            )
            .exceptionHandling(exec -> exec
                    .authenticationEntryPoint(authentication401Handler) // 인증 실패 핸들러
                    .accessDeniedHandler(accessDenied403Handler)        // 접근 거부 핸들러
            );

		return http.build();
	}

    /**
     * It's called by the Spring IoC container
     * during the initialization of the security configuration.
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    /**
     * Set up the UserDetailsService and PasswordEncoder
     * that will be used for authentication throughout the application.
     * It's called by Spring during the application's security configuration phase.
     * It sets up the UserDetailsService and PasswordEncoder
     * that will be used for authentication throughout the application.
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}


    /**
     * 사용자 정보 설정
     * intercept the authentication request from POST /account/login, and authenticate the user
     * @param auth
     * @throws Exception
     */
    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    // {
    //     auth.jdbcAuthentication()
    //         .dataSource(dataSource)
    //         .passwordEncoder(passwordEncoder)
    //         .usersByUsernameQuery("select username,password,enabled "
    //             + "from user "
    //             + "where username = ?")
    //         .authoritiesByUsernameQuery("select u.username, r.name "
    //             + "from user_role ur inner join user u on ur.user_id = u.id "
    //             + "                  inner join role r on ur.role_id = r.id "
    //             + "where u.username = ?");
    // }
