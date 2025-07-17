package br.com.mano7.LiterOxente.repository;

import br.com.mano7.LiterOxente.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
