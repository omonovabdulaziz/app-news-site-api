package uz.pdp.appnewsiteroles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewsiteroles.aop.CheckPermission;
import uz.pdp.appnewsiteroles.entity.Role;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.RoleDTO;

import uz.pdp.appnewsiteroles.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADD_LAVOZIM')")
    @PostMapping
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDTO roleDTO) {
        ApiResponse apiResponse = roleService.addRole(roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    //    @PreAuthorize(value = "hasAuthority('EDIT_LAVOZIM')")
    @CheckPermission(huquq = "hasAuthority('EDIT_LAVOZIM')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@Valid @RequestBody RoleDTO roleDTO, @PathVariable Integer id) {
        ApiResponse apiResponse = roleService.editRole(id, roleDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    @CheckPermission(huquq = "hasAuthority('VIEW_LAVOZIM')")
    @GetMapping
    public Page<Role> getPageRole(@RequestParam int page, @RequestParam int count) {
        return roleService.getPageRole(page, count);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_LAVOZIM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
     ApiResponse apiResponse =   roleService.deleteRole(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

}
