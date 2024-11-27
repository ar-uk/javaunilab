package com.adil.unisiteproject.uniproject.Repository;

import com.adil.unisiteproject.uniproject.models.AppUser;
import com.adil.unisiteproject.uniproject.models.Category;
import com.adil.unisiteproject.uniproject.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(AppUser user);
}

