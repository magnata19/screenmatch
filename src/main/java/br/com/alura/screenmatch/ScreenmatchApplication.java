package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		String json = consumoAPI.obterDados("https://www.omdbapi.com/?t=supernatural&apikey=d151eef9");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();

		//dados da serie
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);


		//dados do episodio em especifico da serie
		String jsonEpisode = consumoAPI.obterDados("https://www.omdbapi.com/?t=supernatural&Season=1&episode=2&apikey=d151eef9");
		DadosEpisodio dadosEpisodio = conversor.obterDados(jsonEpisode, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		//dados da temporada em especifico
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			String jsonTemporada = consumoAPI
					.obterDados("https://www.omdbapi.com/?t=supernatural&Season=%d&apikey=d151eef9".formatted(i));
			DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporada, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
