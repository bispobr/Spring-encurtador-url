package com.site.url.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.url.dto.UrlRequisicaoDTO;
import com.site.url.dto.UrlRespostaDTO;
import com.site.url.mapper.UrlMapper;
import com.site.url.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.net.URI;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    @Mock
    private UrlService urlService;

    @Mock
    private UrlMapper mapper;

    @InjectMocks
    private UrlController urlController;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    @Test
    void urlcurta_DeveRetornar200_ComUrlResposta() throws Exception {
        UrlRequisicaoDTO requisicao = new UrlRequisicaoDTO("https://meusite.com");
        UrlRespostaDTO respostaInterna = new UrlRespostaDTO("abc12345");
        UrlRespostaDTO respostaFinal = new UrlRespostaDTO("http://localhost/abc12345");

        when(urlService.urlCurtaMontagem(any())).thenReturn(respostaInterna);
        when(mapper.paraResposta("http://localhost/abc12345")).thenReturn(respostaFinal);

        mockMvc.perform(post("/shorten-url")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.urlCurta").value("http://localhost/abc12345"));
    }

    @Test
    void getUrlEncurtada_Encontrada_DeveRetornar302ComLocation() throws Exception {
        String curta = "abc12345";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://meusite.com"));
        ResponseEntity<Void> response = ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

        when(urlService.getUrlOriginal(curta)).thenReturn(response);

        mockMvc.perform(get("/" + curta))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://meusite.com"));
    }

    @Test
    void getUrlEncurtada_NaoEncontrada_DeveRetornar404() throws Exception {
        when(urlService.getUrlOriginal("naoExiste")).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/naoExiste"))
                .andExpect(status().isNotFound());
    }
}

