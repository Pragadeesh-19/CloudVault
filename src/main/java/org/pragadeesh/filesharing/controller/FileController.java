package org.pragadeesh.filesharing.controller;

import jakarta.transaction.Transactional;
import org.pragadeesh.filesharing.model.FileMetadata;
import org.pragadeesh.filesharing.model.User;
import org.pragadeesh.filesharing.service.AzureBlobService;
import org.pragadeesh.filesharing.service.CustomUserDetailsService;
import org.pragadeesh.filesharing.service.FileMetadataServices;
import org.pragadeesh.filesharing.util.JwtUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final AzureBlobService azureBlobService;
    private final FileMetadataServices fileMetadataServices;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public FileController(AzureBlobService azureBlobService, FileMetadataServices fileMetadataServices, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.azureBlobService = azureBlobService;
        this.fileMetadataServices = fileMetadataServices;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file,
                                             @RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            User owner = (User) customUserDetailsService.loadUserByUsername(username);

            String blobUrl = azureBlobService.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getSize());

            fileMetadataServices.saveFileMetadata(file.getOriginalFilename(), file.getSize(), owner, blobUrl);

            return ResponseEntity.ok("File uploaded successfully " + blobUrl);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = azureBlobService.downloadFile(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(fileStream));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Transactional
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        try {
            azureBlobService.deleteFile(fileName);

            fileMetadataServices.deleteFileMetadata(fileName);
            return ResponseEntity.ok("File deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    @GetMapping("/share/{fileName}")
    public ResponseEntity<String> shareFile(@PathVariable String fileName) {
        try {
            String tempUrl = azureBlobService.generateTemporaryUrl(fileName);
            return ResponseEntity.ok(tempUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate URL: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileMetadata>> listUserFiles(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            User owner = (User) customUserDetailsService.loadUserByUsername(username);

            List<FileMetadata> userFiles = fileMetadataServices.getFilesByUser(owner);
            return ResponseEntity.ok(userFiles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
