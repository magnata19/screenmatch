package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
        @JsonAlias("Title")
        String tituloDoEpisodio,
        @JsonAlias("Episode")
        Integer numeroDoEpisodio,
        @JsonAlias("imdbRating")
        String avaliacaoDoEpisodio,
        @JsonAlias("Released")
        String dataDeLancamentoDoEpisodio
) {
}
