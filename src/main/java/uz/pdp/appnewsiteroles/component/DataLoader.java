package uz.pdp.appnewsiteroles.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appnewsiteroles.entity.Role;
import uz.pdp.appnewsiteroles.entity.User;
import uz.pdp.appnewsiteroles.entity.enums.Huquq;
import uz.pdp.appnewsiteroles.repository.RoleRepository;
import uz.pdp.appnewsiteroles.repository.UserRepository;
import uz.pdp.appnewsiteroles.utils.AppConstants;

import java.util.Arrays;

import static uz.pdp.appnewsiteroles.entity.enums.Huquq.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value(value = "${spring.datasource.initialization-mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Huquq[] huquqs = Huquq.values();

            Role admin = roleRepository.save(new Role(AppConstants.ADMIN, Arrays.asList(huquqs) , "Admin sistema egasi"));
            Role user = roleRepository.save(new Role(AppConstants.USER, Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT , VIEW_POST) , "oddiy foydalnuvchi"));
            userRepository.save(new User("Admin", "admin", passwordEncoder.encode("admin123"), admin, true));
            userRepository.save(new User("User", "user", passwordEncoder.encode("user123"), user, true));
        }
    }
}
