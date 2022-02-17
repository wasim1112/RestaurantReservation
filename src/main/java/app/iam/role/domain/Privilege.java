package app.iam.role.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
@Entity
public class Privilege {

    @Id
    @Column(name = "pk_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String nameAr;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "fk_privilege_group_id", nullable = false)
    private PrivilegeGroup group;
}
