package uz.pdp.appnewsiteroles.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appnewsiteroles.entity.enums.Huquq;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class RoleDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotEmpty
    private List<Huquq> huquqList;
}
