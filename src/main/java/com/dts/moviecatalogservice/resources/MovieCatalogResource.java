package com.dts.moviecatalogservice.resources;

import com.dts.moviecatalogservice.models.CatalogItem;
import com.dts.moviecatalogservice.models.Movie;
import com.dts.moviecatalogservice.models.Rating;
import com.dts.moviecatalogservice.models.UserRating;
import com.dts.moviecatalogservice.services.MovieInfo;
import com.dts.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

/*    @Autowired
    private DiscoveryClient discoveryClient;*/

    @RequestMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getFallBackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating ratings = userRatingInfo.getUserRating(userId);
        //discoveryClient.getInstancesById();
        return ratings.getUserRating()
                .stream()
                .map(rating -> {
                    //для RestTemplate
                    return movieInfo.getCatalogItem(rating);
                    //для WebClient
/*                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)//Mono означает что это будет выполняться асинхронно
                            .block();// это позволит нам дождаться асинхронной обработки, еслибы нам не нужно было дожидаться результата, то мы бы продолжили выполнять свой код*/

                })
                .collect(Collectors.toList());


    }

    public List<CatalogItem> getFallBackCatalog(@PathVariable String userId) {
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }



}
