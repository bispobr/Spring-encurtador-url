package com.site.url.services;

import com.site.url.dto.UrlRequisicaoDTO;
import com.site.url.dto.UrlRespostaDTO;
import com.site.url.mapper.UrlMapper;
import com.site.url.model.Url;
import com.site.url.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
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

    @Autowired
    private UrlMapper mapper;

    public UrlRespostaDTO urlCurtaMontagem(UrlRequisicaoDTO requisicao){

        Url url = geradorUrlcurta(mapper.paraUrl(requisicao));
        urlRepository.save(url);
        log.info("Url encurtada Salva na Base de Dados");
        return mapper.paraResposta(url);
    }


    private Url geradorUrlcurta(Url url){
        String BASE_URL = "https://aaa.com/";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(url.getUrlLonga().getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            url.setUrlCurta(encoded.substring(0, 8));
            url.setDataExpiracao(LocalDateTime.now().plusDays(10));
            log.info("url encurtada gerada");
            return url;
        } catch (NoSuchAlgorithmException e) {
            log.error("Erro ao gerar URL encurtada");
            throw new RuntimeException("Erro ao gerar URL encurtada", e);
        }
    }

    public ResponseEntity<Void> getUrlOriginal(String urlcurta){

        Optional<Url> urlOptional = urlRepository.findByUrlCurta(urlcurta);
        if (urlOptional.isPresent()){
            Url url = urlOptional.get();
            if (url.getDataExpiracao().isAfter(LocalDateTime.now())){
                log.info("Url original retornada");
                HttpHeaders headers =  new HttpHeaders();
                headers.setLocation(URI.create(url.getUrlLonga()));
                return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
            } else {
                log.info("Url expirada apagada");
                urlRepository.delete(url);
            }
        }

        log.info("url vazia retornada");
        return  ResponseEntity.notFound().build();
    }
}

