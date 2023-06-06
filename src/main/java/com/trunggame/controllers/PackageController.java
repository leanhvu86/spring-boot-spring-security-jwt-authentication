package com.trunggame.controllers;

import com.trunggame.constant.ConstantUtils;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GamePackageDTO;
import com.trunggame.dto.ServerGroupDTO;
import com.trunggame.models.Game;
import com.trunggame.models.GamePackage;
import com.trunggame.models.GameServerGroup;
import com.trunggame.models.ServerGroup;
import com.trunggame.repository.*;
import com.trunggame.security.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private GameServerGroupRepository gameServerGroupRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PackageService packageService;

    @Autowired
    private FileRepository fileRepository;

    // Create a new package
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createPackage(@RequestBody GamePackageDTO input) {
        // Check if the gameId already exists
        if (gameRepository.findById(input.getGameId()).isEmpty()) {
            return new BaseResponseDTO<>("No content", 403, 403, null);
        }
        // Save the package
        packageService.createPackage(input);
        return new BaseResponseDTO<>("Success", 200, 200, input);
    }

    // Read all packages
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> getPackages() {
        return new BaseResponseDTO<>("Success", 200, 200, this.packageService.getAllPackage());
    }

    // Read a package by ID
    @GetMapping("/{id}")
    public BaseResponseDTO<?> getPackage(@PathVariable Long id) {
        Optional<GamePackage> gamePackage = packageRepository.findById(id);
        if (gamePackage.isPresent()) {
            List<GameServerGroup> server = gameServerGroupRepository.findAllByPackageId(gamePackage.get().getId());
            var gamePackageObject = gamePackage.get();
            gamePackageObject.setServer(server);
            var file = fileRepository.findFirstByUniqId(gamePackage.get().getImageId());
            gamePackageObject.setPreviewUrl(file.get().getPreviewUrl());
            return new BaseResponseDTO<>("Success", 200, 200, gamePackage.get());

        } else {
            return new BaseResponseDTO<>("No content", 403, 403, null);

        }
    }

    // Update a package by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updatePackage(@PathVariable Long id, @RequestBody GamePackageDTO newPackage) {
        Optional<GamePackage> oldPackage = packageRepository.findById(id);
        if (oldPackage.isPresent()) {
            this.packageService.updatePackage(newPackage);

            return new BaseResponseDTO<>("Success", 200, 200, newPackage);

        } else {
            return new BaseResponseDTO<>("No content", 403, 403, null);
        }
    }

    // Delete a package by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deletePackage(@PathVariable Long id) {
        Optional<GamePackage> gamePackage = packageRepository.findById(id);
        if (gamePackage.isPresent()) {
            var packageGame = gamePackage.get();
             if (gamePackage.get().getStatus() == GamePackage.Status.ACTIVE) {
                packageGame.setStatus(GamePackage.Status.INACTIVE);
            } else {
                packageGame.setStatus(GamePackage.Status.ACTIVE);
            }
            packageRepository.save(packageGame);
            return new BaseResponseDTO<>("Success", 200, 200, null);
        } else {
            return new BaseResponseDTO<>("No content", 403, 403, null);

        }
    }
}
