package net.rewerk.webstore.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

@RequestMapping("/api/v1/upload")
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
                    signUrlDto.getMime()
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
                                .publicURL(uploadService.getUploadPublicURL(filename))
                                .build())
                        .build(),
                status
        );
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<BaseResponseDto> delete(
        @PathVariable @NotNull String name
    ) {
       HttpStatus status = HttpStatus.NO_CONTENT;
       try {
           uploadService.deleteObject(name);
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

    @DeleteMapping({"/", ""})
    public ResponseEntity<BaseResponseDto> deleteMultiple(
            @Valid @RequestBody MultipleDeletionDto request
            ) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        try {
            uploadService.deleteObjects(Arrays.asList(request.getFilenames())).join();
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
