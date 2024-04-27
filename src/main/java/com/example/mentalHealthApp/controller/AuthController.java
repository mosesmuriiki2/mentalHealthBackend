package com.example.mentalHealthApp.controller;

import com.example.mentalHealthApp.entity.Users;
import com.example.mentalHealthApp.models.UserDto;
import com.example.mentalHealthApp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    //handler method to handle user registration form request
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }
    //saving of user registration details
    @RequestMapping(value = "/register/save", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user")   @RequestBody UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        // Validate the userDto object
        if (bindingResult.hasErrors()) {
            return "register"; // Return the registration form page with validation errors
        }

        // Check if the user with the provided email already exists
        Users existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            bindingResult.rejectValue("email", null, "There is already an account with the same email");
            return "register"; // Return the registration form page with the error message
        }

        // Save the user
        userService.saveUser(userDto);
        return "redirect:/login"; // Redirect to a success page or another URL
    }

    //display all users
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) throws JsonProcessingException {
        List<UserDto> userDtos = userService.findAllUsers();
        model.addAttribute("users", userDtos);
        model.addAttribute("userJson", new ObjectMapper().writeValueAsString(userDtos));
        return "users";
    }
    //display login form

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
