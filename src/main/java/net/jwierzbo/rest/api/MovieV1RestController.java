package net.jwierzbo.rest.api;

import net.jwierzbo.rest.exception.MovieNotFoundException;
import net.jwierzbo.rest.model.Movie;
import net.jwierzbo.rest.repository.MovieRepository;
import net.jwierzbo.rest.repository.SimpleMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1") // prefix for api methods
public class MovieV1RestController {

  private final MovieRepository movieRepository;

  public MovieV1RestController() {
    movieRepository = new SimpleMovieRepository();
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(MovieNotFoundException.class)
  public String handleNotFoundMovie(Exception ex) {
    return "LOCAL-class handler: " + ex.getMessage();
  }

  @RequestMapping(value = "/movies", method = RequestMethod.GET)
  public Iterable<Movie> getMovies() {
    return movieRepository.findAll();
  }

  @ResponseBody // is redundant: @RestController adds it automatically
  @GetMapping("/movies/{id}") // shortcut for @RequestMapping
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  // Example of use generic ResponseEntity instead of @ResponseStatus and @ResponseBody
  @PostMapping(value = "/movies")
  public ResponseEntity createMovie(@RequestBody Movie movie) {
    movieRepository.save(movie);
    return new ResponseEntity<>(movie, HttpStatus.CREATED);
  }

  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@PathVariable Long id) {
    movieRepository.deleteById(id);
  }

  @PutMapping("/movies/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    movie.setId(id);
    return movieRepository.save(movie);
  }
}
