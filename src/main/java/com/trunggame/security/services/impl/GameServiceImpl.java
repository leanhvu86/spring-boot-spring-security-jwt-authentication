package com.trunggame.security.services.impl;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GameInputDTO;
import com.trunggame.enums.ECommonStatus;
import com.trunggame.models.Game;
import com.trunggame.models.Price;
import com.trunggame.models.SmartTagGame;
import com.trunggame.repository.*;
import com.trunggame.security.services.GameService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl  implements GameService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SmartTagRepository smartTagRepository;

    @Autowired
    SmartTagGameRepository smartTagGameRepository;

    @Override
    @Transactional
    public BaseResponseDTO<?> createGame(GameInputDTO input) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        validateInput(input);

        var file = fileRepository.findFirstByUniqId(input.getImageId());

        // Create game builder
        var game = Game.builder()
                .name(input.getName())
                .description(input.getDescription())
                .categoryId(input.getCategoryId())
                .imageId(input.getImageId())
                .thumbnail(input.getImageId())
                .type(input.getType())
                .status(Game.Status.ACTIVE)
                .companyName(input.getCompanyName())
                .thumbnail(input.getThumbnail())
                .contentEn(input.getContentEn())
                .contentVi(input.getContentVi())
                .descriptionEn(input.getDescriptionEn())
                .marketType(input.getMarketType())
                .youtubeLink(input.getYoutubeLink())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        var gameEntity = gameRepository.save(game);
        gameEntity.setPreviewUrl(file.get().getPreviewUrl());

        file.get().setOwnerId(game.getId());
        fileRepository.save(file.get());

        // Create price builder
        var price = Price.builder()
                .price(input.getPrice())
                .promotionPrice(input.getPromotionPrice())
                .promotionPercent(input.getPromotionPercent())
                .status(ECommonStatus.ACTIVE)
                .gameId(gameEntity.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        priceRepository.save(price);


        // save tags and game
        if(input.getTags() != null && !input.getTags().isEmpty()) {
            var tags = input.getTags();
            if(tags.size() > 10) {
                return new BaseResponseDTO<>("Tag's size is must be smaller than equal 10", 400, 400,null);
            }
            List<SmartTagGame> smartTagGames = new ArrayList<>();
            for(var tagName : input.getTags()) {
                var tag = smartTagRepository.findFirstByName(tagName);
                if(tag.isPresent()) {
                    smartTagGames.add(SmartTagGame.builder().game(gameEntity).smartTag(tag.get()).build());
                } else {
                    return new BaseResponseDTO<>("Tag's name does not exist: " + tagName, 400, 400,null);
                }
            }

            // Batch save smart tag game
            smartTagGameRepository.saveAll(smartTagGames);
        }

        return new BaseResponseDTO<>("Success", 200, 200,gameEntity);

    }

    @Override
    public BaseResponseDTO<?> updateGame(GameInputDTO input) {
        if(input.getId() == null) {
            return new BaseResponseDTO<>("Id can not be null", 400, 400,null);
        }

        var gameEntity = gameRepository.findById(input.getId());
        if(gameEntity.isPresent()) {
            validateInput(input);

            // Delete old files
            List<String> uniqIds = Arrays.asList(input.getImageId(), input.getThumbnail());
            fileRepository.updateFileLinkToGame(uniqIds);

            // Delete all tags
            var smartTagGameOpt = smartTagGameRepository.findByGameId(input.getId());
            smartTagGameRepository.deleteAllById(smartTagGameOpt.get().stream().map(s -> s.getId()).collect(Collectors.toList()));


            var file = fileRepository.findFirstByUniqId(input.getImageId());

            // Create game builder
            var game = Game.builder()
                    .id(input.getId())
                    .name(input.getName())
                    .description(input.getDescription())
                    .categoryId(input.getCategoryId())
                    .imageId(input.getImageId())
                    .thumbnail(input.getImageId())
                    .type(input.getType())
                    .status(Game.Status.ACTIVE)
                    .companyName(input.getCompanyName())
                    .contentEn(input.getContentEn())
                    .contentVi(input.getContentVi())
                    .descriptionEn(input.getDescriptionEn())
                    .marketType(input.getMarketType())
                    .youtubeLink(input.getYoutubeLink())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            var gameSaved = gameRepository.save(game);
            gameSaved.setPreviewUrl(file.get().getPreviewUrl());

            file.get().setOwnerId(game.getId());
            fileRepository.save(file.get());

            // Create price builder
            var price = Price.builder()
                    .price(input.getPrice())
                    .promotionPrice(input.getPromotionPrice())
                    .promotionPercent(input.getPromotionPercent())
                    .status(ECommonStatus.ACTIVE)
                    .gameId(gameSaved.getId())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now()).build();

            priceRepository.save(price);


            // save tags and game
            if(input.getTags() != null && !input.getTags().isEmpty()) {
                var tags = input.getTags();
                if(tags.size() > 10) {
                    return new BaseResponseDTO<>("Tag's size is must be smaller than equal 10", 400, 400,null);
                }
                List<SmartTagGame> smartTagGames = new ArrayList<>();
                for(var tagName : input.getTags()) {
                    var tag = smartTagRepository.findFirstByName(tagName);
                    if(tag.isPresent()) {
                        smartTagGames.add(SmartTagGame.builder().game(gameSaved).smartTag(tag.get()).build());
                    } else {
                        return new BaseResponseDTO<>("Tag's name does not exist: " + tagName, 400, 400,null);
                    }
                }

                // Batch save smart tag game
                smartTagGameRepository.saveAll(smartTagGames);
            }

            return new BaseResponseDTO<>("Success", 200, 200,gameSaved);

        }
        return new BaseResponseDTO<>("Game not found", 400, 400,null);
    }

    @Override
    public List<Game> getListGame(Pageable pageable, String name) {
        var listGame = this.createFindGameTemp(name, pageable).getResultList();
        return listGame;
    }

    public Query createFindGameTemp(String search, Pageable pageable){
        Map<String, Object> param = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append(" select * from game g where 1 = 1 ");
        if(StringUtils.isNotBlank(search)) {
            String lowerSearch = search.trim().toLowerCase();
            builder.append(" and (lower(g.name) like :search ");
            param.put("search", "%"+lowerSearch+"%");
        }

        builder.append("LIMIT :limit OFFSET :offset ");
        param.put("limit", pageable.getPageSize());
        param.put("offset", pageable.getOffset());

        var query = entityManager.createNativeQuery(builder.toString(), Game.class);
        for (Map.Entry<String, Object> item : param.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }
        return query;
    }

    private void validateInput(GameInputDTO input){
        if(Objects.isNull(input)) {
            throw new RuntimeException("Input to create game is null");
        }

        var categoryOPT = categoryRepository.findById(input.getCategoryId());
        if(categoryOPT.isEmpty()) {
            throw new RuntimeException("Category does exist");
        }

        var imageOTP = fileRepository.findFirstByUniqId(input.getImageId());

        if(imageOTP.isEmpty()) {
            throw new RuntimeException("Image's id  does exist");
        }
    }
}
