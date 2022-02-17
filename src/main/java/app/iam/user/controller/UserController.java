package app.iam.user.controller;

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
import app.iam.user.dto.EditUserDto;
import app.iam.user.dto.SearchUserDto;
import app.iam.user.dto.ViewListUserDto;
import app.iam.user.dto.ViewOneUserDto;
import app.iam.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;


//    @PreAuthorize("hasAuthority('USER:LIST')")
    @GetMapping
    public Page<ViewListUserDto> getAll(SearchUserDto search, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return userService.getAll(search, pageable);
    }

//    @PreAuthorize("hasAuthority('USER:VIEW')")
    @GetMapping("/{id}")
    public ViewOneUserDto getOne(@PathVariable("id") Long id){
        return userService.getOne(id);
    }



//    @PreAuthorize("hasAuthority('USER:CREATE')")
    @PostMapping
    public void create(@RequestBody EditUserDto newUser, HttpServletRequest httpServletRequest){
        userService.create(newUser, httpServletRequest.getRemoteAddr());
    }



}
