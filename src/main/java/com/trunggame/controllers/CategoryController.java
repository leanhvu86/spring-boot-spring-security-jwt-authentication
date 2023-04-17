package com.trunggame.controllers;


import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.CategoryInputDTO;
import com.trunggame.enums.ECategoryStatus;
import com.trunggame.models.GameCategories;
import com.trunggame.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<GameCategories> createCategory(@RequestBody CategoryInputDTO dto) {

        var gcOTP = categoryRepository.findFirstByName(dto.getName());
        if(gcOTP.isPresent()) {
            return new BaseResponseDTO<>("duplicated category", 400,400,null);
        }

        var gc =  GameCategories.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .parentId(dto.getParentCategoryId())
                .status(ECategoryStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        var result  =  categoryRepository.save(gc);

        return new BaseResponseDTO<>("Success", 200,200,result);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateCategory(@RequestBody CategoryInputDTO dto) {
        if(dto.getId() == null){
            return new BaseResponseDTO<GameCategories>("Id can not be null", 400,400,null);
        }

        if(dto.getParentCategoryId() != null) {
            var parent = categoryRepository.findById(dto.getParentCategoryId());
            if(parent.isEmpty()) {
                return new BaseResponseDTO<GameCategories>("Parent category does not exist", 400,400,null);
            }
        }

        var gcExisted = categoryRepository.findOneByNameAndStatus(dto.getName(), ECategoryStatus.ACTIVE);
        if(gcExisted.isPresent() && gcExisted.get().getId() != dto.getId()) {
            return new BaseResponseDTO<GameCategories>("Category's name does exist", 400,400,null);
        }

        var gcOTP = categoryRepository.findById(dto.getId());
        if(gcOTP.isPresent()) {
            var entity =  gcOTP.get();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setParentId(dto.getParentCategoryId());
            entity.setDescription(dto.getDescription());
            var result  =  categoryRepository.save(entity);
            return new BaseResponseDTO<>("Success", 200,200,result);
        }

        return new BaseResponseDTO<GameCategories>("Content not found", 400,400,null);
    }


    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteCategory(@RequestParam Long id) {
        if(id == null){
            return new BaseResponseDTO<GameCategories>("Id can not be null", 400,400,null);
        }

        var gcOTP = categoryRepository.findById(id);
        if(gcOTP.isPresent()) {
            var entity =  gcOTP.get();
            entity.setStatus(ECategoryStatus.DELETED);
            var result  =  categoryRepository.save(entity);

            // after delete current category, we will delete all child categories
            var listChild = categoryRepository.findAllByParentId(id);
            if(listChild.isPresent()) {
                listChild.get().forEach(child -> child.setStatus(ECategoryStatus.DELETED));
                categoryRepository.saveAll(listChild.get());
            }

            categoryRepository.delete(entity);

            return new BaseResponseDTO<GameCategories>("Success", 200,200, result);
        }

        return new BaseResponseDTO<GameCategories>("Content not found", 400,400,null);
    }


    @GetMapping("/list")
    public BaseResponseDTO<?> getListCategory() {
        var result = categoryRepository.findAllByStatus(ECategoryStatus.ACTIVE);
        return new BaseResponseDTO<>("Success", 200, 200, result.get());
    }


}
