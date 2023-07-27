package uz.pdp.appnewsiteroles.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotNull(message = "fullName  bosh bolmasin")
    private String fullName;
    @NotNull(message = "parol username bosh bolmasin")
    private String username;
    @NotNull(message = "parol  bosh bolmasin")
    private String password;
    @NotNull(message = "parol takrori bosh bolmasin")
    private String prepassword;
}
