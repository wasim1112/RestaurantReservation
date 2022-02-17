package app.iam.user.service;

import app.general.common.utils.GeneralUtils;
import app.general.common.utils.Generators;
import app.general.common.utils.SecurityUtils;
import app.general.common.utils.Validators;
import app.iam.role.domain.Role;
import app.iam.role.repository.RoleRepository;
import app.iam.role.service.RoleService;
import app.iam.user.domain.User;
import app.iam.user.dto.EditUserDto;
import app.iam.user.dto.SearchUserDto;
import app.iam.user.dto.ViewListUserDto;
import app.iam.user.dto.ViewOneUserDto;
import app.iam.user.enumeration.UserStatus;
import app.iam.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;



    @Autowired
    private Validators validators;

    @Autowired
    private Generators generators;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private ModelMapper modelMapper;


    public Page<ViewListUserDto> getAll(SearchUserDto search, Pageable pageable){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Specification<User> spec = Specification.where((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), false));
            predicates.add(cb.notEqual(root.get("username"), "admin"));
            if(validators.isNotEmpty(search.getManagerUsername())){
                predicates.add(cb.like(root.get("managerUsername"), search.getManagerUsername()));
            }

            if(validators.isNotEmpty(search.getUsername())){
                predicates.add(cb.like(root.get("username"), "%"+search.getUsername()+"%"));
            }
            if(search.getMobileNumber() != null){
                predicates.add(cb.like(root.get("mobileNumber"), "%"+search.getMobileNumber()+"%"));
            }
            if(validators.isNotEmpty(search.getEmail())){
                predicates.add(cb.like(root.get("email"), "%"+search.getEmail()+"%"));
            }
            return cb.and(predicates.toArray(new Predicate[] {}));
        });
        return userRepository.findAll(spec, pageable).map(u -> modelMapper.map(u, ViewListUserDto.class));
    }

    public ViewOneUserDto getOne(Long id){
        User user = userRepository.findByIdAndIsDeletedFalse(id);
        return modelMapper.map(user, ViewOneUserDto.class);
    }



    public void create(EditUserDto dto, String ipAddress){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!validators.isValidUsername(dto.getUsername())){
            throw new RuntimeException("errors.sr.invalidUsername");
        }
        if(!validators.isValidMobileNumber(dto.getMobileNumber())){
            throw new RuntimeException("errors.sr.invalidMobile");
        }
        if(dto.getEmail() != null && !validators.isValidEmail(dto.getEmail())){
            throw new RuntimeException("errors.sr.invalidEmail");
        }

        // security
        Set<Long> rolesPool = roleService.getRolesPool(0L).stream().map(Role::getId).collect(Collectors.toSet());
        SecurityUtils.assertListIsSubListOfList(dto.getRolesIds(), rolesPool);

        // validate username
        if(userRepository.findByUsernameAndIsDeletedFalse(dto.getUsername()) != null){
            throw new RuntimeException("errors.sr.usernameTaken");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());
        user.setIsLocked(false);
        user.setManagerUsername(loggedInUser.getUsername());
        String newPassword = generators.randomPassword(10, false);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRoles(new HashSet<>(roleRepository.findAllById(dto.getRolesIds())));
        userRepository.save(user);
    }

}
