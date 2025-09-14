package com.site.url.controller;

import com.site.url.dto.UrlRequisicaoDTO;
import com.site.url.dto.UrlRespostaDTO;
import com.site.url.mapper.UrlMapper;
import com.site.url.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlMapper mapper;

    @PostMapping("/shorten-url")
    @Operation(description = "Endpoint responsável por receber url para ser encurtada")
    @ApiResponse(responseCode = "200", description = "Encurtamento bem sucedido")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UrlRespostaDTO> urlcurta(@RequestBody @Valid UrlRequisicaoDTO request, HttpServletRequest servletRequest){
        log.info("Solicitação para encurtar url recebida");
        String urlRedirecionada = servletRequest.getRequestURL().toString().replace("shorten-url", urlService.urlCurtaMontagem(request).urlCurta());
        return ResponseEntity.ok( mapper.paraResposta(urlRedirecionada));
    }

    @GetMapping ("/{urlcurta}")
    @Operation(description = "Endpoint responsável por retornar url original, (Realize a chamada atraves do navegador)")
    @ApiResponse (responseCode = "302", description = "URL encontrada e válida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse (responseCode = "404", description = "URL não encontrada ou expirada")
    public ResponseEntity<Void> getUrlEncurtada(@PathVariable String urlcurta){
        log.info("solicitação para retornar url original recebida");
        return urlService.getUrlOriginal(urlcurta);
    }

}
