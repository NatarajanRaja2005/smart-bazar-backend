package com.projoker.smart_bazar.security.user;

import com.projoker.smart_bazar.Repository.UserRepository;
import com.projoker.smart_bazar.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));

        return ShopUserDetails.buildUserDetails(user);
    }
}
