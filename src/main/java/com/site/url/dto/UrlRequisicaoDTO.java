package com.site.url.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UrlRequisicaoDTO(@NotBlank @URL String urlLonga) {
}
