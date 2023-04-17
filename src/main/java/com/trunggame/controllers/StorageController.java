package com.trunggame.controllers;


import com.trunggame.security.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/s3/file")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource byteResource = new ByteArrayResource(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, getContentType(fileName));
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + byteResource.getFilename() + "\"");

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .headers(headers)
                .body(byteResource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }

    private String getContentType(String fileName) throws IOException {
        String contentType = "";
        String extension = fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
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
}
