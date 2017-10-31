package com.vishal.models;

public class Movie {

	private String title;
	private String image;
	private Integer movieId;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Movie(String title, String image, Integer movieId) {
		this.title = title;
		this.image = image;
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
