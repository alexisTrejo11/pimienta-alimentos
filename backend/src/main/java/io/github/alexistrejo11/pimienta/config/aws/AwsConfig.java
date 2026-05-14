package io.github.alexistrejo11.pimienta.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * Wires S3 with static keys from Spring {@code Environment} ({@code .env} is merged by
 * {@link io.github.alexistrejo11.pimienta.config.env.DotenvEnvironmentPostProcessor}). The AWS SDK’s
 * default chain only reads OS env / {@code ~/.aws/credentials}, not Spring properties.
 */
@Configuration
public class AwsConfig {

  @Value("${aws.region}")
  private String region;

  @Bean(destroyMethod = "close")
  public S3Client s3Client(
      @Value("${AWS_ACCESS_KEY_ID:}") String accessKeyId,
      @Value("${AWS_SECRET_ACCESS_KEY:}") String secretAccessKey) {
    var b = S3Client.builder().region(Region.of(region));
    if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
      b.credentialsProvider(
          StaticCredentialsProvider.create(
              AwsBasicCredentials.create(accessKeyId.trim(), secretAccessKey.trim())));
    } else {
      b.credentialsProvider(DefaultCredentialsProvider.create());
    }
    return b.build();
  }

  @Bean(destroyMethod = "close")
  public S3Presigner s3Presigner(
      @Value("${AWS_ACCESS_KEY_ID:}") String accessKeyId,
      @Value("${AWS_SECRET_ACCESS_KEY:}") String secretAccessKey) {
    var b = S3Presigner.builder().region(Region.of(region));
    if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
      b.credentialsProvider(
          StaticCredentialsProvider.create(
              AwsBasicCredentials.create(accessKeyId.trim(), secretAccessKey.trim())));
    } else {
      b.credentialsProvider(DefaultCredentialsProvider.create());
    }
    return b.build();
  }
}
