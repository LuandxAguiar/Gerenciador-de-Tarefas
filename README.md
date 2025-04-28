# 📋 API de Gerenciamento de Tarefas

Este projeto é uma API REST desenvolvida em **Java 17 + Spring Boot** para gerenciar **Pessoas** e **Tarefas** de um time de trabalho.

A API permite criar, editar, deletar, alocar e consultar tarefas e pessoas, além de trazer relatórios simples como horas gastas e tarefas pendentes.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5 / Mockito (Testes)

---

## 🛠️ Como Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git](https://github.com/LuandxAguiar/Gerenciador-de-Tarefas.git
2 .Configure o banco de dados PostgreSQL:
  ° Crie um banco chamado gerenciadorTarefa
  ° Usuário padrão: postgres
  ° Senha padrão: root
Altere o application.properties caso necessário.

3. Rode o projeto:

Pelo Eclipse, STS ou IntelliJ: Run 'GerenciadorApplication'

A aplicação ficará disponível em:
http://localhost:8080

📚 Endpoints Disponíveis (Postman ou Insomnia)
🧑 Pessoas

Método	Endpoint	Descrição
POST	/pessoas	Adicionar nova pessoa
PUT	/pessoas/{id}	Atualizar pessoa existente
DELETE	/pessoas/{id}	Deletar pessoa
GET	/pessoas	Listar todas as pessoas
GET	/pessoas/horas	Listar pessoas com total de horas gastas
GET	/pessoas/gastos?nome={nome}&inicio={data}&fim={data}	Buscar pessoas por nome e período, trazendo a média de horas por tarefa
GET	/pessoas/departamentos	Listar departamentos com quantidade de pessoas e tarefas

📋 Tarefas
Método	Endpoint	Descrição
POST	/tarefas	Adicionar nova tarefa
PUT	/tarefas/finalizar/{id}	Finalizar uma tarefa
PUT	/tarefas/alocar/{tarefaId}/pessoa/{pessoaId}	Alocar uma pessoa na tarefa (departamento deve ser igual)
GET	/tarefas	Listar todas as tarefas
GET	/tarefas/pendentes	Listar 3 tarefas pendentes com os prazos mais antigos


✅ Regras de Negócio
Só é possível alocar uma pessoa em uma tarefa se departamentos forem iguais.

Na listagem de tarefas pendentes, são trazidas as 3 tarefas mais antigas sem pessoa alocada.

As pessoas possuem total de horas gastas em tarefas calculadas automaticamente.

🧪 Testes
O projeto possui testes unitários para:

PessoaService
TarefaService

Rodar os testes:
mvn test

