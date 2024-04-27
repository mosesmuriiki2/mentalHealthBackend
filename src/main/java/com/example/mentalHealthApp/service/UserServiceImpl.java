package com.example.mentalHealthApp.service;

import com.example.mentalHealthApp.entity.Roles;
import com.example.mentalHealthApp.entity.Users;
import com.example.mentalHealthApp.models.UserDto;
import com.example.mentalHealthApp.repository.RoleRepository;
import com.example.mentalHealthApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto){
        Users users = new Users();
        users.setName(userDto.getFirstName() +" " + userDto.getLastName());
        users.setEmail(userDto.getEmail());
        //encode password
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Roles roles = roleRepository.findByName("ROLE_ADMIN");

        if (roles == null){
            roles = checkRoleExists();
        }
        users.setRoles(Arrays.asList(roles));
        userRepository.save(users);
    }
    @Override
    public Users  findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public List<UserDto> findAllUsers(){
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(Users users){
        UserDto userDto = new UserDto();
        String[] str = users.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(users.getEmail());
        return userDto;
    }
    private Roles checkRoleExists(){
        Roles roles = new Roles();
        roles.setName("ROLE_ADMIN");
        //roles.setUsers();
        return roleRepository.save(roles);
    }
}
