package app.iam.role.domain;

import lombok.Getter;
import lombok.Setter;
import app.general.common.domain.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
public class Role extends AuditingEntity {

    @Id
    @Column(name = "pk_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @JoinTable(joinColumns = @JoinColumn(name = "fk_role_id"), inverseJoinColumns= @JoinColumn(name = "fk_privilege_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Privilege> privileges = new HashSet<>();

}
