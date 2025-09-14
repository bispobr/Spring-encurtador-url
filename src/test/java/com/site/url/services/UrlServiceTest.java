package com.site.url.services;

import com.site.url.dto.UrlRequisicaoDTO;
import com.site.url.dto.UrlRespostaDTO;
import com.site.url.mapper.UrlMapper;
import com.site.url.model.Url;
import com.site.url.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlMapper mapper;

    @Autowired
    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void urlCurtaMontagem_DeveRetornarUrlRespostaDTO_QuandoSucesso() {
        UrlRequisicaoDTO requisicaoDTO = new UrlRequisicaoDTO("https://meusite.com/page");
        Url entidade = new Url();
        entidade.setUrlLonga(requisicaoDTO.urlLonga());

        UrlRespostaDTO respostaEsperada = new UrlRespostaDTO("https://aaa.com/abc12345");

        when(mapper.paraUrl(requisicaoDTO)).thenReturn(entidade);
        when(mapper.paraResposta(any(Url.class))).thenReturn(respostaEsperada);

        UrlRespostaDTO resposta = urlService.urlCurtaMontagem(requisicaoDTO);

        assertNotNull(resposta);
        assertEquals("https://aaa.com/abc12345", resposta.urlCurta());

        verify(urlRepository).save(any(Url.class));
        verify(mapper).paraResposta(any(Url.class));
    }

    @Test
    void getUrlOriginal_DeveRetornarFound_QuandoUrlExisteENaoExpirou() {
        String curta = "abc12345";
        Url url = new Url();
        url.setUrlCurta(curta);
        url.setUrlLonga("https://meusite.com/page");
        url.setDataExpiracao(LocalDateTime.now().plusDays(5));

        when(urlRepository.findByUrlCurta(curta)).thenReturn(Optional.of(url));

        ResponseEntity<Void> response = urlService.getUrlOriginal(curta);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(URI.create(url.getUrlLonga()), response.getHeaders().getLocation());
    }

    @Test
    void getUrlOriginal_DeveRetornarNotFound_QuandoUrlNaoExiste() {
        when(urlRepository.findByUrlCurta("naoencontrada")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = urlService.getUrlOriginal("naoencontrada");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getUrlOriginal_DeveRetornarNotFound_QuandoUrlExpirada() {
        String curta = "expirada";
        Url url = new Url();
        url.setUrlCurta(curta);
        url.setUrlLonga("https://expirada.com");
        url.setDataExpiracao(LocalDateTime.now().minusDays(1));

        when(urlRepository.findByUrlCurta(curta)).thenReturn(Optional.of(url));

        ResponseEntity<Void> response = urlService.getUrlOriginal(curta);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(urlRepository).delete(url);
    }

}