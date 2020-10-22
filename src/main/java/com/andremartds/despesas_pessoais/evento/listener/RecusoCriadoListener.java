package com.andremartds.despesas_pessoais.evento.listener;

import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import com.andremartds.despesas_pessoais.evento.RecursoCriadoEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecusoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

  @Override
  public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
    HttpServletResponse response = recursoCriadoEvent.getResponse();
    Long codigo = recursoCriadoEvent.getCodigo();
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();
    response.setHeader("location", uri.toASCIIString());
  }

}
