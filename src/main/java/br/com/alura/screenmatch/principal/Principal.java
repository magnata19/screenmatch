package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d151eef9";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para buscar.");
        String nomeDaSerie = leitura.nextLine();
        String json = consumoAPI.obterDados(ENDERECO + nomeDaSerie.replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        //dados da temporada em especifico
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			String jsonTemporada = consumoAPI
					.obterDados(ENDERECO + nomeDaSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporada, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        for(int i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodio> episodios = temporadas.get(i).episodios();
            for(int j = 0; j < episodios.size(); j++) {
                System.out.println(episodios.get(j).tituloDoEpisodio());
            }
        }

//        metodo lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.tituloDoEpisodio())));
//      top 5 episodios
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();
        System.out.println("Top 10 episódios.");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacaoDoEpisodio().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro N/A " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacaoDoEpisodio).reversed())
//                .peek(e -> System.out.println("ordenando " + e))
//                .limit(10)
//                .peek(e -> System.out.println("limitando em 10 " + e))
//                .map(e -> e.tituloDoEpisodio().toUpperCase())
//                .peek(e -> System.out.println("Mapeando " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroDaTemporada(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

//        System.out.println("Escolha um episódio");
//        String trechoDoEp = leitura.nextLine();
//
//        Optional<Episodio> episodioEncontrado = episodios.stream()
//                .filter(e -> e.getTituloDoEpisodio().toUpperCase().contains(trechoDoEp.toUpperCase()))
//                .findFirst();
//        if(episodioEncontrado.isPresent()) {
//            System.out.println("Ep encontrado! Temporada: " + episodioEncontrado.get().getTemporada() +
//                    "\n Nome do ep: " + episodioEncontrado.get().getTituloDoEpisodio());
//        } else {
//            System.out.println("Ep não encontrado!");
//        }

//
//        System.out.println("A partir de que ano você deseja ver os episódios?");
//        Integer ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataDeLancamentoDoEpisodio() != null && e.getDataDeLancamentoDoEpisodio().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "Episódio: " + e.getTituloDoEpisodio() +
//                                "Data de Lançamento: " + e.getDataDeLancamentoDoEpisodio().format(formatador)
//                ));

            Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                    .filter(e -> e.getAvaliacaoDoEpisodio() > 0)
                    .collect(Collectors.groupingBy(Episodio::getTemporada,
                            Collectors.averagingDouble(Episodio::getAvaliacaoDoEpisodio)));
        System.out.println(avaliacoesPorTemporada);
    }
}
