package uz.pdp.appnewsiteroles.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appnewsiteroles.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTextAndTitle(String text, String title);


}
