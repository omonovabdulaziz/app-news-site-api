package uz.pdp.appnewsiteroles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appnewsiteroles.entity.Post;
import uz.pdp.appnewsiteroles.payload.ApiResponse;
import uz.pdp.appnewsiteroles.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;


    public ApiResponse addPost(Post post) {
        if (postRepository.existsByTextAndTitle(post.getText(), post.getTitle()))
            return new ApiResponse("bunday textli title mavjud 2kinchi marta qoshish shart emas", false);

        Post savePost = postRepository.save(post);
        return new ApiResponse("saqlandi id si " + savePost.getId(), true);

    }


    public Page<Post> getAllPost(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        return postRepository.findAll(pageable);
    }

    public ApiResponse updatePost(Long id, Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (!optionalPost.isPresent())
            return new ApiResponse("post topilmadi", false);
        if (postRepository.existsByTextAndTitle(post.getText(), post.getTitle()))
            return new ApiResponse("bunday textli title mavjud 2kinchi marta qoshish shart emas", false);


        Post editePost = optionalPost.get();

        editePost.setText(post.getText());
        editePost.setUrl(post.getUrl());
        editePost.setTitle(post.getTitle());
        editePost.setId(id);
        postRepository.save(editePost);
        return new ApiResponse("updated", true);
    }

    public ApiResponse deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik ", false);
        }
    }
}
