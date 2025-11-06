package org.completable.future.sec09;




import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService){
        this.executorService = executorService;
    }

    public org.completable.future.sec09.ProductDTO getProductDTO(int id) throws Exception{

        var product = CompletableFuture.supplyAsync(()-> org.completable.future.sec09.Client.getProduct(id), executorService)
                .exceptionally(ex -> null);

        var rating = CompletableFuture.supplyAsync(()-> org.completable.future.sec09.Client.getRating(id), executorService)
                .exceptionally(ex -> -1);


       // var rating = Client.getRating(id); You could have done that but It is better to void because some people
        //return new ProductDTO(id, product.get(), Client.getRating(id)); //If they do this then Rating call will happen only after product service call\
        return new ProductDTO(id, product.join(), rating.join());
    }
}
