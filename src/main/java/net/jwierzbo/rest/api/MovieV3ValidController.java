package net.jwierzbo.rest.api;

import net.jwierzbo.rest.repository.MovieRepository;
import net.jwierzbo.rest.repository.SimpleMovieRepository;
import net.jwierzbo.rest.exception.MovieNotFoundException;
import net.jwierzbo.rest.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * This is example of Validation flow in RestController
 */

@RestController
@RequestMapping("/v3")
public class MovieV3ValidController {

  private MovieRepository movieRepository;

  public MovieV3ValidController() {
    movieRepository = new SimpleMovieRepository();
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
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    movie.setId(id);
    return movieRepository.save(movie);
  }
}
