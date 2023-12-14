# banco-api
Projeto de estudo para desenvolvimento de uma API de serviços bancários.

# :computer: Tecnologias utilizadas
- Java, Quarkus, Hibernate, MySQL

# :white_check_mark: Endpoints principais
- "/api/v1/users"
- "/api/v1/accounts"

# :hammer: Funcionalidades do projeto

- Metódo: POST Endpoint:/api/v1/users - Cadastra um usuário com os seguintes atributos "nome","endereco","idade" e "telefone".

- Metódo: POST Endpoint: "/api/v1/accounts/criarConta" - Cria uma conta recebendo os parâmetros "userId" e "accountType"

- Metódo: POST - Endpoint: "/api/v1/accounts/depositar" - Deposita determinado valor para uma conta, recebe como parâmetros numeroConta e valor

- Metódo: POST - Endpoint: "api/v1/accounts/sacar" - Saca determinado valor da conta, recebe como parâmetros numeroConta e valor

- Metódo: GET - Endpoint: "/api/v1/accounts/listarContas" - Retorna uma lista das contas cadastradas exibindo os atributos "userId","accountType","saldoAtual","numeroConta" e "nomeUsuario"

- Metódo: GET - Endpoint: "api/v1/accounts/detalhesConta/{numeroConta}" - Retorna os detalhes de uma conta específica, recebendo como parâmetro o número da conta e exibindo os atributos "numeroConta","accountType","saldoAtual","nomeUsuario", "idade","telefone","endereco"
