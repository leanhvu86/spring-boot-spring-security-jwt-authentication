package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.MarketType;
import com.trunggame.security.services.MarketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/marketTypes")
public class MarketTypeController {

    @Autowired
    private MarketTypeService marketTypeService;

    @GetMapping
    public List<MarketType> getAllMarketTypes() {
        return marketTypeService.getAllMarketTypes();
    }

    @GetMapping("/{id}")
    public  BaseResponseDTO<?> getMarketTypeById(@PathVariable Long id) {
        Optional<MarketType> optionalMarketType = marketTypeService.getMarketTypeById(id);
        if (optionalMarketType.isPresent()) {
            return new BaseResponseDTO<>("Success", 200,200,optionalMarketType.get());
        } else {
            return new BaseResponseDTO<>("No content", 400,400,optionalMarketType.get());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createMarketType(@RequestBody MarketType marketType) {
        return new BaseResponseDTO<>("Success", 200,200,marketTypeService.createMarketType(marketType));
    }

    @PutMapping("/{id}")
    public BaseResponseDTO<?> updateMarketType(@PathVariable Long id, @RequestBody MarketType marketTypeDetails) {
        MarketType updatedMarketType = marketTypeService.updateMarketType(id, marketTypeDetails);
        if (updatedMarketType != null) {
            return new BaseResponseDTO<>("Success", 200,200,null);
        } else {
            return new BaseResponseDTO<>("No content", 403,403,null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteMarketType(@PathVariable Long id) {
        marketTypeService.deleteMarketType(id);
        return new BaseResponseDTO<>("Success", 200,4200,null);
    }
}
