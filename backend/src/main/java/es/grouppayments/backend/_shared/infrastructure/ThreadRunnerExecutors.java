package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend._shared.domain.ThreadRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public final class ThreadRunnerExecutors implements ThreadRunner {
    private final ExecutorService executors;

    public ThreadRunnerExecutors(){
        this.executors = Executors.newCachedThreadPool();
    }

    @Override
    public void run(Runnable runnable) {
        this.executors.submit(runnable);
    }
}
