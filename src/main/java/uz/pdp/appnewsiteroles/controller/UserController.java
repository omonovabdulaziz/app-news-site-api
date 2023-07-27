package uz.pdp.appnewsiteroles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewsiteroles.entity.User;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.RegisterDTO;
import uz.pdp.appnewsiteroles.payload.UserDTO;
import uz.pdp.appnewsiteroles.service.AuthService;
import uz.pdp.appnewsiteroles.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    @GetMapping
    public Page<User> getPageUser(@RequestParam int page , @RequestParam int count){
        return userService.getAllPage(page , count);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(Long id , UserDTO userDTO){
         ApiResponse apiResponse = userService.updateUser(id , userDTO);
         return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }


}
