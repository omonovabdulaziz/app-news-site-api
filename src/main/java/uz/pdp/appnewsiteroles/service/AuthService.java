package uz.pdp.appnewsiteroles.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appnewsiteroles.entity.User;
import uz.pdp.appnewsiteroles.exceptions.ResourceNotFoundException;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.LoginDTO;
import uz.pdp.appnewsiteroles.payload.RegisterDTO;
import uz.pdp.appnewsiteroles.repository.RoleRepository;
import uz.pdp.appnewsiteroles.repository.UserRepository;
import uz.pdp.appnewsiteroles.security.JwtProwider;
import uz.pdp.appnewsiteroles.utils.AppConstants;

import java.io.Serializable;
import java.util.Optional;

@Service

public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProwider jwtProwider;

    public ApiResponse registerUser(RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getPrepassword()))
            return new ApiResponse("Parollar mos emas", false);

        if (userRepository.existsByUsername(registerDTO.getUsername()))
            return new ApiResponse("budnay user mavjud ", false);

        User user = new User(
                registerDTO.getFullName(),
                registerDTO.getUsername(),
                passwordEncoder.encode(registerDTO.getPassword()),
                roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException("Lavozim", "name", AppConstants.USER)),
                true
        );
        userRepository.save(user);
        return new ApiResponse("Muvafaqiyatli royxattan o'tidingiz", true);
    }

    public UserDetails loadUserByUsername(String userNameFromToken) {
        return userRepository.findByUsername(userNameFromToken).orElseThrow(() -> new UsernameNotFoundException(userNameFromToken));
    }

    public ApiResponse loginUser(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        User user = (User) authenticate.getPrincipal();
        String token = jwtProwider.generateToken(user.getUsername(), user.getRole());
        return new ApiResponse(token, true);
    }
}
