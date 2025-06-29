package net.rewerk.webstore.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import net.rewerk.webstore.exception.CloudFileNotFound;
import net.rewerk.webstore.exception.InvalidUploadTypeException;
import net.rewerk.webstore.model.entity.Upload;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.transport.dto.request.upload.SignUrlDto;
import net.rewerk.webstore.transport.dto.response.upload.SignUrlResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UploadService {
    @Value("${uploads.product_images_bucket_name}")
    private String productImagesBucketName;
    @Value("${uploads.brand_images_bucket_name}")
    private String brandImagesBucketName;
    @Value("${uploads.review_images_bucket_name}")
    private String reviewImagesBucketName;
    @Value("${uploads.project_id}")
    private String projectId;
    private final Storage storage;

    public UploadService() {
        try {
            ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(
                    new ClassPathResource("gcs-credentials.json").getInputStream()
            );
            storage = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .setProjectId(projectId)
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SignUrlResponseDto signUploadURL(@NotNull SignUrlDto signUrlDto, @NotNull User user) {
        this.checkPermissions(user, signUrlDto.getType());
        String filename = String.format("%s_%s", UUID.randomUUID(), signUrlDto.getFilename());
        String url = this.signV4UploadURL(
                filename,
                signUrlDto.getMime(),
                signUrlDto.getType()
        );
        return SignUrlResponseDto.builder()
                .uploadURL(url)
                .publicURL(this.getUploadPublicURL(filename, signUrlDto.getType()))
                .build();
    }

    public void deleteObject(@NonNull String objectName,
                             @NonNull Upload.Type uploadType
                             ) {
        if (objectName.startsWith("https://")) {
            objectName = objectName.substring(objectName.lastIndexOf("/") + 1);
        }
        Blob blob = storage.get(getBucketName(uploadType), objectName);
        if (blob != null) {
            BlobId blobId = blob.getBlobId();
            storage.delete(blobId);
        } else throw new CloudFileNotFound("Could not find object " + objectName);
    }

    @Async
    public CompletableFuture<Void> deleteObjects(@NonNull List<String> objectNames,
                                                 @NonNull Upload.Type uploadType) throws InterruptedException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String objectName : objectNames) {
            futures.add(CompletableFuture.runAsync(() -> deleteObject(objectName, uploadType)));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private String getUploadPublicURL(String objectName, Upload.Type uploadType) {
        return "https://%s.storage.googleapis.com/%s".formatted(getBucketName(uploadType), objectName);
    }

    private String getBucketName(@NonNull Upload.Type uploadType) {
        if (Upload.Type.PRODUCT_IMAGE.equals(uploadType)) {
            return productImagesBucketName;
        } else if (Upload.Type.BRAND_IMAGE.equals(uploadType)) {
            return brandImagesBucketName;
        } else if (Upload.Type.REVIEW_IMAGE.equals(uploadType)) {
            return reviewImagesBucketName;
        } else throw new InvalidUploadTypeException("Invalid upload type");
    }

    private String signV4UploadURL(@NonNull String objectName,
                                   @NonNull String mime,
                                   @NonNull Upload.Type uploadType
    ) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(getBucketName(uploadType), objectName)).build();
        Map<String, String> extHeaders = new HashMap<>();
        extHeaders.put("Content-Type", mime);
        URL url = storage.signUrl(
                blobInfo,
                15,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withExtHeaders(extHeaders),
                Storage.SignUrlOption.withV4Signature()
        );

        return url.toString();
    }

    private void checkPermissions(@NonNull User user, @NonNull Upload.Type uploadType) {
        if (!User.Role.ADMINISTRATOR.equals(user.getRole()) &&
                !Upload.Type.REVIEW_IMAGE.equals(uploadType)) {
            throw new InvalidUploadTypeException("Upload type not allowed");
        }
    }
}
