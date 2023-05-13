package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GameInputDTO;
import com.trunggame.repository.GameRepository;
import com.trunggame.repository.PackageRepository;
import com.trunggame.repository.impl.GameRepositoryCustom;
import com.trunggame.security.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    GameRepositoryCustom gameRepositoryCustom;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PackageRepository gamePackageRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createGame(@RequestBody GameInputDTO input) {
        return gameService.createGame(input);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateGame(@RequestBody GameInputDTO input) {
        return gameService.updateGame(input);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?>  deleteGames(@PathVariable Long id) {
        var game = gameRepository.findById(id);
        if(game.isPresent()) {
            gameRepository.deleteById(id);
            return new BaseResponseDTO<>("Success", 200,200,null);
        }
        return new BaseResponseDTO<>("No content", 400,400,null);
    }

    @GetMapping("")
    public BaseResponseDTO<?> getListGame(Pageable pageable) {
        return new BaseResponseDTO<>("Success", 200,200,gameService.getListGame());
    }

    @GetMapping("/{id}")
    public BaseResponseDTO<?> getListGame(@PathVariable Long id) {
        var games = gameRepository.findById(id);
        return new BaseResponseDTO<>("Success", 200,200,games.get());
    }


}
