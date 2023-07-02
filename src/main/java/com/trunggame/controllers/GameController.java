package com.trunggame.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GameInputDTO;
import com.trunggame.repository.GameRepository;
import com.trunggame.repository.PackageRepository;
import com.trunggame.repository.impl.GameRepositoryCustom;
import com.trunggame.security.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import java.util.Base64;

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

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteGames(@RequestBody GameInputDTO gameInputDTO) {
        return gameService.deleteGame(gameInputDTO);
    }

    @GetMapping("")
    public BaseResponseDTO<?> getListGame() {
        return new BaseResponseDTO<>("Success", 200, 200, gameService.getListGame());
    }

    @GetMapping("/load-data")
    public BaseResponseDTO<?> loadData() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString( gameService.getLoadData());
//        System.out.println(jsonString);
//        String data = Base64.getEncoder().encodeToString(jsonString.getBytes());

        return new BaseResponseDTO<>("Success", 200, 200, gameService.getLoadData());
    }

    @GetMapping("/{id}")
    public BaseResponseDTO<?> getGame(@PathVariable Long id) {
        var games = gameRepository.findById(id);
        return new BaseResponseDTO<>("Success", 200, 200, games.get());
    }
}
