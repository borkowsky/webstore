package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.upload.DeleteDto;
import net.rewerk.webstore.model.dto.request.upload.MultipleDeletionDto;
import net.rewerk.webstore.model.dto.request.upload.SignUrlDto;
import net.rewerk.webstore.model.dto.response.BaseResponseDto;
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
    public ResponseEntity<SinglePayloadResponseDto<SignUrlResponseDto>> signUrl(
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

    @DeleteMapping({"/", ""})
    public ResponseEntity<BaseResponseDto> delete(
            @Valid @RequestBody DeleteDto request
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            uploadService.deleteObject(request.getFilename(), request.getType());
        } catch (Exception e) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(
                BaseResponseDto.builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .build(),
                status
        );
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<BaseResponseDto> deleteMultiple(
            @Valid @RequestBody MultipleDeletionDto request
    ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            uploadService.deleteObjects(Arrays.asList(request.getFilenames()), request.getType()).join();
        } catch (Exception e) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(
                BaseResponseDto.builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .build(),
                status
        );
    }
}
