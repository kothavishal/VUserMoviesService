package com.vishal.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vishal.error.Error;
import com.vishal.error.UserMoviesException;
import com.vishal.models.Constants;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Service which handles querying list of movie ids corresponding to a user
 */
@Service
public class MovieListService {

	final static Logger logger = Logger.getLogger(MovieListService.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * method to return list of movie ids for given user id
	 */
	public Observable<Integer> userMovieIds(Integer userId) {

		if (userId == null) {
			return Observable.error(new UserMoviesException("Invalid User Id " + userId, Error.BAD_INPUT));
		}

		return Observable.<Integer[]> create(obsEmitter -> {
			try {
				Integer[] result = restTemplate.getForObject(Constants.MOVIE_LIST_URL + "/{userId}", Integer[].class,
						userId);
				
				if (result == null) {
					obsEmitter.onError(new UserMoviesException("Movies list not found for user id " + userId,
							Error.EXTERNAL_SERVICE));
				}				
				obsEmitter.onNext(result);
				obsEmitter.onComplete();
			} catch (HttpStatusCodeException e) {
				logger.error("Error processing movies list for the userid: " + userId + " with http status code "
						+ e.getStatusCode(), e);
				obsEmitter.onError(new UserMoviesException(
						"Error fetching movies list for the userid: " + userId + ". Please try refreshing ",
						Error.BAD_MOVIE, e.getStatusCode()));
			} catch (RestClientException e) {
				logger.error(
						"Unable to reach movie list service at " + Constants.MOVIE_LIST_URL + e.getLocalizedMessage(),
						e);
				obsEmitter
						.onError(new UserMoviesException(
								"Unable to reach movie list service at " + Constants.MOVIE_LIST_URL
										+ ". Please try refreshing ",
								Error.UNREACHABLE_SERVICE, HttpStatus.REQUEST_TIMEOUT));
			} catch (Exception e) {
				logger.error("Exception thrown while queriying movies list for the user " + userId + " with message "
						+ e.getLocalizedMessage(), e);
				obsEmitter.onError(
						new UserMoviesException("Application Error. Please try refreshing.", Error.INTERNAL_ERROR, e));
			}
		}).flatMap(re -> {
			return Observable.fromArray(re);
		}).retry(Constants.NUM_RETRIES);
	}

	/**
	 * method to return list of movie ids for given user id
	 */
	public Observable<Integer> userMovieIdsOld(Integer userId) {

		if (userId == null) {
			return Observable.error(new UserMoviesException("Invalid User Id " + userId, Error.BAD_INPUT));
		}

		Integer[] result = new Integer[] {};
		try {
			result = restTemplate.getForObject(Constants.MOVIE_LIST_URL + "/{userId}", Integer[].class, userId);
			if (result == null) {
				return Observable.error(
						new UserMoviesException("Movie List not found for user id " + userId, Error.EXTERNAL_SERVICE));
			}
		} catch (HttpStatusCodeException e) {
			return Observable.error(new UserMoviesException(
					"Error fetching movie list : " + e.getResponseBodyAsString() + ". Please try refreshing ",
					Error.BAD_USER, e.getStatusCode()));
		} catch (RestClientException e) {
			return Observable.error(new UserMoviesException(
					"Unable to reach movie list service at " + Constants.MOVIE_LIST_URL + ". Please try refreshing ",
					Error.UNREACHABLE_SERVICE, HttpStatus.REQUEST_TIMEOUT));
		} catch (Exception e) {
			return Observable.error(
					new UserMoviesException("Application Error. Please try refreshing.", Error.INTERNAL_ERROR, e));
		}

		return Observable.fromArray(result);

	}
}
