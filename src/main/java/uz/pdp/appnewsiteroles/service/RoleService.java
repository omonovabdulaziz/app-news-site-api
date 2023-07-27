package uz.pdp.appnewsiteroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.appnewsiteroles.entity.Role;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.RoleDTO;
import uz.pdp.appnewsiteroles.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName()))
            return new ApiResponse("Bunday lavozim bor ", false);

        Role role = new Role(
                roleDTO.getName(),
                roleDTO.getHuquqList(),
                roleDTO.getDescription()
        );
        roleRepository.save(role);

        return new ApiResponse("saqlandi ", true);
    }


    public ApiResponse editRole(Integer id, RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleRepository.findById(Long.valueOf(id));
        if (!optionalRole.isPresent())
            return new ApiResponse("Role yoq", false);
        if (roleRepository.existsByName(roleDTO.getName()))
            return new ApiResponse("Bunday lavozim bor ", false);


        Role role = new Role(
                roleDTO.getName(),
                roleDTO.getHuquqList(),
                roleDTO.getDescription()
        );
        roleRepository.save(role);
        return new ApiResponse("updated", true);
    }

    public Page<Role> getPageRole(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        return roleRepository.findAll(pageable);
    }

    public ApiResponse deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("!Xatolik", false);
        }
    }
}
