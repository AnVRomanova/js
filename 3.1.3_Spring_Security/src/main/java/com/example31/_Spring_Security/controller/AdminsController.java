package com.example31._Spring_Security.controller;


import com.example31._Spring_Security.model.Role;
import com.example31._Spring_Security.model.User;
import com.example31._Spring_Security.service.RoleService;
import com.example31._Spring_Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminsController {

    private final UserService service;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;

    }

    @GetMapping("/only_for_admins/getAll")
    public ResponseEntity<List<User>> adminsPage() {
        List<User> usersList = service.listUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    // Add new user
    @PostMapping("only_for_admins/add")
    public ResponseEntity<?> create2(@RequestBody User user) {
        service.add(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "redirect:/only_for_admins");
        return new ResponseEntity<>( headers,HttpStatus.OK);

    }

    // Edit user
    @PutMapping("only_for_admins/edit")
    public ResponseEntity<?> editUser(@RequestBody User user) {
        service.edit(user);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    //delete users
    @DeleteMapping("only_for_admins/{id}")
    public ResponseEntity<?> deletePage(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>(" deleted",HttpStatus.OK);
    }

    @GetMapping("/read_profile/get")
    public ResponseEntity<?> profilePage( Principal principal) {
        User user = service.findByName(principal.getName());
        return new ResponseEntity<>( user,HttpStatus.OK);
    }


    @GetMapping("/only_for_admins/get")
    public ResponseEntity<?> only_for_adminsGetUser( Principal principal) {
        User user = service.findByName(principal.getName());
        return new ResponseEntity<>( user,HttpStatus.OK);
    }
    @GetMapping("/only_for_admins/{id}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("id") long id) {
        User user = service.readUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Roles
    @GetMapping("only_for_admins/roles")
    public ResponseEntity<?> findAllRoles() {
        List<Role> allRoles = roleService.listRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

}
