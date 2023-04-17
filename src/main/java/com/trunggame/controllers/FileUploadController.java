package com.trunggame.controllers;


import com.trunggame.constant.ConstantUtils;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.FileDTO;
import com.trunggame.enums.EFileDocumentStatus;
import com.trunggame.enums.EFileDocumentType;
import com.trunggame.models.FileDocument;
import com.trunggame.repository.FileRepository;
import com.trunggame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Value("${custom.properties.upload.path}")
    private String uploadPath;

    @Value("${custom.properties.view.path}")
    private String viewPath;


    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    BaseResponseDTO<FileDTO> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        try {

            if(!EFileDocumentType.isValid(type)) {
                return new BaseResponseDTO<>("Invalid type", 400,400,null);
            }

            // Generate unique file name
            String uniqId = UUID.randomUUID().toString();
            String fileName = uniqId + "-" + file.getOriginalFilename();

            // Create directory for upload if it doesn't exist
            Path path = Paths.get( uploadPath);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            // Save file to disk
            var filePath = uploadPath + fileName;
            File uploadFile = new File( filePath);
            file.transferTo(uploadFile);

            // Return file path for viewing on web page
            String fileUrl = viewPath + fileName;


            // Create file entity
            var fileEntity = FileDocument.builder()
                    .name(fileName)
                    .previewUrl(fileUrl)
                    .path(filePath)
                    .status(EFileDocumentStatus.ACTIVE)
                    .uniqId(uniqId)
                    .fileType(type.toUpperCase())
                    .createdAt(LocalDateTime.now())
                    .build();

            // Save File Document
            fileRepository.save(fileEntity);

            return new BaseResponseDTO<>("Success", 200, 200, FileDTO.builder().id(uniqId).url(fileUrl).build());
        } catch (IOException e) {
            e.printStackTrace();
            return new BaseResponseDTO<>("Error uploading file", 500,400,null);
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath + filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] fileContent = resource.getInputStream().readAllBytes();
                String contentType = getContentType(resource);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileContent.length));
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
                return ResponseEntity.ok().headers(headers).body(fileContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    private String getContentType(Resource resource) throws IOException {
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        String filename = resource.getFilename();
        String extension = filename != null ? filename.substring(filename.lastIndexOf(".") + 1) : null;
        switch (extension.toLowerCase()) {
            case "pdf":
                contentType = MediaType.APPLICATION_PDF_VALUE;
                break;
            case "jpg":
            case "jpeg":
                contentType = MediaType.IMAGE_JPEG_VALUE;
                break;
            case "png":
                contentType = MediaType.IMAGE_PNG_VALUE;
                break;
        }
        return contentType;
    }

    @GetMapping("/banner")
    public BaseResponseDTO<List<FileDTO>> getListBanner() {
        var files = fileRepository.getListBanner(ConstantUtils.ACTIVE
                , ConstantUtils.BANNER);
        if(files.isEmpty()) {
            return new BaseResponseDTO<>("Success", 200,200,new ArrayList());
        }

        var fileDTOS = files.stream().map(value ->
                FileDTO.builder().id(value.getUniqId()).url(value.getPreviewUrl()).build())
                .collect(Collectors.toList());

        return new BaseResponseDTO<>("Success", 200,200,fileDTOS);
    }
}
