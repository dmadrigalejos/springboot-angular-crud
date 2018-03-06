package com.wenkaru.springbootangularjscrud.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wenkaru.springbootangularjscrud.model.User;
import com.wenkaru.springbootangularjscrud.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginResource {
	protected final static Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);
	
	@Autowired
    private UserService userService;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> login(@RequestBody(required = false) User user, HttpSession httpSession) throws Exception {
        try {
            User userCred = new User();
            userCred = userService.get(user.getUsername());
            
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = userCred.getPassword();
            
            if (!passwordEncoder.matches(user.getPassword(), hashedPassword)) {
                return ResponseEntity.badRequest().body(new User() {
                    {
                        setUsername("Invalid Employee ID or Password");
                    }
                });
            } else {
                userCred.setPassword("");
            }
            
            List<String> userRoles = userService.getRoles(user.getUsername());
            Set<SimpleGrantedAuthority> authorities = new HashSet<>(userRoles.size());
            
            for (String userRole : userRoles) {
                authorities.add(new SimpleGrantedAuthority(userRole));
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(userCred, null, authorities);
            SecurityContext c = SecurityContextHolder.getContext();
            c.setAuthentication(authentication);

            httpSession.setAttribute("USERINFO", getCurrentUserLogin());
            httpSession.setMaxInactiveInterval(60 * 15);

            User loggedInUser = getCurrentUserLogin();
            return ResponseEntity.ok().body(loggedInUser);
        } catch (Exception e) {
            LOGGER.error("Unable to login", e);

            return ResponseEntity.badRequest().body(new User());
        }
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> authenticate(HttpSession httpSession) {
        try {
            if (httpSession.getAttribute("USERINFO") != null) {
                return ResponseEntity.ok().body((User) httpSession.getAttribute("USERINFO"));
            } else {
                return ResponseEntity.ok().body(new User());
            }

        } catch (Exception e) {
            LOGGER.error("No User found");
            return ResponseEntity.ok().body(new User());
        }
    }
	
	@RequestMapping(value = "/invalidate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok().body(new User());
    }
	
	public static User getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {

                return (User) authentication.getPrincipal();
            }
        }
        throw new AuthenticationCredentialsNotFoundException("No user currently logged in");
    }
}
