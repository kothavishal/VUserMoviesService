package com.vishal.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vishal.datastore.DataService;
import com.vishal.error.Error;
import com.vishal.error.UserMoviesException;
import com.vishal.models.Constants;

import io.reactivex.Observable;

/**
 * Service which handles querying image corresponding to a movie
 */
@Service
public class MovieArtService {

	final static Logger logger = Logger.getLogger(MovieArtService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DataService dataSvc;

	/**
	 * method to fetch movie image for the given movieId
	 */
	public Observable<String> movieImage(Integer movieId) {
		
		if(movieId == null){
			return Observable.error(
					new UserMoviesException("Cannot fetch Movie boxart image as movie id not found ", Error.EXTERNAL_SERVICE));
		}
		
		return Observable.<String> create(sub -> {
			try {
				String result = dataSvc.getImageMap().getIfPresent(movieId);
				if (result == null) {
					result = restTemplate.getForObject(Constants.MOVIE_ART_URL + "/{movieId}", String.class, movieId);
					if (result == null) {
						sub.onError(new UserMoviesException("Movie Image not found for movie id " + movieId,
								Error.EXTERNAL_SERVICE));
					} else {
						dataSvc.getImageMap().put(movieId, result);
					}					
				} else {
					logger.debug("found image from map " + movieId);
				}
				sub.onNext(result);
				sub.onComplete();
			} catch (HttpStatusCodeException e) {
				logger.error("Error processing movie image for the movieid: " + movieId + " with http status code "
						+ e.getStatusCode(), e);
				sub.onError(new UserMoviesException(
						"Error processing movie image for the movieid: " + movieId + ". Please try refreshing ",
						Error.BAD_MOVIE, e.getStatusCode()));
			} catch (RestClientException e) {
				logger.error(
						"Unable to reach movie title service at " + Constants.MOVIE_TITLE_URL + e.getLocalizedMessage(),
						e);
				sub.onError(new UserMoviesException(
						"Unable to reach movie art service " + Constants.MOVIE_ART_URL + ". Please try refreshing ",
						Error.UNREACHABLE_SERVICE, HttpStatus.REQUEST_TIMEOUT));
			} catch (Exception e) {
				logger.error(
						"Exception thrown while queriying movie image " + e.getLocalizedMessage(),
						e);
				sub.onError(new UserMoviesException("Application Error. Please try refreshing.",
						Error.INTERNAL_ERROR, e));
			}
		}).retry(Constants.NUM_RETRIES);
	}
}
