package uz.pdp.appnewsiteroles.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appnewsiteroles.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends AbstractEntity {
    @Column(nullable = false, columnDefinition = "text")
    @NotNull(message = "title kiritish shart")
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    @NotNull(message = "text kiritish shart")
    private String text;
    @Column(nullable = false, columnDefinition = "text")
    @NotNull(message = "url kiritish shart")
    private String url;
}
