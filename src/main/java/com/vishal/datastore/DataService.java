package com.vishal.datastore;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.vishal.models.Constants;

@Service
public class DataService {

	private Cache<Integer, String> titleMap = Caffeine.newBuilder().maximumSize(Constants.MAX_NUM_MOVIE_IMAGE_CACHED)
			.expireAfterWrite(Constants.MOVIE_TITLE_CACHE_EXPIRY_TIME_IN_HOURS, TimeUnit.HOURS)
			.initialCapacity(Constants.INITIAL_NUM_MOVIE_TITLE_CACHED).build();

	private Cache<Integer, String> imageMap = Caffeine.newBuilder().maximumSize(Constants.MAX_NUM_MOVIE_IMAGE_CACHED)
			.initialCapacity(Constants.INITIAL_NUM_MOVIE_IMAGE_CACHED)
			.expireAfterWrite(Constants.MOVIE_IMAGE_CACHE_EXPIRY_TIME_IN_HOURS, TimeUnit.HOURS).build();
	
	public Cache<Integer, String> getTitleMap() {
		return titleMap;
	}

	public void setTitleMap(Cache<Integer, String> titleMap) {
		this.titleMap = titleMap;
	}

	public Cache<Integer, String> getImageMap() {
		return imageMap;
	}

	public void setImageMap(Cache<Integer, String> imageMap) {
		this.imageMap = imageMap;
	}
}
