package app.iam.role.dto;
import lombok.Data;

import java.util.List;


@Data
public class EditRoleDto {
    private String name;
    private List<Long> privilegesIds;
}
