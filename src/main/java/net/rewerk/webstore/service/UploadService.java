package net.rewerk.webstore.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import lombok.NonNull;
import net.rewerk.webstore.exception.CloudFileNotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UploadService {
    @Value("${uploads.bucket_name}")
    private String bucketName;
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

    public String signV4UploadURL(@NonNull String objectName,
                                  @NonNull String mime) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();
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

    public void deleteObject(@NonNull String objectName) {
        if (objectName.startsWith("https://")) {
            objectName = objectName.substring(objectName.lastIndexOf("/") + 1);
        }
        Blob blob = storage.get(bucketName, objectName);
        if (blob != null) {
            BlobId blobId = blob.getBlobId();
            storage.delete(blobId);
        } else throw new CloudFileNotFound("Could not find object " + objectName);
    }

    @Async
    public CompletableFuture<Void> deleteObjects(List<String> objectNames) throws InterruptedException {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (String objectName : objectNames) {
            futures.add(CompletableFuture.runAsync(() -> deleteObject(objectName)));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    public String getUploadPublicURL(String objectName) {
        return String.format("https://%s.storage.googleapis.com/%s", bucketName, objectName);
    }
}
