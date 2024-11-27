package com.adil.unisiteproject.uniproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adil.unisiteproject.uniproject.models.AppUser;
public interface AppUsersRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findByEmail (String email);
}
