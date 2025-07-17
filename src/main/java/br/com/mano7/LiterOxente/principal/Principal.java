package br.com.mano7.LiterOxente.principal;

import br.com.mano7.LiterOxente.model.Autor;
import br.com.mano7.LiterOxente.model.Livro;
import br.com.mano7.LiterOxente.model.LivroDados;
import br.com.mano7.LiterOxente.model.ResultadoDados;
import br.com.mano7.LiterOxente.repository.AutorRepository;
import br.com.mano7.LiterOxente.repository.LivroRepository;
import br.com.mano7.LiterOxente.service.ConsumoAPI;
import br.com.mano7.LiterOxente.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {


    private Scanner leitura = new Scanner(System.in);

    @Autowired
    private ConsumoAPI consumo = new ConsumoAPI();
    @Autowired
    private ConverteDados converteDados = new ConverteDados();

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final String BASE_URL = "https://gutendex.com/books/";

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }


    public void obterMenu() {

        int opcao = -1;

        while (opcao != 0) {
            var menu = """
                    ******* ESCOLHA UMA OPÇÃO ********
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                  
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    buscarLivroTitulo();
                break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }




    private void buscarLivroTitulo() {
        System.out.println("Digite o título do livro:");
        String titulo = leitura.nextLine();

        try {
            String json = consumo.obterDados(BASE_URL + "?search=" + titulo.replace(" ", "+"));
            ResultadoDados resultado = converteDados.obterdados(json, ResultadoDados.class);

            if (resultado.results().isEmpty()) {
                System.out.println("❌ Nenhum livro encontrado com esse título.");
                return;
            }

            LivroDados livroDados = resultado.results().get(0);
            Livro livro = new Livro(livroDados); // usa o construtor

            livroRepository.save(livro);

            System.out.println("\n✅ Livro salvo com sucesso!");
            System.out.printf("Título: %s\nAutor: %s\nIdioma: %s\n",
                    livro.getTitulo(),
                    livro.getAutor().getNome(),
                    livro.getIdioma());

        } catch (Exception e) {
            System.out.println("Erro ao processar dados da API: " + e.getMessage());
        }
    }
    private void listarLivrosRegistrados() {
    List<Livro> livros = livroRepository.findAll(); // Busca todos os livros no DB

        if (livros.isEmpty()) {
        System.out.println("Nenhum livro registrado ainda.");
    } else {
        System.out.println("\n--- LIVROS REGISTRADOS ---");
        // Opcional: ordenar por título para uma lista mais legível
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(livro -> {
                    System.out.println("------------------------------------");
                    System.out.println("Título: " + livro.getTitulo());
                    // Verifica se o autor não é nulo antes de tentar pegar o nome
                    System.out.println("Autor: " + (livro.getAutor() != null ? livro.getAutor().getNome() : "Desconhecido"));
                    System.out.println("Idioma: " + livro.getIdioma());
                    System.out.println("Downloads: " + livro.getDownload());
                    System.out.println("------------------------------------\n");
                });
    }
    }

    private void listarAutoresRegistrados() {
        System.out.print("Digite o nome (ou parte) do autor: ");
        String nomeAutor = leitura.nextLine();

        List<Livro> livros = livroRepository.findByAutorNomeContainingIgnoreCase(nomeAutor);

        if (livros.isEmpty()) {
            System.out.println("Nenhum autor encontrado com esse nome.");
        } else {
            // Agrupar livros por nome de autor
            Map<String, List<Livro>> livrosPorAutor = livros.stream()
                    .collect(Collectors.groupingBy(l -> l.getAutor().getNome()));

            System.out.println("\n--- AUTORES E LIVROS REGISTRADOS ---");

            livrosPorAutor.forEach((autor, livrosDoAutor) -> {
                System.out.println("Autor encontrado: " + autor);
                System.out.println("Livros:");
                livrosDoAutor.forEach(l -> System.out.println("📘 " + l.getTitulo()));
                System.out.println();
            });
        }


    }
    private void listarAutoresVivosPorAno() {
        System.out.print("Digite o ano para verificar autores vivos: ");
        int ano = leitura.nextInt();
        leitura.nextLine(); // limpar o buffer

        List<Autor> autores = autorRepository.findAll();

        Set<Autor> autoresVivosUnicos = autores.stream()
                .filter(a -> a.getNascimento() <= ano &&
                        (a.getFalecimento() == null || a.getFalecimento() > ano))
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Autor::getNome)))
                );

        if (autoresVivosUnicos.isEmpty()) {
            System.out.println("Nenhum autor estava vivo no ano " + ano + ".");
        } else {
            System.out.println("\nAutores vivos no ano " + ano + ":");
            autoresVivosUnicos.forEach(a -> {
                String statusMorte = (a.getFalecimento() == null) ? "ainda vivo" : "falecido em " + a.getFalecimento();
                System.out.printf("📚 %s (nascido em %d, %s)\n", a.getNome(), a.getNascimento(), statusMorte);
            });
        }
    }

    private void listarLivosPorIdioma() {
        System.out.println("\nEscolha um idioma (digite a sigla ou o nome):");
        System.out.println("pt - Português");
        System.out.println("en - Inglês");
        System.out.println("es - Espanhol");
        System.out.println("fr - Francês");
        System.out.println("de - Alemão");
        System.out.print("Sua escolha: ");
        String escolha = leitura.nextLine().trim().toLowerCase();

        // Mapear siglas e nomes para a sigla padrão
        Map<String, String> mapaIdiomas = new HashMap<>();
        mapaIdiomas.put("pt", "pt");
        mapaIdiomas.put("portugues", "pt");
        mapaIdiomas.put("en", "en");
        mapaIdiomas.put("ingles", "en");
        mapaIdiomas.put("es", "es");
        mapaIdiomas.put("espanhol", "es");
        mapaIdiomas.put("fr", "fr");
        mapaIdiomas.put("frances", "fr");
        mapaIdiomas.put("de", "de");
        mapaIdiomas.put("alemao", "de");

        Map<String, String> nomeCompleto = new HashMap<>();
        nomeCompleto.put("pt", "Português");
        nomeCompleto.put("en", "Inglês");
        nomeCompleto.put("es", "Espanhol");
        nomeCompleto.put("fr", "Francês");
        nomeCompleto.put("de", "Alemão");

        String idiomaSelecionado = mapaIdiomas.get(escolha);

        if (idiomaSelecionado == null) {
            System.out.println("Idioma não reconhecido.");
            return;
        }

        Set<String> titulosExibidos = new HashSet<>();


        List<Livro> livros = livroRepository.findAll().stream()
                .filter(l -> l.getIdioma() != null && l.getIdioma().equalsIgnoreCase(idiomaSelecionado))
                .filter(l -> titulosExibidos.add(l.getTitulo())) // só adiciona se ainda não existe
                .collect(Collectors.toList());

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma selecionado (" + idiomaSelecionado + ") => " + nomeCompleto.get(idiomaSelecionado));
        } else {
            System.out.println("\nLivros no idioma " + "("+ idiomaSelecionado + ") =>" + nomeCompleto.get(idiomaSelecionado));
            livros.forEach(l -> System.out.printf("📖 %s (%s)\n", l.getTitulo(), l.getIdioma()));
        }
    }

}
