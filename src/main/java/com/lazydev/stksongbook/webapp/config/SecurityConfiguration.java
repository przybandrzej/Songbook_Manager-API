package com.lazydev.stksongbook.webapp.config;

import com.lazydev.stksongbook.webapp.security.jwt.JWTConfigurer;
import com.lazydev.stksongbook.webapp.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final UserDetailsService userDetailsService;
  private final TokenProvider tokenProvider;
  private final CorsFilter corsFilter;
  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuser;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String admin;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderator;
  @Value("${spring.flyway.placeholders.role.user}")
  private String user;

  public SecurityConfiguration(UserDetailsService userDetailsService, AuthenticationManagerBuilder authenticationManagerBuilder,
                               CorsFilter corsFilter, TokenProvider tokenProvider) {
    this.userDetailsService = userDetailsService;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.corsFilter = corsFilter;
    this.tokenProvider = tokenProvider;
  }

  @PostConstruct
  public void init() {
    try {
      authenticationManagerBuilder
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/app/**/*.{js,html}")
        .antMatchers("/i18n/**")
        .antMatchers("/content/**")
        .antMatchers("/swagger-ui/index.html")
        .antMatchers("/test/**");
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .and()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/register").permitAll()
        .antMatchers("/api/activate").permitAll()
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/is-authenticated").permitAll()
        .antMatchers("/api/account/reset-password/init").permitAll()
        .antMatchers("/api/account/reset-password/finish").permitAll()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .antMatchers("/api/songs/approve").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.PUT, "/api/authors/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.DELETE, "/api/authors/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.POST, "/api/categories/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.GET, "/api/user_roles/**").hasAnyAuthority(user)
        .antMatchers("/api/user_roles/**").hasAnyAuthority(superuser)
        .antMatchers(HttpMethod.PUT, "/api/tags/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers(HttpMethod.DELETE, "/api/tags/**").hasAnyAuthority(superuser, admin, moderator)
        .antMatchers("/api/**").authenticated()
        .and()
        .apply(securityConfigurerAdapter());
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
