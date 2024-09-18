package org.pragadeesh.filesharing.repository;

import org.pragadeesh.filesharing.model.FileMetadata;
import org.pragadeesh.filesharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
    void deleteByFileName(String fileName);

    List<FileMetadata> findByOwner(User owner);
}
