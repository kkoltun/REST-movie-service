package dev.karolkoltun.movie;

import java.util.Optional;

public interface MovieRepository {
  Movie save(Movie movie);

  void deleteById(Long id);

  Iterable<Movie> findAll();

  Optional<Movie> findById(Long id);
}
