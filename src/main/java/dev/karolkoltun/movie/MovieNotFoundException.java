package dev.karolkoltun.movie;

class MovieNotFoundException extends RuntimeException {

  MovieNotFoundException(Long id) {
    super("could not find movie with id: " + id);
  }
}
