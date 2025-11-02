package org.executor.service.sec07;

import java.util.concurrent.ExecutorService;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService){
        this.executorService = executorService;
    }

    public ProductDTO getProductDTO(int id) throws Exception{
        var product = executorService.submit(()->Client.getProduct(id));
        var rating = executorService.submit(() -> Client.getRating(id));

       // var rating = Client.getRating(id); You could have done that but It is better to void because some people
        //return new ProductDTO(id, product.get(), Client.getRating(id)); //If they do this then Rating call will happen only after product service call\
        return new ProductDTO(id, product.get(), rating.get());
    }
}
