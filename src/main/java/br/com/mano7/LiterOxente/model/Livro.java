package br.com.mano7.LiterOxente.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private String ano;
    private String download;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    public Livro(){

    }


    public Livro(LivroDados livroDados) {
        this.titulo = livroDados.titulo();
        this.idioma = livroDados.idiomas().isEmpty() ? "desconhecido" : livroDados.idiomas().get(0);

        AutorDados autorDados = livroDados.autores().isEmpty()
                ? new AutorDados("Desconhecido", null, null)
                : livroDados.autores().get(0);

        this.autor = new Autor(autorDados);
        this.download = livroDados.download();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
