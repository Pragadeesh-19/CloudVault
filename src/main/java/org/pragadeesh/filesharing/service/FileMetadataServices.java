package org.pragadeesh.filesharing.service;

import org.pragadeesh.filesharing.model.FileMetadata;
import org.pragadeesh.filesharing.model.User;
import org.pragadeesh.filesharing.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileMetadataServices {

    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadataServices(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public void saveFileMetadata(String fileName, long fileSize, User owner, String blobUrl) {
        FileMetadata fileMetadata = new FileMetadata(fileName, fileSize, owner, blobUrl);
        fileMetadataRepository.save(fileMetadata);
    }

    public void deleteFileMetadata(String fileName) {
        fileMetadataRepository.deleteByFileName(fileName);
    }

    public List<FileMetadata> getFilesByUser(User owner) {
        return fileMetadataRepository.findByOwner(owner);
    }
}
