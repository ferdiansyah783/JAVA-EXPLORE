package com.domain.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.domain.models.entities.File;
import com.domain.response.ResponseFileHandler;
import com.domain.response.ResponseMessageHandler;
import com.domain.services.FileStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("")
    public ResponseEntity<ResponseMessageHandler> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            fileStorageService.store(file);

            message = "Upload File Success: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageHandler(message));
        } catch (Exception e) {
            message = "Upload File Failed: " + file.getOriginalFilename() + " !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageHandler(message));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseFileHandler>> getListFiles() {
        List<ResponseFileHandler> files = fileStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFileHandler(dbFile.getName(), fileDownloadUri, dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") String fileId) {
        File file = fileStorageService.getFile(fileId);

        return ResponseEntity.ok()

                // Download Image From DB
                // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                // file.getName() + "\"")

                // Show Image From Databse
                .contentType(MediaType.IMAGE_PNG)
                .body(file.getData());
    }

}
