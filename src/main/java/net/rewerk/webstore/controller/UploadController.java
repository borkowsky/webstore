package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.OperationInterruptedException;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.transport.dto.request.upload.DeleteDto;
import net.rewerk.webstore.transport.dto.request.upload.MultipleDeleteDto;
import net.rewerk.webstore.transport.dto.request.upload.SignUrlDto;
import net.rewerk.webstore.transport.dto.response.common.SinglePayloadResponseDto;
import net.rewerk.webstore.transport.dto.response.upload.SignUrlResponseDto;
import net.rewerk.webstore.service.UploadService;
import net.rewerk.webstore.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RequestMapping("/api/v1/uploads")
@RestController
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/sign-url")
    public ResponseEntity<SinglePayloadResponseDto<SignUrlResponseDto>> signUploadUrl(
            @Valid @RequestBody SignUrlDto signUrlDto,
            @AuthenticationPrincipal UserDetails user
    ) {
        return ResponseUtils.createSingleResponse(uploadService.signUploadURL(signUrlDto, (User) user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUpload(
            @Valid @RequestBody DeleteDto request
    ) {
        uploadService.deleteObject(request.getFilename(), request.getType());
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
