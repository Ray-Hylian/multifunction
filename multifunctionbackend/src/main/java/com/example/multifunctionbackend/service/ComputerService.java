package com.example.multifunctionbackend.service;

import com.example.multifunctionbackend.entities.Computer;

import java.util.List;

public interface ComputerService {

    public Computer createComputer(Computer computer);

    public List<Computer> createComputers(List<Computer> computers);

    public List<Computer> getComputers();

    public void deleteComputer(long id);

    public Computer updateComputer(long id, Computer updatedComputer);

}
