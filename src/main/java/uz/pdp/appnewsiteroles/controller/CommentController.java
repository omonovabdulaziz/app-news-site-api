package uz.pdp.appnewsiteroles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appnewsiteroles.entity.Comment;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.CommentDTO;
import uz.pdp.appnewsiteroles.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize(value = "hasAuthority('ADD_COMMENT')")
    @PostMapping
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.addComment(commentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_COMMENT')")
    @GetMapping("/{postId}")
    public Page<Comment> getPageComment(@PathVariable Integer postId, @RequestParam int page, @RequestParam int count) {
        return commentService.findbyPostId(page, count, postId);
    }

    @PreAuthorize(value = "hasAnyAuthority('EDIT_COMMENT' , 'EDIT_MY_COMMENT')")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable Integer commentId, @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETE_COMMENT' , 'DELETE_MY_COMMENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        ApiResponse apiResponse = commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(apiResponse);
    }

}
