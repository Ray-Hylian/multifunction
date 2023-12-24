package com.example.multifunctionbackend.controller;

import com.example.multifunctionbackend.entities.Computer;
import com.example.multifunctionbackend.service.ComputerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computers")
@CrossOrigin(origins = "http://localhost:3000")
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    @PostMapping(path="create-computer")
    public Computer createComputer(@RequestBody Computer computer) {
        return computerService.createComputer(computer);
    }

    @GetMapping(path="all-computers")
    public List<Computer> readComputers(){
        return computerService.getComputers();
    }

    @DeleteMapping(path = "/delete-computer/{id}")
    public void deleteComputer(@PathVariable long id) {
        computerService.deleteComputer(id);
    }

    @PostMapping(path="create-computers")
    public List<Computer> createComputers(@RequestBody List<Computer> computers) {
        return computerService.createComputers(computers);
    }

    @PutMapping(path = "/update-computer/{id}")
    public ResponseEntity<Computer> updateComputer(@PathVariable long id, @RequestBody Computer updatedComputer) {
        try {
            Computer updated = computerService.updateComputer(id, updatedComputer);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

