package com.sprint.mission.discodeit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.security.SecurityMatchers;
import com.sprint.mission.discodeit.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.sprint.mission.discodeit.security.handler.SessionRegistryLogoutHandler;
import com.sprint.mission.discodeit.security.jwt.JwtService;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
@EnableMethodSecurity // 메소드 레벨 security 활성화
@EnableSpringHttpSession // HttpSession 을 Spring Session 으로 변환
public class SecurityConfig {

    private static final String KEY = "mySuperSecretKey123!"; // 추후 환경 변수 등으로 관리
    private static final int REMEMBER_ME_COOKIE_EXPIRED_TIME = 60 * 60 * 24 * 21;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        CustomUsernamePasswordAuthenticationFilter loginFilter,
        UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository,
        SessionRegistry sessionRegistry, ObjectMapper objectMapper)
        throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/actuator/**",
                    "/favicon.ico",
                    "/",
                    "/error",
                    "/index.html",
                    "/assets/**",
                    "/api/auth/**",
                    "/api/users"
                ).permitAll()
                .requestMatchers("api/auth/role").hasRole("ADMIN")
                .anyRequest().hasRole("USER")
            );

        // session
        http
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // remember-me
        http
            .rememberMe(r -> r
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(REMEMBER_ME_COOKIE_EXPIRED_TIME)
                .key(KEY)
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository)
            );

        // 기본 설정 및 커스텀 필터 추가
        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .logout(logout ->
                logout
                    .logoutRequestMatcher(SecurityMatchers.LOGOUT)
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                    .addLogoutHandler(new SessionRegistryLogoutHandler(sessionRegistry))
            )
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
//            .addFilter(
//                new ConcurrentSessionFilter(sessionRegistry,
//                    new CustomSessionInformationExpiredStrategy(objectMapper))
//            );

        return http.build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder, RoleHierarchy roleHierarchy) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(roleHierarchy));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter(
        AuthenticationManager authenticationManager,
        RegisterSessionAuthenticationStrategy sessionAuthenticationStrategy,
        RememberMeServices rememberMeServices, JwtService jwtService) {
        return new CustomUsernamePasswordAuthenticationFilter(authenticationManager,
            sessionAuthenticationStrategy, rememberMeServices, jwtService);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        // 최초 실행 시 true로 설정하면 테이블을 자동 생성해줌 (한 번만!)
        repo.setCreateTableOnStartup(false);
        return repo;
    }

    @Bean
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService,
        PersistentTokenRepository tokenRepository) {
        PersistentTokenBasedRememberMeServices services =
            new PersistentTokenBasedRememberMeServices(KEY, userDetailsService,
                tokenRepository);
        services.setParameter("remember-me"); // 쿼리 파라미터 또는 input name
        services.setTokenValiditySeconds(REMEMBER_ME_COOKIE_EXPIRED_TIME);
        return services;
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy(
        SessionRegistry sessionRegistry) {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry);
    }

    @Bean
    public CookieCsrfTokenRepository customCookieCsrfTokenRepository() {
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse(); // HttpOnly false
        repository.setCookieName("XSRF-TOKEN");           // 쿠키 이름
        repository.setHeaderName("X-XSRF-TOKEN");         // 헤더 이름
        return repository;
    }
}
