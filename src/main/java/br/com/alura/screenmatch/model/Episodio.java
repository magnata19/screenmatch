package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String tituloDoEpisodio;
    private Integer numeroDoEpisodio;
    private Double avaliacaoDoEpisodio;
    private LocalDate dataDeLancamentoDoEpisodio;

    public Episodio(Integer integer, DadosEpisodio dadosEpisodio) {
        this.temporada = integer;
        this.tituloDoEpisodio = dadosEpisodio.tituloDoEpisodio();
        this.numeroDoEpisodio = dadosEpisodio.numeroDoEpisodio();
        try {
            this.avaliacaoDoEpisodio = Double.valueOf(dadosEpisodio.avaliacaoDoEpisodio());
        } catch (NumberFormatException e) {
           this.avaliacaoDoEpisodio = 0.0;
        }
        try {
            this.dataDeLancamentoDoEpisodio = LocalDate.parse(dadosEpisodio.dataDeLancamentoDoEpisodio());
        } catch (DateTimeParseException e) {
            this.dataDeLancamentoDoEpisodio = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTituloDoEpisodio() {
        return tituloDoEpisodio;
    }

    public void setTituloDoEpisodio(String tituloDoEpisodio) {
        this.tituloDoEpisodio = tituloDoEpisodio;
    }

    public Integer getNumeroDoEpisodio() {
        return numeroDoEpisodio;
    }

    public void setNumeroDoEpisodio(Integer numeroDoEpisodio) {
        this.numeroDoEpisodio = numeroDoEpisodio;
    }

    public Double getAvaliacaoDoEpisodio() {
        return avaliacaoDoEpisodio;
    }

    public void setAvaliacaoDoEpisodio(Double avaliacaoDoEpisodio) {
        this.avaliacaoDoEpisodio = avaliacaoDoEpisodio;
    }

    public LocalDate getDataDeLancamentoDoEpisodio() {
        return dataDeLancamentoDoEpisodio;
    }

    public void setDataDeLancamentoDoEpisodio(LocalDate dataDeLancamentoDoEpisodio) {
        this.dataDeLancamentoDoEpisodio = dataDeLancamentoDoEpisodio;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", tituloDoEpisodio='" + tituloDoEpisodio + '\'' +
                ", numeroDoEpisodio=" + numeroDoEpisodio +
                ", avaliacaoDoEpisodio=" + avaliacaoDoEpisodio +
                ", dataDeLancamentoDoEpisodio=" + dataDeLancamentoDoEpisodio;
    }
}
