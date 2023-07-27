package uz.pdp.appnewsiteroles.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data@AllArgsConstructor@NoArgsConstructor
public class LoginDTO {

    @NotNull(message = "Username bosh bolmasin")
    private String username;
    @NotNull(message = "parol bosh bolmasin")
    private String password;
}
