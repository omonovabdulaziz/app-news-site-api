package uz.pdp.appnewsiteroles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewsiteroles.entity.Post;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;


    //create
    @PreAuthorize(value = "hasAuthority('ADD_POST')")
    @PostMapping
    public ResponseEntity<?> addPost(@Valid @RequestBody Post post) {
        ApiResponse apiResponse = postService.addPost(post);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    //READ
    @PreAuthorize(value = "hasAuthority('VIEW_POST')")
    @GetMapping
    public Page<Post> getPost(@RequestParam int page, @RequestParam int count) {
        return postService.getAllPost(page, count);
    }

    //UPDATE
    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('EDIT_POST')")
    public ResponseEntity<?> editPost(@Valid @PathVariable Long id, @RequestBody Post post) {
        ApiResponse apiResponse = postService.updatePost(id, post);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    //deletePost
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('DELETE_POST')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        ApiResponse apiResponse = postService.deletePost(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
