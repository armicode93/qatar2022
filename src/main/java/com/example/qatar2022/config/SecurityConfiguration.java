package com.example.qatar2022.config;

import com.example.qatar2022.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
    extends WebSecurityConfigurerAdapter { // this class overload config methods to config to prive
                                           // our costum configuration


  @Autowired private UserService userService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() // to encode password,criptage
      {
    return new BCryptPasswordEncoder();
  }

  // To integrate Spring data JPA and hybernate in Spring security we need to provide a bean
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
  }

  @Override // like this passiamo authentificationProvider to the config methode
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {


    auth.authenticationProvider(authenticationProvider());


  }


  @Override // we are going to override config methods
  protected void configure(HttpSecurity http) throws Exception { // we have a config method


    //http.csrf().disable(); // disable csrf
    http.authorizeRequests()
        .antMatchers(
            "/registration",
            "login",
            "index",
            "/",
            "framents/**",
            "partie",
            "partie/show",
            "equipe")
        .permitAll()


        // authenticate anyRequest to this url
        .and()
        .formLogin() // we are going to use a form login
        .loginPage("/login") // costum login page
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll() // al user can access to this url
        .and()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout")
        .permitAll(); // permit acces to this url

    http.authorizeRequests().antMatchers("/profile/**").authenticated();
    http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/equipe/edit/**").hasRole("ADMIN");
    //http.authorizeRequests().antMatchers("/equipe").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/partie").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/partie/edit/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/editResult/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/delete/**").hasRole("ADMIN");

    http.authorizeRequests().antMatchers("/reservation/**").hasAnyRole("USER", "ADMIN");
    http.authorizeRequests().antMatchers("/selectPlace/**").hasAnyRole("USER", "ADMIN");
    http.authorizeRequests().antMatchers("/reservationPartie/**").hasAnyRole("USER", "ADMIN");

    http.authorizeRequests().antMatchers("/ticket/**").hasAnyRole("USER", "ADMIN");

    http.authorizeRequests().antMatchers("/edit").hasRole("ADMIN");

    http.authorizeRequests().antMatchers("/users").hasAnyRole("ADMIN");

    http.exceptionHandling()
        .accessDeniedPage(
            "/403"); // redirection quando non possiamo accedere ad una pagina offlimits,
  }



}
