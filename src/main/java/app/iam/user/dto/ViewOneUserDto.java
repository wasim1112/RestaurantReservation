package app.iam.user.dto;

import lombok.Data;
import app.iam.role.domain.Role;
import app.iam.user.enumeration.UserStatus;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
public class ViewOneUserDto {
    private Long id;
    private String username;
    private Long mobileNumber;
    private String email;
    private Boolean isLocked = false;

    private Set<Role> roles = new HashSet<>();
    private Instant createdAt;
    private Instant lastModifiedAt;
    private Set<String> privileges;
}
