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

    http.authorizeRequests().antMatchers("/partie").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/partie/edit/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/editResult/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/delete/**").hasRole("ADMIN");
 
    http.authorizeRequests().antMatchers("/joueurDelete/**").hasRole("ADMIN");

    http.authorizeRequests().antMatchers("/addJoueur/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/addPosteEq1/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/addJoueur/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/addPosteEq2/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/deletePosteEq1/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/deletePosteEq2/**").hasRole("ADMIN");

    http.authorizeRequests().antMatchers("/createParties").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/createNextParties").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/roles/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/equipe/add").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/equipe").permitAll();
    http.authorizeRequests().antMatchers("/equipeDelete/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/pay/**").authenticated();
    http.authorizeRequests().antMatchers("/sendTicket/**").authenticated();
    http.authorizeRequests().antMatchers("/qrCode/**").authenticated();

    http.authorizeRequests().antMatchers("/changerStatutEq1/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/changerStatutEq2/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/addPosteEq1/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers("/addPosteEq2/**").hasRole("ADMIN");





    http.authorizeRequests().antMatchers("/reservation/**").authenticated();
    http.authorizeRequests().antMatchers("/selectPlace/**").authenticated();
    http.authorizeRequests().antMatchers("/reservationPartie/**").authenticated();
    http.authorizeRequests().antMatchers("/pay/**").authenticated();
    http.authorizeRequests().antMatchers("/sendTicket/**").authenticated();
    http.authorizeRequests().antMatchers("/qrCode/**").authenticated();


    http.authorizeRequests().antMatchers("/ticket/**").authenticated();

    http.authorizeRequests().antMatchers("/edit").hasRole("ADMIN");



    http.exceptionHandling()
        .accessDeniedPage(
            "/403");
  }



}
