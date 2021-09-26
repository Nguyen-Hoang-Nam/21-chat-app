package chat.chat.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import chat.chat.service.IUserService;
import chat.chat.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private IUserService userService;

    private Environment env;

    private BCryptPasswordEncoder encoder;

    private JwtUtil jwtUtil;

    public WebSecurityConfig(IUserService userService, Environment env, BCryptPasswordEncoder encoder,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.env = env;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // @Override
    // public void configure(WebSecurity web) throws Exception {
    // web.ignoring().antMatchers("/actuator/**");
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://127.0.0.1:3000"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setExposedHeaders(List.of("token"));
            return cors;
        }).and().authorizeRequests().antMatchers("/user/**").permitAll().antMatchers("/chat/**").permitAll()
                .antMatchers("/ws").permitAll().anyRequest().authenticated().and().addFilter(getAuthenticationFilter());

        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, jwtUtil,
                authenticationManager());

        authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }
}
