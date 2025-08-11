package de.testo.tiny.rest;

import de.testo.tiny.model.Problem;
import de.testo.tiny.model.url.TinyURLNotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Optional;

import static java.lang.String.format;

@ControllerAdvice
public class ExceptionsController {


    public static final String DEFAULT_URL_INVALID_MESSAGE = "The introduced string is not a valid URL";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleValidationError_1(final MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();
        var urlNotValidDetail = Optional.ofNullable(bindingResult.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(DEFAULT_URL_INVALID_MESSAGE);
        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(
                        urlNotValidDetail
                ).build();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleValidationError_2(final MethodValidationResult restCallNotValidResult) {

        var urlNotValidDetail = Optional.of(restCallNotValidResult)
                .map(MethodValidationResult::getParameterValidationResults)
                .map(list -> list.get(0))
                .map(ParameterValidationResult::getResolvableErrors)
                .map(list -> list.get(0))
                .map(MessageSourceResolvable::getDefaultMessage)
                .orElse(DEFAULT_URL_INVALID_MESSAGE);

        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(urlNotValidDetail)
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
