package org.executor.service.sec08;

import org.executor.service.sec07.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class MapConcurrent {

    static final Logger log = LoggerFactory.getLogger(MapConcurrent.class);

    void main(){

        var list = IntStream.range(1, 50)
                .boxed()
                .gather(Gatherers.mapConcurrent(3, MapConcurrent::getProductInfo))
                .toList();

        log.info("Size of List {}", list.size());

    }

    //3rd Party
    //Contract : 3 concurrent calls allowed
    private static String getProductInfo(int id){
        var product = Client.getProduct(id);

        log.info("{} -> {}", id, product);
        return product;
    }


}
