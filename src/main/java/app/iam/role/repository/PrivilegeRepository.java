package app.iam.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.iam.role.domain.Privilege;


public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
