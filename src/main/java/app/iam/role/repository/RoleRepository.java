package app.iam.role.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import app.iam.role.domain.Role;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Page<Role> findAllByCreatedBy(String creatorPkId, Pageable pageable);
    List<Role> findAllByCreatedBy(String creatorPkId);




}
