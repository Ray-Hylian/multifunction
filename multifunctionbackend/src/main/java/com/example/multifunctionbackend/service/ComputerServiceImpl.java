package com.example.multifunctionbackend.service;

import com.example.multifunctionbackend.repository.ComputerRepository;
import com.example.multifunctionbackend.entities.Computer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Override
    public Computer createComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    @Override
    public List<Computer> createComputers(List<Computer> computers) {
        return computerRepository.saveAll(computers);
    }

    @Override
    public List<Computer> getComputers() {
        return computerRepository.findAll();
    }

    @Override
    public void deleteComputer(long id) {
        computerRepository.deleteById(id);
    }

    @Override
    public Computer updateComputer(long id, Computer updatedComputer) {
        Computer existingComputer = computerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Computer not found with id: " + id));
        existingComputer.setModel(updatedComputer.getModel());
        return computerRepository.save(existingComputer);
    }

}
