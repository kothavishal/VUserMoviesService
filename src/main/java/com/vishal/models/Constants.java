package com.vishal.models;

public class Constants {

	public static final String MOVIE_ART_URL = "http://localhost:7003";
	public static final String MOVIE_LIST_URL = "http://localhost:7001";
	public static final String MOVIE_TITLE_URL = "http://localhost:7002";

	public static final Integer THREAD_POOL_SIZE = 200;
	
	public static final Integer USER_MOVIES_CACHE_EXPIRY_TIME_IN_HOURS = 24;
	public static final Integer MOVIE_TITLE_CACHE_EXPIRY_TIME_IN_HOURS = 24;
	public static final Integer MOVIE_IMAGE_CACHE_EXPIRY_TIME_IN_HOURS = 24;	
		
	public static final Integer INITIAL_NUM_MOVIE_IMAGE_CACHED = 1000;
	public static final Integer MAX_NUM_MOVIE_IMAGE_CACHED = 10000;
	
	public static final Integer INITIAL_NUM_MOVIE_TITLE_CACHED = 1000;		
	public static final Integer MAX_NUM_MOVIE_TITLE_CACHED = 10000;

	public static final Integer NUM_RETRIES = 3;
}
