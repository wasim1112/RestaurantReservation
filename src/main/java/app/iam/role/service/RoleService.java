package app.iam.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import app.general.common.utils.SecurityUtils;
import app.general.common.utils.Validators;
import app.iam.role.domain.Role;
import app.iam.role.repository.PrivilegeRepository;
import app.iam.role.repository.RoleRepository;
import app.iam.user.domain.User;
import app.iam.user.repository.UserRepository;
import app.iam.role.domain.Privilege;
import app.iam.role.dto.EditRoleDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validators validators;
    @Cacheable(cacheNames = "RoleList")
    public Page<Role> getAll(Pageable pageable){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return roleRepository.findAllByCreatedBy(loggedInUser.getId().toString(), pageable);
    }
    @Cacheable(cacheNames = "OneRole")
    public Role get(Long id){
        Role role = roleRepository.findById(id).get();
        assertCanManage(role);
        return role;
    }

    public void create(EditRoleDto dto){
        if(!validators.isNotEmpty(dto.getName())){
            throw new RuntimeException("errors.rm.invalidName");
        }
        if(dto.getPrivilegesIds().isEmpty()){
            throw new RuntimeException("errors.rm.emptyPrivileges");
        }

        Set<Long> privilegesPool = getPrivilegesPool(0L).stream().map(Privilege::getId).collect(Collectors.toSet());
        SecurityUtils.assertListIsSubListOfList(dto.getPrivilegesIds(), privilegesPool);


        Role role = new Role();
        role.setName(dto.getName());
        role.setPrivileges(new HashSet<>(privilegeRepository.findAllById(dto.getPrivilegesIds())));

        roleRepository.save(role);
    }

    public void update(Long id, EditRoleDto dto){
        Role repoRole = roleRepository.findById(id).get();
        assertCanManage(repoRole);

        if(!validators.isNotEmpty(dto.getName())){
            throw new RuntimeException("errors.rm.invalidName");
        }
        if(dto.getPrivilegesIds().isEmpty()){
            throw new RuntimeException("errors.rm.emptyPrivileges");
        }

        Set<Long> privilegesPool = getPrivilegesPool(repoRole.getId()).stream().map(Privilege::getId).collect(Collectors.toSet());
        SecurityUtils.assertListIsSubListOfList(dto.getPrivilegesIds(), privilegesPool);

        repoRole.setName(dto.getName());
        repoRole.setPrivileges(new HashSet<>(privilegeRepository.findAllById(dto.getPrivilegesIds())));

        roleRepository.save(repoRole);
    }

    public void delete(Long id){
        Role repoRole = roleRepository.findById(id).get();
        assertCanManage(repoRole);

        List<User> users = userRepository.findAllByRolesContainsAndIsDeletedFalse(repoRole);
        if(!users.isEmpty()){
            throw new RuntimeException("errors.rm.unableToDelete");
        }


        roleRepository.delete(repoRole);
    }
    @Cacheable(cacheNames = "RolesPool")
    public Set<Role> getRolesPool(Long userId){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userId == null || userId == 0){ // create new user
            return new HashSet<>(roleRepository.findAllByCreatedBy(loggedInUser.getId().toString()));
        }else{
            User user = userRepository.findById(userId).get();
            User manager = userRepository.findByUsernameAndIsDeletedFalse(user.getManagerUsername());
            Set<Role> roles = new HashSet<>(roleRepository.findAllByCreatedBy(manager.getId().toString()));


            return roles;
        }
    }

    public Set<Privilege> getPrivilegesPool(Long roleId){
        final Set<Privilege> privileges = new HashSet<>();

        if(roleId == null || roleId == 0){ // create new role
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loggedInUser.getRoles().forEach(r -> privileges.addAll(r.getPrivileges()));
        }else{
            Role repoRole = roleRepository.findById(roleId).get();
            User roleCreator = userRepository.findById(Long.valueOf(repoRole.getCreatedBy())).get();
            roleCreator.getRoles().forEach(r -> privileges.addAll(r.getPrivileges()));
        }

        return privileges;
    }

    private void assertCanManage(Role role){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Long.valueOf(role.getCreatedBy()).equals(loggedInUser.getId())){
            throw new RuntimeException("errors.general.notAuthorized");
        }
    }
}
