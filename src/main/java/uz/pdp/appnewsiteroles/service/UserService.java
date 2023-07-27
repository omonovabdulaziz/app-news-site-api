package uz.pdp.appnewsiteroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appnewsiteroles.entity.Role;
import uz.pdp.appnewsiteroles.entity.User;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.UserDTO;
import uz.pdp.appnewsiteroles.repository.RoleRepository;
import uz.pdp.appnewsiteroles.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public Page<User> getAllPage(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        return userRepository.findAll(pageable);
    }

    public ApiResponse updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("updateBoluvchi user topilmadi", false);


        Optional<Role> optionalRole = roleRepository.findById(Long.valueOf(userDTO.getRoleId()));
        if (!optionalRole.isPresent())
            return new ApiResponse("Role Topilmadi", false);

        User user = optionalUser.get();
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setRole(optionalRole.get());
        user.setId(id);
        userRepository.save(user);
        return new ApiResponse("Updated", true);


    }

    public ApiResponse deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("Hammasi joyda o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("!xatolik", false);
        }
    }
}
