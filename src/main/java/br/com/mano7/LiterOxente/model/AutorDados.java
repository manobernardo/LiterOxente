package br.com.mano7.LiterOxente.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDados(@JsonAlias("name") String nome,
                         @JsonAlias("birth_year") Integer nascimento,
                         @JsonAlias("death_year") Integer falecimento) {
}
