package app.iam.user.dto;

import lombok.Data;


@Data
public class SearchUserDto {

    private String idNumber;
    private String nationalityCode;
    private String username;
    private String mobileNumber;
    private String email;
    private String managerUsername;
}
