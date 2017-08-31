package de.testo.tiny.rest;

import de.testo.tiny.model.Problem;
import de.testo.tiny.model.TinyURLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ControllerAdvice
public class ExceptionsController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleValidationError(final MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();
        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(bindingResult.getFieldError().getDefaultMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleInvalidBodyError(final HttpMessageNotReadableException exception) {

        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(exception.getMessage().split(":")[0])
                .build();
    }

    @ExceptionHandler(TinyURLNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Problem handleNotFoundException(final TinyURLNotFoundException exception) {

        return Problem.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .detail(format("Tiny URL %s does not exist", exception.getNotExistingTinyURL()))
                .build();
    }


}
