package dev.karolkoltun.movie;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SimpleMovieRepository implements MovieRepository {

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
            counter.incrementAndGet(),
            "Taxi Driver",
            "Martin Scorsese",
            LocalDate.of(1976, 2, 8)));
  }

  public List<Movie> findAll() {
    return movies;
  }

  public Optional<Movie> findById(Long id) {
    for (Movie c : movies) {
      if (c.getId().equals(id)) {
        return Optional.of(c);
      }
    }

    throw new MovieNotFoundException(id);
  }

  public Movie save(Movie movie) {
    movie.setId(counter.incrementAndGet());
    movies.add(movie);
    return movie;
  }

  public void deleteById(Long id) {
    for (Movie c : movies) {
      if (c.getId().equals(id)) {
        movies.remove(c);
      }
    }

    throw new MovieNotFoundException(id);
  }

  public Movie update(Long id, Movie movie) {
    for (Movie c : movies) {
      if (c.getId().equals(id)) {
        movie.setId(c.getId());
        movies.remove(c);
        movies.add(movie);
        return movie;
      }
    }

    throw new MovieNotFoundException(id);
  }
}
