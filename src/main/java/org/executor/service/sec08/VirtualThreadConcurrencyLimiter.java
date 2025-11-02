package org.executor.service.sec08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class VirtualThreadConcurrencyLimiter implements AutoCloseable{

    static final Logger log = LoggerFactory.getLogger(VirtualThreadConcurrencyLimiter.class);

    private final ExecutorService executor;

    private final Semaphore semaphore;

    private final Queue<Callable<?>> queue;

    public VirtualThreadConcurrencyLimiter(ExecutorService executorService, int limit){
        this.executor = executorService;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <V> Future<V> submit(Callable<V> callable){
        this.queue.add(callable);
        return executor.submit(() -> executeTask());
    }

    private <V> V executeTask(){
        try{
            semaphore.acquire();
            return (V) this.queue.poll().call();
        }catch (Exception e){
            log.error("{}", e);
        }finally {
            semaphore.release();
        }
        return null;
    }


    @Override
    public void close() throws Exception {
        this.executor.close();
    }
}

