package org.pragadeesh.filesharing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
public class FileMetadata {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "upload_data", nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "blob_url", nullable = false)
    private String blobUrl;

    public FileMetadata() {}

    public FileMetadata(String fileName, long fileSize, User owner, String blobUrl) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadDate = LocalDateTime.now();
        this.owner = owner;
        this.blobUrl = blobUrl;
    }
}
