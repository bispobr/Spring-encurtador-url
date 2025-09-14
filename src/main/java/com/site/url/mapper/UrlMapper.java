package com.site.url.mapper;

import com.site.url.dto.UrlRequisicaoDTO;
import com.site.url.dto.UrlRespostaDTO;
import com.site.url.model.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public Url paraUrl(UrlRequisicaoDTO requisicao){
        Url url =  new Url();
        url.setUrlLonga(requisicao.urlLonga());
        return url;
    }

    public UrlRespostaDTO paraResposta(String urlRedirecionanada){
        return new UrlRespostaDTO(urlRedirecionanada);
    }

    public UrlRespostaDTO paraResposta (Url url){
        return new UrlRespostaDTO(url.getUrlCurta());
    }
}
