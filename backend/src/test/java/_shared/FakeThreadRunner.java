package _shared;

import es.grouppayments.backend._shared.domain.ThreadRunner;

public final class FakeThreadRunner implements ThreadRunner {
    @Override
    public void run(Runnable runnable) {
        runnable.run();
    }
}

