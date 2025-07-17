package br.com.mano7.LiterOxente.service;

import java.util.List;

public interface IConverteDados {

    <T> T obterdados(String json, Class<T> classe);

    //<T> List<T> obterLista(String json, Class<T> classe);

}
