package com.trunggame.controllers;

import com.trunggame.constant.ConstantUtils;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.TagDeleteDTO;
import com.trunggame.models.SmartTag;
import com.trunggame.repository.SmartTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tag")
public class SmartTagController {

    @Autowired
    SmartTagRepository smartTagRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createTag(@RequestBody SmartTag input) {

        return getBaseResponseDTO(input, false);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateTag(@RequestBody SmartTag input) {

        return getBaseResponseDTO(input,true);
    }

    private BaseResponseDTO<?> getBaseResponseDTO(@RequestBody SmartTag input, boolean isUpdate) {
        var existedSmartTag = smartTagRepository.findFirstByName(input.getName());
        if(existedSmartTag.isPresent()) {
            return new BaseResponseDTO<>("Error: Tag is already taken!", 400, 400,null);
        }

        input.setStatus(ConstantUtils.ACTIVE);
        if (!isUpdate) {
            input.setCreatedAt(LocalDateTime.now());
            input.setUpdatedAt(LocalDateTime.now());
        } else {
            input.setUpdatedAt(LocalDateTime.now());
        }
        var entity = smartTagRepository.save(input);

        return new BaseResponseDTO<>("Success", 200, 200,entity);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteTag(@RequestBody TagDeleteDTO input) {
        if(input.getIds().size() > 0) {
            smartTagRepository.deleteAllByIdIn(input.getIds());
        }
        return new BaseResponseDTO<>("Success", 200, 200,null);
    }


    @GetMapping("/{id}")
    public BaseResponseDTO<?> getTagDetail(@PathVariable Long id) {
        return new BaseResponseDTO<>("Success", 200, 200,smartTagRepository.findById(id));
    }


    @GetMapping("/all")
    public BaseResponseDTO<?> getAllTagDetail() {
        return new BaseResponseDTO<>("Success", 200, 200,smartTagRepository.findAll());
    }
}
