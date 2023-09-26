package es.grouppayments.backend._shared.domain;

public interface ThreadRunner {
    void run(Runnable runnable);
}
