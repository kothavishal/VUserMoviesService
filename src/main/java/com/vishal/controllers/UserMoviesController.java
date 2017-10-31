package com.vishal.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.vishal.models.Movie;
import com.vishal.services.UserMoviesService;

/**
 * Controller that exposes REST APIs to query movies for a given user
 */
@RestController
public class UserMoviesController {

	Logger logger = Logger.getLogger(UserMoviesController.class);

	@Autowired
	private UserMoviesService userMoviesSvc;

	@RequestMapping("/users/{userId}/movies")
	public DeferredResult<List<Movie>> getUserMovies(@PathVariable Integer userId) {
		DeferredResult<List<Movie>> result = new DeferredResult<>();

		userMoviesSvc.userMovies(userId).toList().subscribe(movies -> result.setResult(movies),
				e -> result.setErrorResult(e));
		return result;
	}
}
