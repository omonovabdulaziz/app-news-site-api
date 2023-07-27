package uz.pdp.appnewsiteroles.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    @NotNull(message = "text kiritish shart")
    private String text;
    @NotNull(message = "postId kiritish shart")
    private Long postId;
}
