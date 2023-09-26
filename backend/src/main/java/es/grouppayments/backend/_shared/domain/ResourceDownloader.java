package es.grouppayments.backend._shared.domain;

import java.io.IOException;

public interface ResourceDownloader {
    byte[] download(String url) throws IOException;
}
