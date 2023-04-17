package com.trunggame.security.services;

import com.trunggame.models.MarketType;
import com.trunggame.repository.MarketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketTypeService {

    @Autowired
    private MarketTypeRepository marketTypeRepository;

    public List<MarketType> getAllMarketTypes() {
        return marketTypeRepository.findAll();
    }

    public Optional<MarketType> getMarketTypeById(Long id) {
        return marketTypeRepository.findById(id);
    }

    public MarketType createMarketType(MarketType marketType) {
        return marketTypeRepository.save(marketType);
    }

    public MarketType updateMarketType(Long id, MarketType marketTypeDetails) {
        Optional<MarketType> optionalMarketType = marketTypeRepository.findById(id);
        if (optionalMarketType.isPresent()) {
            MarketType marketType = optionalMarketType.get();
            marketType.setName(marketTypeDetails.getName());
            marketType.setDescription(marketTypeDetails.getDescription());
            return marketTypeRepository.save(marketType);
        } else {
            return null; // or throw an exception
        }
    }

    public void deleteMarketType(Long id) {
        marketTypeRepository.deleteById(id);
    }
}
