package com.onlineCourse.eduhub.service;

import java.util.List;

import com.onlineCourse.eduhub.dto.trainer.TrainerRequest;
import com.onlineCourse.eduhub.entity.Trainer;

public interface TrainerService {

    List<Trainer> getAllTrainers();
    Trainer getTrainerById(Long id);
    Trainer createTrainer(TrainerRequest dto);
    Trainer updateTrainer(Long id, TrainerRequest dto);
    void deleteTrainer(Long id);
}