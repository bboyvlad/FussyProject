package jd.security;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;



@Component
public class PrincipalAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

    @Autowired
    private PrincipalUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails principalDetails, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {


        if(token.getCredentials()==null || principalDetails.getPassword()==null){
            System.out.println("Credentials may not be null.");
            throw new BadCredentialsException("Credentials may not be null.");
        }

        if(!passwordEncoder.matches((String) token.getCredentials(),principalDetails.getPassword())){
            System.out.println("Invalid Credentials");
            throw new BadCredentialsException("Invalid Credentials");
        }

    }

    @Override
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {

        UserDetails principalDetails=userDetailsService.loadUserByUsername(email);
        if(principalDetails==null){

            throw new UsernameNotFoundException(
                    "Account " + email + " not found.");
        }
        return principalDetails;
    }

    public String encodePassword(String pass){
        return passwordEncoder.encode(pass);
    }
}
