package dev.karolkoltun.movie;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionController {

  @ResponseStatus(value = NOT_FOUND)
  @ExceptionHandler(MovieNotFoundException.class)
  @ResponseBody
  public String handleMovieNotFoundException(MovieNotFoundException exception) {
    return "Exception happened: " + exception.getMessage();
  }

  @ResponseStatus(value = BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public String handleMethodArgumentException(MethodArgumentNotValidException exception) {
    List<String> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    return String.join(",", errors);
  }

  @ResponseStatus(value = INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String handleUnknownException(Exception exception) {
    return "Unknown error happened: " + exception.getMessage();
  }
}
