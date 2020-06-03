package com.dts.moviecatalogservice.services;

import com.dts.moviecatalogservice.models.CatalogItem;
import com.dts.moviecatalogservice.models.Movie;
import com.dts.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class MovieInfo {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
