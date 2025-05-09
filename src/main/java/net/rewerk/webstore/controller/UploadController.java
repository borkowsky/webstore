package net.rewerk.webstore.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.OperationInterruptedException;
import net.rewerk.webstore.model.dto.request.upload.DeleteDto;
import net.rewerk.webstore.model.dto.request.upload.MultipleDeleteDto;
import net.rewerk.webstore.model.dto.request.upload.SignUrlDto;
import net.rewerk.webstore.model.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.model.dto.response.upload.SignUrlResponseDto;
import net.rewerk.webstore.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@RequestMapping("/api/v1/uploads")
@RestController
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/sign-url")
    public ResponseEntity<SinglePayloadResponseDto<SignUrlResponseDto>> signUploadUrl(
            @Valid @RequestBody SignUrlDto signUrlDto
    ) {
        HttpStatus status = HttpStatus.OK;
        UUID uuid = UUID.randomUUID();
        String filename = String.format("%s_%s", uuid, signUrlDto.getFilename());
        String result = null;
        try {
            result = uploadService.signV4UploadURL(
                    filename,
                    signUrlDto.getMime(),
                    signUrlDto.getType()
            );
        } catch (Exception e) {
            System.out.println("Sign URL failed: " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(
                SinglePayloadResponseDto.<SignUrlResponseDto>builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .payload(SignUrlResponseDto.builder()
                                .uploadURL(result)
                                .publicURL(uploadService.getUploadPublicURL(filename, signUrlDto.getType()))
                                .build())
                        .build(),
                status
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUpload(
            @Valid @RequestBody DeleteDto request
    ) {
        try {
            uploadService.deleteObject(request.getFilename(), request.getType());
        } catch (Exception e) {
            throw new EntityNotFoundException("Object not found in storage");
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<Void> deleteMultipleUploads(
            @Valid @RequestBody MultipleDeleteDto request
    ) {
        try {
            uploadService.deleteObjects(Arrays.asList(request.getFilenames()), request.getType()).join();
        } catch (InterruptedException e) {
            throw new OperationInterruptedException("Failed to delete multiple objects");
        }
        return ResponseEntity.noContent().build();
    }
}
