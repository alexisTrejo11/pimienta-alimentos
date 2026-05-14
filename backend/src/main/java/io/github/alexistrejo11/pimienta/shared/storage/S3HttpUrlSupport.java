package io.github.alexistrejo11.pimienta.shared.storage;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Helpers for S3 object references: strip signing query strings, resolve object keys from URLs, and
 * build canonical HTTPS URLs (virtual-hosted style).
 */
public final class S3HttpUrlSupport {

  private S3HttpUrlSupport() {}

  /**
   * Removes the HTTP query string and fragment. Does not decode the path.
   *
   * <p>Use when normalizing a URL for logging or when the query carries temporary signature
   * parameters that must not be persisted.
   */
  public static String withoutQueryAndFragment(String url) {
    if (url == null) {
      return null;
    }
    String t = url.strip();
    int q = t.indexOf('?');
    int h = t.indexOf('#');
    int cut = t.length();
    if (q >= 0) {
      cut = Math.min(cut, q);
    }
    if (h >= 0) {
      cut = Math.min(cut, h);
    }
    return cut == t.length() ? t : t.substring(0, cut);
  }

  /**
   * Accepts a plain object key or a full S3 / presigned HTTP URL and returns the key used in
   * {@code PutObject}/{@code DeleteObject}.
   *
   * @param bucketName used to strip optional {@code /bucket/} prefix from path-style URLs
   */
  public static String objectKeyFromUrlOrKey(String fileUrlOrKey, String bucketName) {
    if (fileUrlOrKey == null || fileUrlOrKey.isBlank()) {
      throw new IllegalArgumentException("Storage reference is blank");
    }
    String t = fileUrlOrKey.strip();
    if (!t.contains("://")) {
      return t.startsWith("/") ? t.substring(1) : t;
    }
    URI uri = URI.create(t);
    String path = uri.getPath();
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("Could not resolve object key from URL");
    }
    String key = path.startsWith("/") ? path.substring(1) : path;
    if (bucketName != null && !bucketName.isBlank() && key.startsWith(bucketName + "/")) {
      key = key.substring(bucketName.length() + 1);
    }
    int q = key.indexOf('?');
    if (q >= 0) {
      key = key.substring(0, q);
    }
    return URLDecoder.decode(key, StandardCharsets.UTF_8);
  }

  /**
   * Virtual-hosted–style object URL ({@code https://bucket.s3.region.amazonaws.com/key}). Path
   * segments are percent-encoded; the result has no query string (not presigned).
   */
  public static String virtualHostedObjectUrl(String bucket, String region, String key) {
    if (bucket == null || bucket.isBlank()) {
      throw new IllegalArgumentException("bucket must not be blank");
    }
    if (region == null || region.isBlank()) {
      throw new IllegalArgumentException("region must not be blank");
    }
    if (key == null || key.isBlank()) {
      throw new IllegalArgumentException("key must not be blank");
    }
    String encodedKey =
        Arrays.stream(key.split("/", -1))
            .map(
                seg ->
                    java.net.URLEncoder.encode(seg, StandardCharsets.UTF_8)
                        .replace("+", "%20"))
            .collect(Collectors.joining("/"));
    return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + encodedKey;
  }
}
