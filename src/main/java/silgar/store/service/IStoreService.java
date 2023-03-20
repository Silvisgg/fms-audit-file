package silgar.store.service;

import org.springframework.web.multipart.MultipartFile;


public interface IStoreService {

    public void store (MultipartFile file) throws Exception;

}
