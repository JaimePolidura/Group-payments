package es.grouppayments.backend.users.usersimage._shared.infrastructure;

import es.grouppayments.backend._shared.domain.ResourceDownloader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public final class ImageDownloader implements ResourceDownloader {
    @Override
    public byte[] download(String url) throws IOException {
        try(InputStream inputStream = new URL(url).openStream()){
            return inputStream.readAllBytes();
        }
    }
}
