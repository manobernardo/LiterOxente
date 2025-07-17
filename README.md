# 📚 LiterOxente

**LiterOxente** é uma aplicação Java com Spring Boot que permite consultar e armazenar informações de livros e autores. Os dados são obtidos via API **Gutenbox** e armazenados em um banco **PostgreSQL**. A interação com o usuário ocorre via terminal, com um menu simples e funcional.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- API [Gutenbox](https://gutenbox.app)
- Maven

---

## 🧠 Funcionalidades

- Buscar livros por autor e salvar no banco de dados.
- Listar autores registrados e seus respectivos livros.
- Exibir autores vivos em determinado ano.
- Listar livros por idioma (busca por sigla ou nome).
- Evita registros duplicados de autores e livros.
- Interface textual com menu interativo via console.

---

## 📦 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/literoxente.git
cd literoxente
