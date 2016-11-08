package jd.security;

import jd.persistence.model.Principal;
import jd.persistence.model.Role;
import jd.service.PrincipalserviceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by eduardom on 10/15/16.
 */
@Service
public class PrincipalUserDetailsService implements UserDetailsService {

    @Autowired
    private PrincipalserviceInt principalserviceInt;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Principal principal=principalserviceInt.findByEmail(email);

        if(principal==null){
            // Not found...
            System.out.println("User " + email + " not found.");
            throw new UsernameNotFoundException(
                    "User " + email + " not found.");
        }

        Collection<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();

        if (principal.getRoles() == null || principal.getRoles().isEmpty()) {
            // No Roles assigned to user...
            System.out.println("User not authorized.");
            throw new UsernameNotFoundException("User not authorized.");
        }

        for (Role role : principal.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        User principalDetails=new User(principal.getEmail(),
                principal.getPassword(),
                principal.isEnabled(),
                !principal.isExpired(),
                !principal.isCredentialsexpired(),
                !principal.isLocked(), grantedAuthorities);

        return principalDetails;
    }
}
