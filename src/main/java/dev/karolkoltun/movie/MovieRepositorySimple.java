package dev.karolkoltun.movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MovieRepositorySimple implements MovieRepository {

  private final AtomicLong counter = new AtomicLong();

  private static List<Movie> movies;

  {
    movies = new ArrayList<>();
    movies.add(
        new Movie(
            counter.incrementAndGet(), "Django", "Quentin Tarantino", LocalDate.of(2012, 12, 11)));
    movies.add(
        new Movie(
            counter.incrementAndGet(), "Gran Torino", "Clint Eastwood", LocalDate.of(2008, 12, 9)));
    movies.add(
        new Movie(
            counter.incrementAndGet(), "Taxi Driver", "Martin Scorsese", LocalDate.of(1976, 2, 8)));
  }

  public List<Movie> findAll() {
    return movies;
  }

  public Optional<Movie> findById(Long id) {
    return movies.stream().filter(m -> m.getId().equals(id)).findFirst();
  }

  public Movie getById(Long id) throws MovieNotFoundException {
    return findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  public Movie save(Movie movie) {
    movie.setId(counter.incrementAndGet());
    movies.add(movie);
    return movie;
  }

  public void deleteById(Long id) {
    movies.remove(getById(id));
  }

  public Movie update(Long id, Movie newMovie) {
    Movie oldMovie = getById(id);
    int index = movies.indexOf(oldMovie);

    newMovie.setId(id);
    movies.set(index, newMovie);

    return newMovie;
  }
}
