package com.trunggame.controllers;

import com.trunggame.dto.BannerDTO;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.Banner;
import com.trunggame.repository.BannerRepository;
import com.trunggame.repository.FileRepository;
import com.trunggame.repository.impl.PostRespositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private PostRespositoryCustom postRespositoryCustom;

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

        List<BannerDTO> list = postRespositoryCustom.getAllActiveBanner();
        int priority = 0;
        if (list.isEmpty()) {
            priority = 1;
        } else {
            for (BannerDTO bannerDTO : list) {
                if (bannerDTO.getPriority() != null) {
                    priority = Integer.parseInt(bannerDTO.getPriority()) + 1;
                }
            }
        }

        var bannerCreate = Banner.builder()
                .fileId(banner.getFileId())
                .status(Banner.Status.ACTIVE)
                .priority(Integer.toString(priority))
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
            List<BannerDTO> list = postRespositoryCustom.getAllActiveBanner();
            if (type == 0) {
                banner.setStatus(Banner.Status.INACTIVE);
                banner.setPriority("");
                int priority=1;
                for(BannerDTO bannerDTO: list){
                    if(bannerDTO.getId()!= banner.getId()){
                        var ban = bannerRepository.findById(bannerDTO.getId());
                        ban.get().setPriority(String.valueOf(priority));
                        priority++;
                    }
                }
            } else {
                banner.setStatus(Banner.Status.ACTIVE);
                banner.setPriority(String.valueOf(list.size()+1));
            }

            bannerRepository.save(banner);
            return new BaseResponseDTO<>("Success", 200, 200, banner);
        } else {
            return new BaseResponseDTO<>("Not Found", 401, 401, null);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> getAll() {
        return new BaseResponseDTO<>("Success", 200, 200, postRespositoryCustom.getAllBanner());
    }

    @PostMapping("/up/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> uplevelBanner(@PathVariable("id") Long id) {
        Optional<Banner> bannerOptional = bannerRepository.findById(id);

        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();

            List<BannerDTO> list = postRespositoryCustom.getAllBanner();
            Long preId = null;

            for (BannerDTO bannerDTO : list) {
                if (!Objects.equals(bannerDTO.getId(), id)) {
                    preId = bannerDTO.getId();
                } else {
                    assert preId != null;
                    Optional<Banner> preBanner = bannerRepository.findById(preId);
                    var pre = preBanner.get();
                    var lastPriority = pre.getPriority();
                    pre.setPriority(banner.getPriority());
                    bannerRepository.save(pre);
                    banner.setPriority(lastPriority);
                    bannerRepository.save(banner);
                    return new BaseResponseDTO<>("Success", 200, 200, banner);
                }
            }
            return new BaseResponseDTO<>("There is some trouble", 404, 404, null);
        } else {
            return new BaseResponseDTO<>("Not Found", 404, 401, null);
        }
    }
}

