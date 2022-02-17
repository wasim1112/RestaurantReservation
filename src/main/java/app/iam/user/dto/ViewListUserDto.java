package app.iam.user.dto;
import lombok.Data;
import app.iam.user.enumeration.UserStatus;


import java.time.Instant;


@Data
public class ViewListUserDto {
    private Long id;
    private String username;
    private Instant createdAt;
    private Boolean isLocked = false;
}
