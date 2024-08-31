package api.hotel.security;

import api.hotel.domain.User;
import api.hotel.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        User user = userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User has not been found"));

        CustomUserDetails customUserDetails =new CustomUserDetails();
        customUserDetails.setUser(user);

        return customUserDetails;
    }
}
