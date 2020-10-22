package com.andremartds.despesas_pessoais.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MimaneyExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    final String mensagemDesenvolvedor = ex.getCause().toString();
    final String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
    List<Error> error = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
    return handleExceptionInternal(ex, error, headers, status, request);
  }

  @ExceptionHandler({ EmptyResultDataAccessException.class })
  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
      final WebRequest request) {

    final String mensagemDesenvolvedor = ex.toString();
    final String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,
        LocaleContextHolder.getLocale());

    List<Error> error = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));

    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    List<Error> erros = criaListaDeErrors(ex.getBindingResult());
    return handleExceptionInternal(ex, erros, headers, status, request);
  }

  private List<Error> criaListaDeErrors(BindingResult bindingResult) {
    List<Error> erros = new ArrayList<>();

    for (FieldError error : bindingResult.getFieldErrors()) {
      String mensagemUsuario = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      String mensagemDesenvolvedor = error.toString();
      erros.add(new Error(mensagemUsuario, mensagemDesenvolvedor));
    }
    return erros;
  }

  public class Error {
    private String mensagemUsuario;
    private String mensagemDesenvolvedor;

    public Error(final String mensagemUsuario, final String mensagemDesenvolvedor) {
      this.mensagemUsuario = mensagemUsuario;
      this.mensagemDesenvolvedor = mensagemDesenvolvedor;
    }

    public String getMensagemUsuario() {
      return mensagemUsuario;
    }

    public String getMensagemDesenvolvedor() {
      return mensagemDesenvolvedor;
    }

  }

}
