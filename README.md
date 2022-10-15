:warning: Esse projeto ainda está em fase de desenvolvimento.

#### Recursos já disponíveis

#### Criação e leitura
* professores
* matérias
* turmas
* alunos

#### Ainda em desenvolvimento

* Login do gerenciador da escola.
* Login dos alunos para o acesso das notas (Notas ainda não disponíveis).
* Login dos professores para registar as notas dos alunos.

#### Rotas já Disponíveis

URL: http://localhost:8080/api/v1/<rota>

|Rota | Método | Ação
|-----|--------|-------
|/turma | GET | Lista todas as turmas
|/turma | POST | Registra uma nova turma
|/professor | GET | Lista todos os professores
/professor | POST | Registra um novo professor
|/materia | GET | Lista todas as matérias
|/materia | POST | Registra uma nova matéria
|/aluno | GET | Lista todos os alunos
|/aluno | POST | Registra um novo aluno


### Como registrar uma turma?
  
URL: http://localhost:8080/api/v1/turma

  #### Exemplo de entrada
  ~~~JSON
  
  "title": "turma-1"
  
  ~~~

* Não é possível registrar duas turmas com o mesmo nome.
* Em uma turma só é possível registar no máximo 30 alunos.
* Em uma turma só é possível registar 8 professores (ainda não implementado).


### Como registrar um professor?
  
URL: http://localhost:8080/api/v1/professor

  #### Exemplo de entrada
  ~~~JSON
{
	"name": "Professor-1",
	"username": "professor1",
	"password": "12345",
	"turma": { "id": 1 },
	"materia": { "id": 1 }
}
  
  ~~~
  
* Como o professor terá um sistema de login, não será possível registar dois professores com o mesmo nome de usuário.
* Para registrar um professor, é necessário primeiro registar uma turma e uma matéria.
* O professor só poderá fazer parte de uma única turma (pode mudar no projeto final).
* Para cada professor, só será permitido ensinar uma matéria (Pode mudar na versão final).


### Como registrar uma matéria?
  
URL: http://localhost:8080/api/v1/materia

  #### Exemplo de entrada
  ~~~JSON
{
	"title": "Matemática"
}
  
  ~~~

* Não será permitido registrar duas matérias com o mesmo nome.


### Como registrar um aluno?
  
URL: http://localhost:8080/api/v1/aluno

  #### Exemplo de entrada
  ~~~JSON
{
	"name": "Aluno-1",
	"username": "aluno-1",
	"password": "12345",
	"turma": { "id": 1 }
}
  
  ~~~

* Antes de registar um aluno, verifique se já possui alguma turma registrada, caso contrário um erro será exibido para o usuário.
	
	
Por enquanto isso é tudo. Você pode revisar alguns testes que foram implementados para os serviços.

email: eduardop9011@gmail.com