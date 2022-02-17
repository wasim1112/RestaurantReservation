package app.iam.user.domain;

import app.general.common.domain.AuditingEntity;
import app.iam.role.domain.Role;
import app.iam.user.enumeration.UserStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Setter
@Getter
@Table(name = "USERS")
@Entity
public class User extends AuditingEntity implements UserDetails {

    @Id
    @Column(name = "pk_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JoinTable(joinColumns = @JoinColumn(name = "fk_user_id"), inverseJoinColumns= @JoinColumn(name = "fk_role_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private String mobileNumber;

    @Size(max = 100)
    @Column(nullable = false)
    private String email;

    private String managerUsername;


    @Column(nullable = false)
    private Boolean isLocked = false;


    public Set<String> getPrivileges() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public boolean hasPrivilege(String privilege){
        return getPrivileges().contains(privilege);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.stream().filter(r -> !getIsDeleted()).forEach(role -> role.getPrivileges().forEach(privilege -> authorities.add(privilege::getCode)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }

    @Override
    public boolean equals(Object otherUser) {
        if(otherUser == null) return false;
        else if (!(otherUser instanceof UserDetails)) return false;
        else return (otherUser.hashCode() == hashCode());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}
