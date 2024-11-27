package com.adil.unisiteproject.uniproject.Controllers;

import com.adil.unisiteproject.uniproject.Repository.AppUsersRepository;
import com.adil.unisiteproject.uniproject.models.AppUser;
import com.adil.unisiteproject.uniproject.models.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class AccountController {
    @Autowired
    private AppUsersRepository repo;

    @GetMapping("/register")
    public String register(Model model){
        RegisterDTO registerDto = new RegisterDTO();
        model.addAttribute("registerDto", registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute("registerDto") RegisterDTO registerDTO,
            BindingResult result) {


        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.addError(new FieldError("registerDto", "confirmPassword", "Passwords do not match"));
        }


        AppUser existingUser = repo.findByEmail(registerDTO.getEmail());
        if (existingUser != null) {
            result.addError(new FieldError("registerDto", "email", "Account already taken"));
        }


        if (result.hasErrors()) {
            model.addAttribute("registerDto", registerDTO);
            model.addAttribute("success", false);
            return "register";
        }


        try {
            var bCryptEncoder = new BCryptPasswordEncoder();
            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDTO.getFirstName());
            newUser.setLastName(registerDTO.getLastName());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPhone(registerDTO.getPhone());
            newUser.setAddress(registerDTO.getAddress());
            newUser.setRole("client");
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));

            repo.save(newUser);

            model.addAttribute("registerDto", new RegisterDTO());
            model.addAttribute("success", true);

        } catch (Exception ex) {
            result.addError(new FieldError("registerDto", "firstName", "An unexpected error occurred: " + ex.getMessage()));
            model.addAttribute("registerDto", registerDTO);
            model.addAttribute("success", false);
        }

        return "register";
    }


}
