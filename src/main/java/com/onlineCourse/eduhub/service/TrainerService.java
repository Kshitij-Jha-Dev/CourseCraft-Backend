package com.onlineCourse.eduhub.service;

import java.util.List;
import java.util.Map;

import com.onlineCourse.eduhub.dto.trainer.TrainerRequest;
import com.onlineCourse.eduhub.entity.Trainer;

public interface TrainerService {

    List<Trainer> getAllTrainers();
    Trainer getTrainerById(Integer id);
    Trainer createTrainer(TrainerRequest dto);
    Trainer updateTrainer(Integer id, TrainerRequest dto);
    void deleteTrainer(Integer id);
}