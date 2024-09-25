package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PedidoImportacaoErrorValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PedidoImportacaoErrorValid {
    String message() default "Campos inválidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
