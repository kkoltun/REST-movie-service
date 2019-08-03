package dev.karolkoltun.movie;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
 * This is example Controller class with extended description for SwaggerApi
 *  These @Api*** annotations are not necessary if we use @ResponseStatus
 *  and @ResponseBody objects instead of generic response
 */

@Api(value = "MovieController")
@RestController
@RequestMapping("/swagger")
public class MovieControllerSwagger {

  private final MovieRepository movieRepository;

  public MovieControllerSwagger() {
    movieRepository = new MovieRepositorySimple();
  }

  @ApiOperation(value = "View a list of Movies", response = Movie.class, responseContainer = "List")
  @GetMapping("/movies")
  public Iterable<Movie> getMovies() {
    return movieRepository.findAll();
  }

  @ApiOperation(value = "Search a Movie by ID", response = Movie.class)
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
  @GetMapping("/movies/{id}")
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @ApiOperation(value = "Add new Movie", response = Movie.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Not Found")
      })
  @ApiImplicitParams({
    // This can be write also as inline @ApiParam - example below in "deleteMovie"
    @ApiImplicitParam(
        name = "movie",
        value = "Movie object to add",
        required = true,
        dataType = "Movie",
        paramType = "body")
  })
  @PostMapping(value = "/movies")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Movie createMovie(@RequestBody Movie movie) {
    return movieRepository.save(movie);
  }

  @ApiOperation(value = "Delete specific movie")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@ApiParam(value = "Movie ID", required = true) @PathVariable Long id) {
    movieRepository.deleteById(id);
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Not Found")
      })
  @PutMapping("/movies/{id}")
  public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
    movie.setId(id);
    return movieRepository.save(movie);
  }
}
