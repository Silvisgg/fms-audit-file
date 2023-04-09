package silgar.store.service;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface IStoreService {

    public void store (MultipartFile file);

}
