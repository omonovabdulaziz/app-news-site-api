package uz.pdp.appnewsiteroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appnewsiteroles.entity.Comment;
import uz.pdp.appnewsiteroles.entity.Post;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.payload.CommentDTO;
import uz.pdp.appnewsiteroles.repository.CommentRepository;
import uz.pdp.appnewsiteroles.repository.PostRepository;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public ApiResponse addComment(CommentDTO commentDTO) {
        Optional<Post> optionalPost = postRepository.findById(commentDTO.getPostId());
        if (!optionalPost.isPresent())
            return new ApiResponse("Post Topilmadi", false);
        Comment comment = new Comment(commentDTO.getText(), optionalPost.get());
        commentRepository.save(comment);
        return new ApiResponse("comment added", true);

    }


    public Page<Comment> findbyPostId(int page, int count, Integer postId) {
        Pageable pageable = PageRequest.of(page, count);
        return commentRepository.findByPostId(Long.valueOf(postId), pageable);
    }

    public ApiResponse updateComment(Integer commentId, CommentDTO commentDTO) {
        Optional<Post> optionalPost = postRepository.findById(commentDTO.getPostId());
        if (!optionalPost.isPresent())
            return new ApiResponse("Post Topilmadi", false);
        Comment editComment = commentRepository.findById(Long.valueOf(commentId)).get();
        editComment.setPost(optionalPost.get());
        editComment.setText(commentDTO.getText());
        editComment.setId(Long.valueOf(commentId));
        commentRepository.save(editComment);
        return new ApiResponse("comment updated", true);
    }

    public ApiResponse deleteComment(Long id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("deleted", true);

        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }
}
