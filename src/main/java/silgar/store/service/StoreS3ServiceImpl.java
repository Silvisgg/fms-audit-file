package silgar.store.service;

import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import silgar.store.minio.MinioConfiguration;
import silgar.store.minio.MinioConfigurationProperties;

import java.io.InputStream;
import java.nio.file.Path;

@Service
public class StoreS3ServiceImpl implements IStoreService {

    @Autowired
    private MinioConfiguration minioConfiguration;
    @Autowired
    private MinioConfigurationProperties minioConfigurationProperties;

    @Override
    public void store (MultipartFile file) throws Exception {
        Path path = Path.of(file.getOriginalFilename());
        InputStream fileInputStream = file.getInputStream();
        String contentType = file.getContentType();

        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(path.toString())
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .contentType(contentType)
                    .build();

            minioConfiguration.minioClient().putObject(args);
        } catch (Exception e) {
            throw new Exception("Error while fetching files in Minio", e);
        }
    }

}
