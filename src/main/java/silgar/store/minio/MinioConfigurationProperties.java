package silgar.store.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.minio")
public class MinioConfigurationProperties {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration connectTimeout;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration writeTimeout;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration readTimeout;

    private boolean checkBucket;
    private boolean createBucket;

}
