package com.vishal.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.vishal.models.Movie;

import io.reactivex.Observable;

public class UserMoviesServiceTest {

	private static final Integer TEST_USER_ID = 12345;

	private static final Integer TEST_MOVIE1_ID = 793;
	private static final String TEST_MOVIE1_IMAGE = "Picture Perfect";
	private static final String TEST_MOVIE1_TITLE = "Picture_Perfect_17f6812.jpg";

	private UserMoviesService userMoviesSvc;

	@Mock
	private MovieArtService movieArtSvc;

	@Mock
	private MovieTitleService movieTitleSvc;

	@Mock
	private MovieListService movieListSvc;	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userMoviesSvc = new UserMoviesService();
		userMoviesSvc.setMovieArtService(movieArtSvc);
		userMoviesSvc.setMovieTitleService(movieTitleSvc);
		userMoviesSvc.setMovieListService(movieListSvc);			

		Mockito.when(movieListSvc.userMovieIds(TEST_USER_ID))
				.thenReturn(Observable.fromArray(new Integer[] { TEST_MOVIE1_ID }));

		Mockito.when(movieTitleSvc.movieTitle(TEST_MOVIE1_ID)).thenReturn(Observable.just(TEST_MOVIE1_TITLE));
		Mockito.when(movieArtSvc.movieImage(TEST_MOVIE1_ID)).thenReturn(Observable.just(TEST_MOVIE1_IMAGE));

	}

	@Test
	public void testUserMovies() {
		Observable<Movie> moviesObservable = userMoviesSvc.userMovies(TEST_USER_ID);

		Movie m1 = moviesObservable.blockingFirst();
		Assert.assertNotNull(m1);
		Assert.assertEquals(m1.getImage(), TEST_MOVIE1_IMAGE);
		Assert.assertEquals(m1.getTitle(), TEST_MOVIE1_TITLE);
		Assert.assertEquals(m1.getMovieId(), TEST_MOVIE1_ID);

	}

	@After
	public void tearDown() {
	}
}
