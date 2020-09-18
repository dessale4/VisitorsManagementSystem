package com.desale.visitorsManagementSystem.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desale.visitorsManagementSystem.exception.custom.AuthenticationException;
import com.desale.visitorsManagementSystem.util.JwtTokenUtil;



@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("Authentication Request For '{}'", request.getRequestURL());
		
		//get token from request headr if it exists
		final String requestTokenHeader = request.getHeader(this.tokenHeader);

		String username = null;
		if (requestTokenHeader != null) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
			} catch (Exception e) {
				logger.error("JWT_Exception", e.getMessage());
				throw new AuthenticationException("INVALID_CREDENTIALS");
			}
		}

		logger.debug("JWT_TOKEN_USERNAME_VALUE '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//UserDetails Provides core user information.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			//validate token of user
			if (jwtTokenUtil.validateToken(requestTokenHeader, userDetails)) {// then principal
				//UsernamePasswordAuthenticationToken is An org.springframework.security.core.Authentication implementation that is designed for simple presentation of a username and password.
				//The principal and credentials should be set with an Object that provides the respective property via its Object.toString() method. The simplest such Object to use is String.
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());//arguments pricipal, credentials and roles respectively
				//authenticate user
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//save authentication in application context
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		//continue to other filters
		chain.doFilter(request, response);
	}

}
