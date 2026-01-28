package com.onlineCourse.eduhub.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.dto.trainer.TrainerRequest;
import com.onlineCourse.eduhub.service.TrainerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/trainers")
@RequiredArgsConstructor
public class AdminTrainerController {

    private final TrainerService trainerService;

    // GET all trainers
    @GetMapping
    public ResponseEntity<?> getAllTrainers() {
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", trainerService.getAllTrainers()
        ));
    }

    // GET trainer by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainer(@PathVariable Integer id) {
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", trainerService.getTrainerById(id)
        ));
    }

    // POST create trainer
    @PostMapping
    public ResponseEntity<?> createTrainer(
            @Valid @RequestBody TrainerRequest dto) {

        return ResponseEntity.status(201).body(Map.of(
            "success", true,
            "message", "Trainer created successfully",
            "data", trainerService.createTrainer(dto)
        ));
    }

    // PUT update trainer
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrainer(
            @PathVariable Integer id,
            @Valid @RequestBody TrainerRequest dto) {

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Trainer updated successfully",
            "data", trainerService.updateTrainer(id, dto)
        ));
    }

    // DELETE trainer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable Integer id) {

        trainerService.deleteTrainer(id);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Trainer deleted successfully"
        ));
    }
}