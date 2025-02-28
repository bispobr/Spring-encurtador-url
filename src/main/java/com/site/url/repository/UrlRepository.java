package com.site.url.repository;

import com.site.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByUrlCurta(String urlCurta);
}
