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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { //this class overload config methods to config to prive our costum configuration

  /*  @Autowired
    private DataSource dataSource;

   */



    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() // to encode password,criptage
    {
        return new BCryptPasswordEncoder();
    }



    //To integrate Spring data JPA and hybernate in Spring security we need to provide a bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    @Override// like this passiamo authentificationProvider to the config methode
    protected void configure (AuthenticationManagerBuilder auth) throws  Exception{

        auth.authenticationProvider(authenticationProvider());
      /*  auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal ,password as credentials ,true from users where username=?")
                //con principal e credentials diciamo a spring che 1 e username e l'altro e password
                //requette pour cherche le user
                .authoritiesByUsernameQuery("select r.role as role ,u.username as principal FROM role_user ru,roles r,users u where r.id = ru.role_id AND ru.user_id = u.id and u.username=?")
                //pour recuperer le role de l'utilisateur
                .passwordEncoder(passwordEncoder())
                .rolePrefix("ROLE_");

       */


    }


    @Override // we are going to override config methods
    protected  void configure(HttpSecurity http) throws Exception{ //we have a config method



        http.authorizeRequests().antMatchers(
                        "/registration", // we have provided the acces to the different url
                        "login",
                        "index",
                        "/",
                "framents/**",
                "partie",
                "partie/show",
                        "equipe"
                ).permitAll()

                //authenticate anyRequest to this url
                .and()
                .formLogin()// we are going to use a form login
                .loginPage("/login") //costum login page
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()//al user can access to this url
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher( new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll(); // permit acces to this url



        http.authorizeRequests() .antMatchers("/profile/**").authenticated();
       //http.authorizeRequests() .antMatchers("/admin/**").hasRole("ADMIN");
       // http.authorizeRequests() .antMatchers("/equipe/edit").hasRole("ADMIN");
       // http.authorizeRequests() .antMatchers("/equipe/add").hasRole("ADMIN");
       // http.authorizeRequests() .antMatchers("/partie/add").hasRole("ADMIN");
        //http.authorizeRequests() .antMatchers("/partie/edit").hasRole("ADMIN");

        //http.authorizeRequests() .antMatchers("/partie/editResultForm").hasRole("ADMIN");
        //http.authorizeRequests() .antMatchers("/reservation/**").hasRole("USER");
        //http.authorizeRequests() .antMatchers("/ticket/**").hasRole("USER");


        //http.authorizeRequests() .antMatchers("//edit").hasRole("ADMIN");


        //http.authorizeRequests() .antMatchers("v1/public/users").hasAnyRole("ADMIN");





        http.exceptionHandling().accessDeniedPage("/403"); //redirection quando non possiamo accedere ad una pagina offlimits,

    }
}






















    /*   private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }





    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html","/403.html","/fragments/**").permitAll()
                .antMatchers("/403.html").permitAll()
                .antMatchers("/partie/**").permitAll()


                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")

                .antMatchers("v1/public/users").hasAnyRole("ADMIN")

                .and()
                .formLogin()
                .loginProcessingUrl("/signin")
                .loginPage("/login").permitAll()
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");

        30 days, the cookie can be value until 30 days
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

 */