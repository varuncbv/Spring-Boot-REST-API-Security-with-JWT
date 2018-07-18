package com.letsstartcoding.springbootrestapijwt.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class JWTConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	

	@Value("${varun.oauth.tokenTimeout:3600}")
	private int expiration;
	
	@Autowired
    private DataSource dataSource;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	
	private String signingKey;
	
	@Autowired
	private TokenStore tokenStore;


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("jwtdemo123");
		return converter;
	}
	
	
	

	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
		
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		List list=Arrays.asList(accessTokenConverter);
		enhancerChain.setTokenEnhancers(list);
	   
	      
		configurer.tokenStore(tokenStore)
        .accessTokenConverter(accessTokenConverter)
        .tokenEnhancer(enhancerChain);
		configurer.authenticationManager(authenticationManager);
		configurer.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory().withClient("varun").secret("secret").accessTokenValiditySeconds(expiration)
				.scopes("read", "write").authorizedGrantTypes("password", "refresh_token").resourceIds("resource");
		
		
		/*if you want to make use of the jdbctokenstore,please make use of below code*/
		  //clients.jdbc(dataSource);
		
	}
	
	
}
