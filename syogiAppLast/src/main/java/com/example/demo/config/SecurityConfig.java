package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//SQLインジェクション（攻撃方法）
	//csrf
	//sessionID登頂みたいなやつ

	@Autowired
	private DataSource dataSource;

	private static final String USER_SQL = "select address, password, true from syogi.users where address = ?";

	private static final String ROLE_SQL = "select address, role from syogi.users where address = ?";

	@Override
	public void configure(WebSecurity web)throws Exception{

		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity http)throws Exception{

		http
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/loginInput").permitAll()
			.antMatchers("/profileInput").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/img/**").permitAll()
			.anyRequest().authenticated();


		http
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/lgoin")
				.failureUrl("/login")
				.usernameParameter("address")
				.passwordParameter("password")
				.defaultSuccessUrl("/profile",true);

		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login");

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{

		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			.passwordEncoder(passwordEncoder());
	}
}

