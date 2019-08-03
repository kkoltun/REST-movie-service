package dev.karolkoltun.movie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1") // prefix for api methods
public class MovieController {

  private final MovieRepository movieRepository;

  public MovieController() {
    movieRepository = new MovieRepositorySimple();
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
