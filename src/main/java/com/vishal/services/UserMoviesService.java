package com.vishal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vishal.models.Constants;
import com.vishal.models.Movie;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Service which handles querying movies corresponding to user
 */
@Service
public class UserMoviesService {

	final static Logger logger = Logger.getLogger(UserMoviesService.class);

	@Autowired
	private MovieListService movieListSvc;

	@Autowired
	private MovieArtService movieArtSvc;

	@Autowired
	private MovieTitleService movieTitleSvc;

	private final ExecutorService THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(Constants.THREAD_POOL_SIZE);

	/**
	 * Creates an Observable that streams movies for given userId
	 */
	public Observable<Movie> userMovies(Integer userId) {

		return movieListSvc.userMovieIds(userId).retry(Constants.NUM_RETRIES).flatMap(m -> {

			logger.debug("querying title and image for the movieid " + m);

			Observable<String> tit = movieTitleSvc.movieTitle(m);
			Observable<String> img = movieArtSvc.movieImage(m);

			return Observable.zip(tit, img, (title, image) -> new Movie(title, image, m))
					.subscribeOn(Schedulers.from(THREAD_POOL_EXECUTOR))
					.doOnError(e -> System.out.println("Failed to fetch data"));
		});
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate r = new RestTemplate();
		List<HttpMessageConverter<?>> converters = r.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				List<MediaType> mediaTypes = new ArrayList<>();
				mediaTypes.add(MediaType.TEXT_HTML);
				((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypes);
			}
		}
		return r;
	}

	public void setMovieListService(MovieListService movieListSvc) {
		this.movieListSvc = movieListSvc;
	}

	public void setMovieTitleService(MovieTitleService movieTitleSvc) {
		this.movieTitleSvc = movieTitleSvc;
	}

	public void setMovieArtService(MovieArtService movieArtSvc) {
		this.movieArtSvc = movieArtSvc;
	}

}
