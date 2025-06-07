package com.site.url.controller;

import com.site.url.model.Url;
import com.site.url.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten-url")
    @Operation(description = "Endpoint responsável por receber url para ser encurtada")
    @ApiResponse(responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Map<String,String>> urlcurta(@RequestBody Map <String,String> request){
        log.info("Solicitação para encurtar url recebida");
        String UrlLonga =  request.get("url");
        String urlencurtada =  urlService.urlCurtaMontagem(UrlLonga);
        Map<String,String> resposta =  new HashMap<String,String>();
        resposta.put("url","https://xxx.com/"+urlencurtada);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping ("/{urlcurta}")
    @Operation(description = "Endpoint responsável por retornar url original")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse (responseCode = "404", description = "Url não encontrada")
    public ResponseEntity<Object> getUrlEncurtada(@PathVariable String urlcurta){
        log.info("solicitação para retornar url original recebida");
        Optional<Url> urlOptional = urlService.getUrlOriginal(urlcurta);

        if (urlOptional.isPresent()){
            Url url = urlOptional.get();
            System.out.println("Redirecionando para" +url.getUrlLonga());
            return ResponseEntity.status(200).location(URI.create(url.getUrlLonga())).build();

        }
        System.out.println("URL expirada ou não encontrada " + urlcurta);
        log.info("url solicitada expirada ou não encontrada");
        return ResponseEntity.notFound().build();
    }

}
