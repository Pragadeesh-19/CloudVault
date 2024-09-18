package org.pragadeesh.filesharing.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.OffsetDateTime;

@Service
public class AzureBlobService {

    private final BlobContainerClient blobContainerClient;

    public AzureBlobService(@Value("${azure.storage.connection-string}") String connectionString,
                            @Value("${azure.storage.container-name}") String containerName) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        this.blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadFile(InputStream filestream, String fileName, long fileSize) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(filestream, fileSize, true);
        return blobClient.getBlobUrl();
    }

    public InputStream downloadFile(String fileName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        return blobClient.openInputStream();
    }

    public void deleteFile(String fileName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.delete();
    }

    public String generateTemporaryUrl(String fileName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(
                OffsetDateTime.now().plusDays(1), // set url to expire after 24 hours.
                BlobSasPermission.parse("r")); // only allow read-only access
        String sasToken = blobClient.generateSas(sasSignatureValues);
        return blobClient.getBlobUrl() + "?" + sasToken;
    }

}
