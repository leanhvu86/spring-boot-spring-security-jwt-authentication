package com.trunggame.security.services;


import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GameInputDTO;
import com.trunggame.models.Game;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

     BaseResponseDTO<?> createGame(GameInputDTO input);
     BaseResponseDTO<?> updateGame(GameInputDTO input);
     List<Game>getListGame(Pageable pageable, String name);

}
