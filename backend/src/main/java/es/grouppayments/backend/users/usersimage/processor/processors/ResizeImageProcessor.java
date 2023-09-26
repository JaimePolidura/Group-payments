package es.grouppayments.backend.users.usersimage.processor.processors;

import es.grouppayments.backend.users.usersimage.processor.ImageProcessor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

//Resize to 1x1
public final class ResizeImageProcessor implements ImageProcessor {
    @SneakyThrows
    @Override
    public byte[] apply(byte[] input) {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(input));
        boolean alreadyResized = image.getWidth() == image.getHeight();

        if(alreadyResized)
            return bufferedImageToBytes(image);

        return image.getWidth() < image.getHeight() ?
                resizeTakingWidth(image) :
                resizeTakingHeight(image);
    }

    private byte[] resizeTakingHeight(BufferedImage nonResizedImage) {
        var resizedImage = new BufferedImage(nonResizedImage.getHeight(), nonResizedImage.getHeight(), nonResizedImage.getType());
        final int maxHeigh = nonResizedImage.getHeight();
        final int maxWidth = nonResizedImage.getHeight();
        final int xGap = (nonResizedImage.getWidth() - nonResizedImage.getHeight()) / 2;

        for(int y = 0;  y < maxHeigh; y++)
            for(int x = 0; x < maxWidth; x++)
                resizedImage.setRGB(x, y, nonResizedImage.getRGB(x + xGap, y));

        return this.bufferedImageToBytes(resizedImage);
    }

    private byte[] resizeTakingWidth(BufferedImage nonResizedImage) {
        var resizedImage = new BufferedImage(nonResizedImage.getWidth(), nonResizedImage.getWidth(), nonResizedImage.getType());
        final int maxHeigh = nonResizedImage.getWidth();
        final int maxWidth = nonResizedImage.getWidth();
        final int yGap = (nonResizedImage.getHeight() - nonResizedImage.getWidth()) / 2;

        for(int y = 0;  y < maxHeigh; y++)
            for(int x = 0; x < maxWidth; x++)
                resizedImage.setRGB(x, y, nonResizedImage.getRGB(x, y + yGap));

        return this.resizeTakingWidth(resizedImage);
    }

    @SneakyThrows
    private byte[] bufferedImageToBytes(BufferedImage bufferedImage){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return baos.toByteArray();
    }
}
