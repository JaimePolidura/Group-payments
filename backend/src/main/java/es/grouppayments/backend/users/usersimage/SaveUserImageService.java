package es.grouppayments.backend.users.usersimage;

import es.grouppayments.backend._shared.domain.ResourceDownloader;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;
import es.grouppayments.backend.users.usersimage.processor.ImageProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public final class SaveUserImageService {
    private final UsersImagesService userImageService;
    private final ResourceDownloader resourceDownloader;
    private final ImageProcessorService imageProcessor;

    public int save(String photoUrl){
        try{
            return trySaveUserImage(photoUrl);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private int trySaveUserImage(String photoUrl) throws IOException {
        byte[] content = this.resourceDownloader.download(photoUrl);

        return this.userImageService.save(imageProcessor.apply(content));
    }
}
