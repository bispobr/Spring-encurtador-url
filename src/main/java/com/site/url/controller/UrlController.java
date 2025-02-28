package com.site.url.controller;

import com.site.url.model.Url;
import com.site.url.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten-url")
    public ResponseEntity<Map<String,String>> urlcurta(@RequestBody Map <String,String> request){
        String UrlLonga =  request.get("url");
        String urlencurtada =  urlService.urlCurta(UrlLonga);
        Map<String,String> resposta =  new HashMap<String,String>();
        resposta.put("url","https://xxx.com/"+urlencurtada);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping ("/{urlcurta}")
    public ResponseEntity<Object> getUrlEncurtada(@PathVariable String urlcurta){
        Optional<Url> urlOptional = urlService.getUrlOriginal(urlcurta);

        if (urlOptional.isPresent()){
            Url url = urlOptional.get();
            System.out.println("Redirecionando para" +url.getUrlLonga());
            return ResponseEntity.status(200).location(URI.create(url.getUrlLonga())).build();

        }
        System.out.println("URL expirada ou não encontrada " + urlcurta);
        return ResponseEntity.notFound().build();
    }

}
