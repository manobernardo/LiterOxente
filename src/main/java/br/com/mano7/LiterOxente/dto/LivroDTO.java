package br.com.mano7.LiterOxente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {
    private String title;
    private List<String> languages;
    private List<AutorDTO> authors;
    private String download_count;

    public String getTitle() { return title; }
    public List<String> getLanguages() { return languages; }
    public List<AutorDTO> getAuthors() { return authors; }
    public String getDownload_count() { return download_count; }
}
