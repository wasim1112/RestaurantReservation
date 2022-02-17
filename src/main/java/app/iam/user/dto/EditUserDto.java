package app.iam.user.dto;


import lombok.Data;

import java.util.List;


@Data
public class EditUserDto {

    private String idNumber;
    private String username;
    private String mobileNumber;
    private String email;
    private List<Long> rolesIds;
}
