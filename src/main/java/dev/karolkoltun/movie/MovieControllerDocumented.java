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
@RequestMapping("/documented")
class MovieControllerDocumented {

  private final MovieRepository movieRepository;

  MovieControllerDocumented() {
    movieRepository = new MovieRepositorySimple();
  }

  @ApiOperation(value = "Get all movies", response = Movie.class, responseContainer = "List")
  @GetMapping("/movies")
  public Iterable<Movie> getMovies() {
    return movieRepository.findAll();
  }

  @ApiOperation(value = "Get a movie by ID", response = Movie.class)
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
  @GetMapping("/movies/{id}")
  public Movie getMovie(@PathVariable("id") Long id) {
    return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @ApiOperation(value = "Add a new movie", response = Movie.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Bad Request"),
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

  @ApiOperation(value = "Delete a movie by ID")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
  @DeleteMapping("/movies/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteMovie(@ApiParam(value = "Movie ID", required = true) @PathVariable Long id) {
    movieRepository.deleteById(id);
  }

  @ApiOperation(value = "Replace a movie under an ID")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")
      })
  @PutMapping("/movies/{id}")
  public Movie replaceMovie(@PathVariable Long id, @RequestBody Movie movie) {
    movie.setId(id);
    return movieRepository.save(movie);
  }

  @ApiOperation(value = "Edit one of the properties of a movie")
  @ApiResponses(
      value = {
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 404, message = "Not Found")
      })
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
