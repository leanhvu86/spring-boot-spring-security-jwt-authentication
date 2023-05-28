package com.trunggame.security.services;


import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.GamePackageDTO;

import java.util.List;

public interface PackageService {

     BaseResponseDTO<?> createPackage(GamePackageDTO input);
     BaseResponseDTO<?> updatePackage(GamePackageDTO input);
     public List<GamePackageDTO> getAllPackage();
}
