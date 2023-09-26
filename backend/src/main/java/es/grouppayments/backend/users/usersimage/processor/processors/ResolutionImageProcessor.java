package es.grouppayments.backend.users.usersimage.processor.processors;

import es.grouppayments.backend.users.usersimage.processor.ImageProcessor;
import lombok.SneakyThrows;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public final class ResolutionImageProcessor implements ImageProcessor {
    private static final int RESOLUTION = 1000;

    @Override
    @SneakyThrows
    public byte[] apply(byte[] input) {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(input));
        var resizedImage = Scalr.resize(originalImage, RESOLUTION, RESOLUTION);

        return bufferedImageToBytes(resizedImage);
    }

    @SneakyThrows
    private byte[] bufferedImageToBytes(BufferedImage bufferedImage){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return baos.toByteArray();
    }

}
