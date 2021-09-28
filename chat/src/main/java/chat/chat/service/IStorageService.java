package chat.chat.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    public void init();

    public void store(MultipartFile file, String filename);

    public Resource load(String filename);
}
