package app.iam.role.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.iam.role.domain.Privilege;
import app.iam.role.domain.Role;
import app.iam.role.dto.EditRoleDto;
import app.iam.role.service.RoleService;

import java.util.Set;


@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    @PreAuthorize("hasAuthority('ROLE:LIST')")
    @GetMapping
    public Page<Role> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return roleService.getAll(pageable);
    }

//    @PreAuthorize("hasAuthority('ROLE:VIEW')")
    @GetMapping("/{id}")
    public Role get(@PathVariable("id") Long id){
        return roleService.get(id);
    }

//    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    @PostMapping
    public void create(@RequestBody EditRoleDto dto){
        roleService.create(dto);
    }

//    @PreAuthorize("hasAuthority('ROLE:EDIT')")
    @PostMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody EditRoleDto dto){
        roleService.update(id, dto);
    }

//    @PreAuthorize("hasAuthority('ROLE:LOCK')")
    @PostMapping("/lock/{id}")
    public void lock(@PathVariable("id") Long id){
        // TODO: todo
    }

//    @PreAuthorize("hasAuthority('ROLE:DELETE')")
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        roleService.delete(id);
    }

//    @PreAuthorize("hasAnyAuthority('USER:VIEW', 'USER:EDIT')")
    @GetMapping("/rolesPool/{userId}")
    public Set<Role> getRolesPool(@PathVariable Long userId){
        return roleService.getRolesPool(userId);
    }

//    @PreAuthorize("hasAnyAuthority('ROLE:VIEW', 'ROLE:EDIT')")
    @GetMapping("/privilegesPool/{userId}")
    public Set<Privilege> getPrivilegesPool(@PathVariable Long userId){
        return roleService.getPrivilegesPool(userId);
    }
}
