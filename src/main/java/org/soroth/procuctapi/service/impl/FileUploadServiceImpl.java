package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.file.FileResponse;
import org.soroth.procuctapi.entity.FileUpload;
import org.soroth.procuctapi.mapper.FileUploadMapper;
import org.soroth.procuctapi.repository.FileRepository;
import org.soroth.procuctapi.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileRepository fileRepository;
    private final FileUploadMapper fileUploadMapper;
    @Value("${file.storage-location}")
    private  String filesStorageLocation;


    @Override
    public FileResponse upload(MultipartFile file) {
        return uploadFile(file);
    }

    @Override
    public List<FileResponse> uploadMultipleFiles(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @Override
    public FileResponse findByName(String name) {
        var file =  fileRepository.findByName(name).orElseThrow(
                ()-> new NoSuchElementException("File with name " + name + " not found")
        );
        return fileUploadMapper.mapToResponse(file);
    }

    @Override
    public Page<FileResponse> findAll(int pageNumber, int pageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortById);

        return fileRepository.findAll(pageable)
                .map(fileUploadMapper::mapToResponse);
    }

    @Override
    public void deleteByName(String name) {
        var file =  fileRepository.findByName(name).orElseThrow(
                ()-> new NoSuchElementException("File with name " + name + " not found")
        );
        fileRepository.delete(file);
        // delete inside the storageLocation
        String fullPath = filesStorageLocation + file.getName()+"."+file.getExtension();
        Path pathToDelete = Paths.get(fullPath);
        try {
            var result =   Files.deleteIfExists(pathToDelete);
            if(!result){
                throw new NoSuchElementException("File with name " + name + " not found");
            }
        } catch (IOException e) {
            // TODO: customize and handle later
            e.printStackTrace();
            throw new RuntimeException("File with name " + name + " not found");
        }
    }

    // file for saving the upload file to the local machine (server)
    private FileResponse uploadFile(MultipartFile file)  {
        // 1. Rename the file
        String name = UUID.randomUUID().toString();
        // 2. Get extension of the file
        String ext = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = name + "." + ext ;

        // 3. construct path ( )
        Path path = Paths.get(filesStorageLocation + fileName);

        // 4. (Save) copy the file to Local machine
        try {
            Files.copy(
                    file.getInputStream(),
                    path
            );
            // StandardCopyOption.REPLACE_EXISTING
        }catch (IOException e){
            throw new IllegalArgumentException("Error uploading file: " + e.getMessage(), e);
        }
        // 5. save fileUpload data to database
        var fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setExtension(ext);
        fileUpload.setCaption("ISTAD media server");
        fileUpload.setSize(file.getSize());
        fileUpload.setMediaType(file.getContentType());
        fileRepository.save(fileUpload);

        return fileUploadMapper.mapToResponse(fileUpload);
    }
}
