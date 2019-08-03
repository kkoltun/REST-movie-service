package net.jwierzbo.rest.api;

import net.jwierzbo.rest.dao.MovieDAO;
import net.jwierzbo.rest.exception.MovieNotFoundException;
import net.jwierzbo.rest.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1") // prefix for api methods
public class MovieV1RestController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(MovieNotFoundException.class)
  public String handleNotFoundMovie(Exception ex) {
    return "LOCAL-class handler: " + ex.getMessage();
  }

  @Autowired private MovieDAO movieDAO;

  @RequestMapping(value = "/movies", method = RequestMethod.GET)
  public List<Movie> getMovies() {
    return movieDAO.list();
  }

  @ResponseBody // is redundant: @RestController adds it automatically
  @GetMapping("/movies/{id}") // shortcut for @RequestMapping
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieDAO.get(id).get();
  }

  // Example of use generic ResponseEntity instead of @ResponseStatus and @ResponseBody
  @PostMapping(value = "/movies")
  public ResponseEntity createMovie(@RequestBody Movie movie) {
    movieDAO.create(movie);
    return new ResponseEntity<>(movie, HttpStatus.CREATED);
  }

  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@PathVariable Long id) {
    movieDAO.delete(id);
  }

  @PutMapping("/movies/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    return movieDAO.update(id, movie);
  }
}
