package dev.karolkoltun.movie.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DirectorNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DirectorName {

  String message() default "{Name}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
