package dev.karolkoltun.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.karolkoltun.movie.validation.Name;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "Movie")
class Movie {

  @ApiModelProperty(notes = "The database generated product ID")
  private Long id;

  // example of default Bean validation
  @NotNull(message = "title can not be null")
  private String title;

  // example of custom Bean validation
  @Name(message = "Invalid director name - it has to start with uppercase!")
  private String director;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(notes = "Movie premiere date", example = "2018-02-28")
  private LocalDate releaseDate;

  Movie(long id, String title, String director, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.director = director;
    this.releaseDate = releaseDate;
  }

  Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  String getTitle() {
    return title;
  }

  void setTitle(String title) {
    this.title = title;
  }

  String getDirector() {
    return director;
  }

  void setDirector(String director) {
    this.director = director;
  }

  LocalDate getReleaseDate() {
    return releaseDate;
  }

  void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }
}
