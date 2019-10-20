package com.ssa.SpotifyMusicSearchApplication.controller;

import com.ssa.SpotifyMusicSearchApplication.dto.FieldErrorDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UsernameIsAlreadyTakenException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.mapper.FieldErrorMapper;
import com.ssa.SpotifyMusicSearchApplication.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
public abstract class AbstractController {

    private final FieldErrorMapper fieldErrorMapper = new FieldErrorMapper();

    private static final String FORBIDDEN = "This operation is forbidden. You have no access to this resource.";

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.trace(ex.getMessage(), ex);
        return new ErrorResponse(FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
        log.trace(ex.getMessage(), ex);
        return new ErrorResponse(FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleRecordValidationException(ValidationException ex) {
        log.trace(ex.getMessage(), ex);
        return ex.getErrorResponse();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handleBindValidationException(BindException ex) {
        log.trace(ex.getMessage(), ex);
        return new ErrorResponse(ex.getFieldError().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    public ErrorResponse handleUsernameIsAlreadyTakenException(UsernameIsAlreadyTakenException ex) {
        log.trace(ex.getMessage(), ex);
        return ex.getErrorResponse();
    }

    public void validateFieldErrors(Errors errors) throws ValidationException {
        if (errors.hasErrors()) {
            List<FieldErrorDTO> fieldsErrors = fieldErrorMapper.map(errors.getFieldErrors());
            throw new ValidationException(new ErrorResponse(fieldsErrors));
        }
    }

}