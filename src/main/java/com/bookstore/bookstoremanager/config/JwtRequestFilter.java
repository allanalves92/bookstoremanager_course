package com.bookstore.bookstoremanager.config;

import com.bookstore.bookstoremanager.users.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired private AuthenticationService authenticationService;

  @Autowired private JwtTokenManager jwtTokenManager;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    var username = "";
    var jwtToken = "";

    var requestTokenHeader = request.getHeader("Authorization");

    if (isTokenPresent(requestTokenHeader)) {
      jwtToken = requestTokenHeader.substring(7);
      username = jwtTokenManager.getUsernameFromToken(jwtToken);
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }

    if (isUsernameInContext(username)) {
      addUsernameInContext(request, username, jwtToken);
    }

    chain.doFilter(request, response);
  }

  private boolean isTokenPresent(String requestTokenHeader) {
    return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ");
  }

  private boolean isUsernameInContext(String username) {
    return !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {
    UserDetails userDetails = authenticationService.loadUserByUsername(username);
    if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }
}
