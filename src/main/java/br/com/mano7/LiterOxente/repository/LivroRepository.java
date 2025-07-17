package br.com.mano7.LiterOxente.repository;

import br.com.mano7.LiterOxente.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAutorNomeContainingIgnoreCase(String nome);

}
