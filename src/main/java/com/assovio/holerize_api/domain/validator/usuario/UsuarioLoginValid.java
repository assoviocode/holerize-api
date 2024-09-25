package com.assovio.holerize_api.domain.validator.usuario;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UsuarioLoginValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioLoginValid {
    String message() default "Campos inválidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
