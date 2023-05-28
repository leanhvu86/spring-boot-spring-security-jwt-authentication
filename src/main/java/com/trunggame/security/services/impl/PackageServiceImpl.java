package com.trunggame.security.services.impl;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GameInformationDTO;
import com.trunggame.dto.GameInputDTO;
import com.trunggame.dto.GamePackageDTO;
import com.trunggame.enums.ECommonStatus;
import com.trunggame.models.*;
import com.trunggame.repository.*;
import com.trunggame.repository.impl.GameRepositoryCustom;
import com.trunggame.repository.impl.PackageRepositoryImpl;
import com.trunggame.security.services.GameService;
import com.trunggame.security.services.PackageService;
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
public class PackageServiceImpl implements PackageService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmartTagRepository smartTagRepository;

    @Autowired
    private SmartTagGameRepository smartTagGameRepository;

    @Autowired
    private GameServerGroupRepository gameServerGroupRepository;

    @Autowired
    private GameRepositoryCustom gameRepositoryCustom;

    @Autowired
    private PackageRepository gamePackageRepository;

    @Autowired
    private PackageRepositoryImpl packageRepositoryImpl;

    @Override
    @Transactional
    public BaseResponseDTO<?> createPackage(GamePackageDTO input) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        validateInput(input);

        var file = fileRepository.findFirstByUniqId(input.getImageId());

        // Create game builder
        var gamePackage = GamePackage.builder()
                .name(input.getName())
                .unit(input.getUnit())
                .price(input.getPrice())
                .rating(input.getRating())
                .attribute(input.getAttribute())
                .warehouseQuantity(input.getWarehouseQuantity())
                .descriptionVi(input.getDescriptionVi())
                .gameId(input.getGameId())
                .status(GamePackage.Status.ACTIVE)
                .imageId(input.getImageId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        var packageEntity = gamePackageRepository.save(gamePackage);
        packageEntity.setPreviewUrl(file.get().getPreviewUrl());

        file.get().setOwnerId(gamePackage.getId());
        fileRepository.save(file.get());


        // Save game server group
        if (input.getServer().size() > 0) {
            List<GameServerGroup> gameServerGroups = new ArrayList<>();
            for (var gs : input.getServer()) {
                gameServerGroups.add(GameServerGroup.builder().gameId(packageEntity.getGameId()).packageId(packageEntity.getId()).name(gs.getName()).createdAt(LocalDateTime.now()).build());
            }

            gameServerGroupRepository.saveAll(gameServerGroups);

        }

        return new BaseResponseDTO<>("Success", 200, 200, packageEntity);

    }

    @Override
    public BaseResponseDTO<?> updatePackage(GamePackageDTO input) {
        Optional<GamePackage> oldPackage = gamePackageRepository.findById(input.getId());

        GamePackage updatedPackage = oldPackage.get();
        updatedPackage.setName(input.getName());
        updatedPackage.setPrice(input.getPrice());
        updatedPackage.setUnit(input.getUnit());
        updatedPackage.setRating(input.getRating());
        updatedPackage.setServerGroup(input.getServerGroup());
        updatedPackage.setAttribute(input.getAttribute());
        updatedPackage.setWarehouseQuantity(input.getWarehouseQuantity());
        updatedPackage.setTradeCount(input.getTradeCount());
        updatedPackage.setDescriptionVi(input.getDescriptionVi());
        updatedPackage.setDescriptionEn(input.getDescriptionEn());
        updatedPackage.setDeliveryTime(input.getDeliveryTime());
        updatedPackage.setGameId(input.getGameId());
        gamePackageRepository.save(updatedPackage);

        // Save game server group
        if (input.getServer().size() > 0) {
            List<GameServerGroup> gameServerGroups = new ArrayList<>();
            List<GameServerGroup> gameServerDelete;
            gameServerDelete = gameServerGroupRepository.findAllByPackageId(updatedPackage.getId());
            for (var gs : input.getServer()) {
                gameServerGroups.add(GameServerGroup.builder().gameId(updatedPackage.getGameId()).packageId(updatedPackage.getId()).name(gs.getName()).createdAt(LocalDateTime.now()).build());
                gameServerDelete.removeIf(e -> e.getId().equals(gs.getId()));
            }

            gameServerGroupRepository.saveAll(gameServerGroups);
            gameServerGroupRepository.deleteAll(gameServerDelete);
        }

        return new BaseResponseDTO<>("Success", 200, 200, null);
    }

    @Override
    public List<GamePackageDTO> getAllPackage() {
        return this.packageRepositoryImpl.getAllPackage();
    }

    private void validateInput(GamePackageDTO input) {
        if (Objects.isNull(input)) {
            throw new RuntimeException("Input to create game is null");
        }

        var categoryOPT = gameRepository.findById(input.getGameId());
        if (categoryOPT.isEmpty()) {
            throw new RuntimeException("Game does exist");
        }

        var imageOTP = fileRepository.findFirstByUniqId(input.getImageId());

        if (imageOTP.isEmpty()) {
            throw new RuntimeException("Image's id  does exist");
        }
    }
}
