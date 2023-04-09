package silgar.store.service;

import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import silgar.store.exception.ConflictException;
import silgar.store.minio.MinioConfiguration;
import silgar.store.minio.MinioConfigurationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class StoreS3ServiceImpl implements IStoreService {

    @Autowired
    private MinioConfiguration minioConfiguration;
    @Autowired
    private MinioConfigurationProperties minioConfigurationProperties;

    @Override
    public void store (MultipartFile file) {
        try {
            Path path = Path.of(file.getOriginalFilename());
            InputStream fileInputStream = file.getInputStream();
            String contentType = file.getContentType();

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(path.toString())
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .contentType(contentType)
                    .build();

            minioConfiguration.minioClient().putObject(args);
        } catch (Exception ioe) {
            throw new ConflictException ("Error storing file. ", ioe,  false, true);
        }
    }

}
