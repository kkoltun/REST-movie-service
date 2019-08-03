package dev.karolkoltun.movie.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DirectorNameValidator implements ConstraintValidator<DirectorName, String> {
  public void initialize(DirectorName constraintAnnotation) {}

  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && !value.isEmpty() && Character.isUpperCase(value.charAt(0));
  }
}
