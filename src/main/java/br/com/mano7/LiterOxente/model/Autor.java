package br.com.mano7.LiterOxente.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer nascimento;
    private Integer falecimento;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livro;

    public Autor(){

    }
    public Autor(AutorDados autorDados) {
        this.nome = autorDados.nome();
        this.nascimento = (autorDados.nascimento() != null) ? autorDados.nascimento() : 0;
        this.falecimento = autorDados.falecimento();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(Integer falecimento) {
        this.falecimento = falecimento;
    }

    public List<Livro> getLivro() {
        return livro;
    }

    public void setLivro(List<Livro> livro) {
        this.livro = livro;
    }
}
