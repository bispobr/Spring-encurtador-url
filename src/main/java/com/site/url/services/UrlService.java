package com.site.url.services;

import com.site.url.model.Url;
import com.site.url.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String urlCurtaMontagem(String urlLonga){
        String urlencurtada = geradorUrlcurta(urlLonga);
        Url url = new Url();
        url.setUrlLonga(urlLonga);
        url.setUrlCurta(urlencurtada);
        url.setDataExpiracao(LocalDateTime.now().plusDays(10));
        urlRepository.save(url);
        log.info("Montagem url concluida");
        return urlencurtada;
    }


    private String geradorUrlcurta(String urlLonga){
        String BASE_URL = "https:/aaa.com/";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(urlLonga.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            log.info("url encurtada retornada");
            return encoded.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            log.error("Erro ao gerar URL encurtada");
            throw new RuntimeException("Erro ao gerar URL encurtada", e);
        }
    }

    public Optional<Url>getUrlOriginal(String urlcurta){

        Optional<Url> urlOptional = urlRepository.findByUrlCurta(urlcurta);
        if (urlOptional.isPresent()){
            Url url = urlOptional.get();
            if (url.getDataExpiracao().isAfter(LocalDateTime.now())){
                log.info("Url original retornada");
                return Optional.of(url);
            } else {
                log.info("Url expirada apagada");
                urlRepository.delete(url);
            }
        }
        log.info("url vazia retornada");
        return Optional.empty();
    }
}
