package uz.pdp.appnewsiteroles.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnewsiteroles.entity.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment , Long> {
    Page<Comment> findByPostId(Long post_id, Pageable pageable);
}
