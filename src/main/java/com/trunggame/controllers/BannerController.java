package com.trunggame.controllers;

import com.trunggame.dto.BannerDTO;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.Banner;
import com.trunggame.repository.BannerRepository;
import com.trunggame.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Banner> getById(@PathVariable Long id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        return banner.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createBanner(@RequestBody BannerDTO banner) {
        var file = fileRepository.findFirstByUniqId(banner.getFileId());

        var bannerCreate = Banner.builder()
                .fileId(banner.getFileId())
                .status(Banner.Status.ACTIVE)
                .imageUrl(file.get().getPreviewUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bannerRepository.save(bannerCreate);
        return new BaseResponseDTO<>("Success", 200, 200, bannerCreate);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateBanner(@PathVariable("id") Long type, @RequestBody Banner bannerInput) {

        Optional<Banner> bannerOptional = bannerRepository.findById(bannerInput.getId());

        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();

            if (type == 0) banner.setStatus(Banner.Status.INACTIVE);
            else banner.setStatus(Banner.Status.ACTIVE);

            bannerRepository.save(banner);
            return new BaseResponseDTO<>("Success", 200, 200, banner);
        } else {
            return new BaseResponseDTO<>("Not Found", 401, 401, null);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> getAll() {
        return new BaseResponseDTO<>("Success", 200, 200, bannerRepository.findAll());
    }
}

