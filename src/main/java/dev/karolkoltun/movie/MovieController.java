package dev.karolkoltun.movie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 * This is example of Validation flow in RestController
 */

@RestController
@RequestMapping("/")
public class MovieController {

  private MovieRepository movieRepository;

  MovieController() {
    movieRepository = new MovieRepositorySimple();
  }

  @GetMapping("/movies")
  public Iterable<Movie> getMovies() {
    return movieRepository.findAll();
  }

  @GetMapping("/movies/{id}")
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @PostMapping(value = "/movies")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Movie createMovie(@Valid @RequestBody Movie movie) { // Validate input
    return movieRepository.save(movie);
  }

  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@Valid @PathVariable Long id) { // Validate input
    movieRepository.deleteById(id);
  }

  @PutMapping("/movies/{id}")
  public Movie replaceMovie(@PathVariable Long id, @RequestBody Movie movie) {
    return movieRepository.update(id, movie);
  }

  @PatchMapping("/movies/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie partialMovie) {
    Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));

    if (partialMovie.getDirector() != null) {
      movie.setDirector(partialMovie.getDirector());
    }
    if (partialMovie.getReleaseDate() != null) {
      movie.setReleaseDate(partialMovie.getReleaseDate());
    }
    if (partialMovie.getTitle() != null) {
      movie.setTitle(partialMovie.getTitle());
    }

    return movieRepository.update(id, movie);
  }
}
