package es.grouppayments.backend._shared.infrastructure.cqrs;

import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ApplicationCQRSClassMapper {
    private final ApplicationContext context;
    private final Reflections reflections;

    public ApplicationCQRSClassMapper(ApplicationContext context) {
        this.context = context;
        this.reflections = new Reflections("es.jaime.gateway");
    }

    public<I, H> Map<Class<I>, H> find (Class<H> classToFind) {
        Set<Class<? extends H>> handlers = reflections.getSubTypesOf(classToFind);
        Map<Class<I>, H> results = new HashMap<>();

        for(Class<? extends H> handler : handlers){
            ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<I> queryClass = (Class<I>) paramType.getActualTypeArguments()[0];

            H newInstance = context.getBean(handler);

            results.put(queryClass, newInstance);
        }

        return results;
    }
}
