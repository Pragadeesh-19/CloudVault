# Cloud-Based File Storage System (Azure Blob Storage)

## Project Description
This project is a cloud-based file storage system that allows users to upload, download, delete, and manage files using Azure Blob Storage. The application provides user authentication via JWT and enables secure file sharing through temporary URLs. It also manages file metadata in a PostgreSQL database using Spring Data JPA.

## Features
- **User Authentication**: JWT-based authentication to ensure secure access.
- **File Upload**: Upload files to Azure Blob Storage.
- **File Download**: Download files securely from Azure Blob Storage.
- **File Deletion**: Delete files from Azure Blob Storage.
- **Temporary URL Generation**: Share files using temporary URLs.
- **File Listing**: List all files uploaded by the user.
- **File Metadata Management**: Track metadata of uploaded files in a PostgreSQL database.

## Tech Stack
- **Backend**: Spring Boot
- **Security**: Spring Security for JWT-based authentication
- **Cloud Storage**: Azure Blob Storage using Azure SDK
- **Database**: PostgreSQL (for storing file metadata)
- **Persistence**: Spring Data JPA for managing database entities
- **Testing**: Postman for testing API endpoints

## API Endpoints

### Upload File

- **URL**: `/files/upload`
- **Method**: `POST`
- **Headers**: 
  - `Authorization: Bearer <JWT token>`
- **Body**: 
  - `file` (MultipartFile): The file to upload.
- **Response**: 
  - 200 OK: Returns the blob URL for the uploaded file.
  - 400 Bad Request: If the upload fails.

### Download File

- **URL**: `/files/download/{fileName}`
- **Method**: `GET`
- **Response**: 
  - 200 OK: Returns the file as an attachment.
  - 400 Bad Request: If the file is not found.

### Delete File

- **URL**: `/files/delete/{fileName}`
- **Method**: `DELETE`
- **Response**: 
  - 200 OK: Confirmation of successful deletion.
  - 400 Bad Request: If the file is not found or deletion fails.

### Share File (Generate Temporary URL)

- **URL**: `/files/share/{fileName}`
- **Method**: `GET`
- **Response**: 
  - 200 OK: Returns a temporary URL for file access.
  - 400 Bad Request: If URL generation fails.

### List User Files

- **URL**: `/files/list`
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <JWT token>`
- **Response**: 
  - 200 OK: Returns a list of file metadata owned by the authenticated user.
  - 400 Bad Request: If an error occurs.

## Project Setup

1. CLone the repository
   ```
   git clone https://github.com/Pragadeesh-19/CloudVault.git
   cd CloudVault
   ```

2. Configure Azure Storage and PostgreSQL:
   Update the application.properties file with your Azure Blob Storage Credentials and PostgreSQL details.
   ```
   azure.storage.connection-string=<your-azure-connection-string>
   spring.datasource.url=jdbc:postgresql://localhost:5432/<your-database>
   spring.datasource.username=<your-db-username>
   spring.datasource.password=<your-db-password>
   ```

3. Build and Run the project:
   ```
   mvn clean install
   mvn spring-boot:run
   ```

4. Test the API using postman:
   Use Postman to interact with the various API endpoints for file upload, download, deletion, and sharing

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any changes or enhancements.
