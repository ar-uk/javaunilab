package com.adil.unisiteproject.uniproject.Services;

import com.adil.unisiteproject.uniproject.Repository.AppUsersRepository;
import com.adil.unisiteproject.uniproject.Repository.CategoryRepository;
import com.adil.unisiteproject.uniproject.models.AppUser;
import com.adil.unisiteproject.uniproject.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUsersService implements UserDetailsService {

    @Autowired
    private AppUsersRepository repo;

    @Autowired
    private CategoryRepository categoryRepository;  // To access Category entities

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = repo.findByEmail(email);
        if (appUser != null) {
            var springUser = User.withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole())
                    .build();
            return springUser;
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // Get the current logged-in user
    public AppUser getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByEmail(email);
    }

    // Fetch all categories from the CategoryRepository (this could be user-specific or not)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();  // Fetch all categories (adjust as needed)
    }
}