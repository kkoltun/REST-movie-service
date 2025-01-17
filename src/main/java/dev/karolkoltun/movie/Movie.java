package dev.karolkoltun.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.karolkoltun.movie.validation.DirectorName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "Movie")
class Movie {

  @ApiModelProperty(notes = "The database generated product ID")
  private Long id;

  // example of default Bean validation
  @NotNull(message = "Title should not be null")
  private String title;

  // example of custom Bean validation
  @DirectorName(message = "Director should not be null and should be uppercase!")
  private String director;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(notes = "Movie premiere date", example = "2018-02-28")
  @NotNull(message = "Movie premiere date should not be null")
  private LocalDate releaseDate;

  Movie(long id, String title, String director, LocalDate releaseDate) {
    this.id = id;
    this.title = title;
    this.director = director;
    this.releaseDate = releaseDate;
  }

  public Movie() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }
}
