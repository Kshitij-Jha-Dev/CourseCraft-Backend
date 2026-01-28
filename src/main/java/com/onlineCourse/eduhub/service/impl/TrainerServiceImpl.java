package com.onlineCourse.eduhub.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.trainer.TrainerRequest;
import com.onlineCourse.eduhub.entity.Trainer;
import com.onlineCourse.eduhub.repository.TrainerRepository;
import com.onlineCourse.eduhub.service.TrainerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepo;

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepo.findAll();
    }

    @Override
    public Trainer getTrainerById(Integer id) {
        return trainerRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    @Override
    public Trainer createTrainer(TrainerRequest dto) {
        Trainer trainer = Trainer.builder()
            .trainerName(dto.getTrainerName())
            .description(dto.getDescription())
            .rating(dto.getRating() == null ? 4.5 : dto.getRating())
            .imageUrl(dto.getImageUrl())
            .build();

        return trainerRepo.save(trainer);
    }

    @Override
    public Trainer updateTrainer(Integer id, TrainerRequest dto) {

        Trainer trainer = getTrainerById(id);

        trainer.setTrainerName(dto.getTrainerName());
        trainer.setDescription(dto.getDescription());
        trainer.setRating(dto.getRating());
        trainer.setImageUrl(dto.getImageUrl());

        return trainerRepo.save(trainer);
    }

    @Override
    public void deleteTrainer(Integer id) {

        Trainer trainer = getTrainerById(id);

        // optional business rule safety
        if (!trainer.getCourses().isEmpty()) {
            throw new RuntimeException("Cannot delete trainer with assigned courses");
        }

        trainerRepo.delete(trainer);
    }
}