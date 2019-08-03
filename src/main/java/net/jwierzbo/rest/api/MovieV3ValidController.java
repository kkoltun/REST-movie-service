package net.jwierzbo.rest.api;

import net.jwierzbo.rest.dao.MovieDAO;
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

  @Autowired private MovieDAO movieDAO;

  @GetMapping("/movies")
  public List<Movie> getMovies() {
    return movieDAO.list();
  }

  @GetMapping("/movies/{id}")
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieDAO.get(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @PostMapping(value = "/movies")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Movie createMovie(@Valid @RequestBody Movie movie) { // Validate input
    return movieDAO.create(movie);
  }

  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@Valid @PathVariable Long id) { // Validate input
    movieDAO.delete(id);
  }

  @PutMapping("/movies/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    return movieDAO.update(id, movie);
  }
}
