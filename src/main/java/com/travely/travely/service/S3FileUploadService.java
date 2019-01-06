package com.travely.travely.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ds on 2018-12-25.
 */


@Slf4j
@Service
public class S3FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;


    public S3FileUploadService(final AmazonS3Client amazonS3Client) {

        this.amazonS3Client = amazonS3Client;
    }


    public String upload(String classify, MultipartFile uploadFile) throws IOException {

        String origName = uploadFile.getOriginalFilename();
        String url;
        classify = classify + "/";
        long fileSize = 10 * 1024 * 1024;               // Set file size 5MB

        try {
            final String ext = origName.substring(origName.lastIndexOf('.'));
            final String saveFileName = getUuid() + ext;
            File file = new File(System.getProperty("user.dir") + saveFileName);
            if (fileSize < file.length()) {
                return "파일 사이즈를 초과했습";
            }
            uploadFile.transferTo(file);
            uploadOnS3(classify + saveFileName, file);
            url = defaultUrl + classify + saveFileName;
            file.delete();

        } catch (StringIndexOutOfBoundsException e) {

            url = null;
        }
        return url;
    }

    private static String getUuid() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void uploadOnS3(final String fileName, final File file) {

        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        final PutObjectRequest request = new PutObjectRequest(bucket, fileName, file);
        final Upload upload = transferManager.upload(request);

        try {

            upload.waitForCompletion();

        } catch (AmazonClientException amazonClientException) {

            log.error(amazonClientException.getMessage());

        } catch (InterruptedException e) {

            log.error(e.getMessage());
        }
    }
}