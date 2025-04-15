package com.sprint.mission.discodeit.storage.s3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

//@RestController
//@RequestMapping("api/s3")
public class AWSS3Test {

  private final S3Client s3Client;
  private final S3Presigner s3Presigner;
  private final String bucketName;

  private static final String BASE_PATH
      = "src/main/java/com/sprint/mission/discodeit/storage/s3/data/";

  public AWSS3Test() throws IOException {
    Properties props = new Properties();
    try (InputStream input = new FileInputStream(".env")) {
      props.load(input);
    }

    AwsBasicCredentials credentials = AwsBasicCredentials.create(
        props.getProperty("AWS_S3_ACCESS_KEY"),
        props.getProperty("AWS_S3_SECRET_KEY")
    );

    bucketName = props.getProperty("AWS_S3_BUCKET");

    s3Client = S3Client.builder()
        .region(Region.of(props.getProperty("AWS_S3_REGION")))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();

    s3Presigner = S3Presigner.builder()
        .region(Region.of(props.getProperty("AWS_S3_REGION")))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }

  @PostMapping("/upload")
  public void uploadFileTest() {
    String key = "test-upload.txt";
    Path path = Path.of(BASE_PATH + "sample.txt");

    PutObjectRequest putRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    s3Client.putObject(putRequest, path);
    System.out.println("✅ Upload complete");
  }

  @GetMapping("/download")
  public void downloadFileTest() throws IOException {
    String key = "test-upload.txt";
    GetObjectRequest getRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    try (InputStream s3Object = s3Client.getObject(getRequest);
        FileOutputStream out = new FileOutputStream(BASE_PATH + "downloaded.txt")) {
      s3Object.transferTo(out);
    }

    System.out.println("✅ Download complete");
  }

  @GetMapping("/pre")
  public void generatePresignedUrlTest() {
    String key = "test-upload.txt";

    GetObjectRequest getRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(10))
        .getObjectRequest(getRequest)
        .build();

    URL presignedUrl = s3Presigner.presignGetObject(presignRequest).url();
    System.out.println("✅ Presigned URL: " + presignedUrl);
  }
}
