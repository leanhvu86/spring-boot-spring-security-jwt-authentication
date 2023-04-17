package com.trunggame.controllers;

import com.trunggame.constant.ConstantUtils;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.GamePackage;
import com.trunggame.repository.GameRepository;
import com.trunggame.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/packages")
public class PackageController {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private GameRepository gameRepository;


    // Create a new package
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createPackage(@RequestBody GamePackage input) {
        // Check if the gameId already exists
        if (gameRepository.findById(input.getGameId()).isEmpty()) {
            return new BaseResponseDTO<>("No content", 403,403,null);
        }
        // Save the package
        input.setCreatedAt(LocalDateTime.now());
        input.setStatus(ConstantUtils.ACTIVE);
        packageRepository.save(input);
        return new BaseResponseDTO<>("Success", 200,200,input);
    }

    // Read all packages
    @GetMapping
    public BaseResponseDTO<?>  getPackages() {
        List<GamePackage> packages = packageRepository.findAll();
        return new BaseResponseDTO<>("Success", 200,200,packages);
    }

    // Read a package by ID
    @GetMapping("/{id}")
    public BaseResponseDTO<?>  getPackage(@PathVariable Long id) {
        Optional<GamePackage> gamePackage = packageRepository.findById(id);
        if (gamePackage.isPresent()) {
            return new BaseResponseDTO<>("Success", 200,200,gamePackage.get());

        } else {
            return new BaseResponseDTO<>("No content", 403,403,null);

        }
    }

    // Update a package by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?>  updatePackage(@PathVariable Long id, @RequestBody GamePackage newPackage) {
        Optional<GamePackage> oldPackage = packageRepository.findById(id);
        if (oldPackage.isPresent()) {
            GamePackage updatedPackage = oldPackage.get();
            updatedPackage.setName(newPackage.getName());
            updatedPackage.setPrice(newPackage.getPrice());
            updatedPackage.setUnit(newPackage.getUnit());
            updatedPackage.setRating(newPackage.getRating());
            updatedPackage.setServerGroup(newPackage.getServerGroup());
            updatedPackage.setServer(newPackage.getServer());
            updatedPackage.setAttribute(newPackage.getAttribute());
            updatedPackage.setWarehouseQuantity(newPackage.getWarehouseQuantity());
            updatedPackage.setTradeCount(newPackage.getTradeCount());
            updatedPackage.setDescriptionVi(newPackage.getDescriptionVi());
            updatedPackage.setDescriptionEn(newPackage.getDescriptionEn());
            updatedPackage.setDeliveryTime(newPackage.getDeliveryTime());
            updatedPackage.setGameId(newPackage.getGameId());
            packageRepository.save(updatedPackage);
            return new BaseResponseDTO<>("Success", 200,200,updatedPackage);

        } else {
            return new BaseResponseDTO<>("No content", 403,403,null);
        }
    }

    // Delete a package by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  BaseResponseDTO<?> deletePackage(@PathVariable Long id) {
        Optional<GamePackage> gamePackage = packageRepository.findById(id);
        if (gamePackage.isPresent()) {
            packageRepository.delete(gamePackage.get());
            return new BaseResponseDTO<>("Success", 200,200,null);
        } else {
            return new BaseResponseDTO<>("No content", 403,403,null);

        }
    }
}
