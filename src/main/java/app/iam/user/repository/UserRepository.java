package app.iam.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import app.iam.role.domain.Role;
import app.iam.user.domain.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByIdAndIsDeletedFalse(Long id);
    User findByUsernameAndIsDeletedFalse(String username);
    List<User> findAllByRolesContainsAndIsDeletedFalse(Role role);
}
