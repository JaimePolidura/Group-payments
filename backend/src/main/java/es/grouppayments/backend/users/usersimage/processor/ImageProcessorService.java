package es.grouppayments.backend.users.usersimage.processor;

import es.grouppayments.backend.users.usersimage.processor.processors.ResizeImageProcessor;
import es.grouppayments.backend.users.usersimage.processor.processors.ResolutionImageProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public final class ImageProcessorService implements MainImageProcessor{
    public static final Set<ImageProcessor> pipeline = Set.of(new ResizeImageProcessor(), new ResolutionImageProcessor());

    @Override
    public byte[] apply(byte[] inital) {
        for (ImageProcessor imageProcessor : pipeline) {
            inital = imageProcessor.apply(inital);
        }

        return inital;
    }
}
