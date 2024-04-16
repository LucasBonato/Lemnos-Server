<div align="center">
    <br />
    <img src="src/main/resources/imagens/Logo-Lemnos-Horizontal-Branco.png" alt="Logo" />
    <hr />
    <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge" alt="..." />
    <img src="https://img.shields.io/static/v1?label=Projeto%20de&message=TCC&color=blue&style=for-the-badge" alt="..." />
    <hr />
    <p>
        Uma API para o projeto de TCC para a finalização do curso de Desenvolvimento de Sistemas na 
        <a href="https://basilides.com.br">Etec Professor Basilides de Godoy</a>, sendo feita com o ecossistema do 
<a href="https://spring.io">Framework Spring</a>, em conjunto de um <a href="https://lemnos.vercel.app">site</a> 
        utilizando React e um <a href="">aplicativo</a> mobile utilizando Flutter.
    </p>
    <hr />
</div>

# Como utilizar a API

API criada nesse projeto vem com a ideia de facilitar o trabalho na criação do e-commerce Lemnos, possuindo formas 
de cadastro de Clientes e Funcionários, gerenciamento, listagem e verificação de produtos, tratamento e 
gerenciamento de carrinho.

> _<ins>Ainda não foi feito o Deploy da API</ins>_

### _Url Base_: `http://localhost:8080/api/`

|   Headers    | Description      |
|:------------:|:-----------------|
| Content-Type | application/json |
|   charset    | =utf8            |

---

# Endpoints

| **EndPoints** | **Sub Endpoints** | **Exemplos**                                          | **O que enviar?**                                               | **Parâmetros** |
|---------------|-------------------|-------------------------------------------------------|-----------------------------------------------------------------|----------------|
| /cadastro     |                   | [/cliente](#cliente)<br/>[/funcionario](#funcionario)<br/>[/fornecedor]() | [Json Cliente](#jsoncliente)<br>[Json Funcionario](#jsonfuncionario)<br>[Json Fornecedor]() |
| /cliente      |                   |                                                       |                                                                 |
| /funcionario  |                   |                                                       |                                                                 |
| /fornecedor   |                   |                                                       |                                                                 |


# Cliente

<hr />

O cliente tem 5 métodos para fazer as requisições para a API, sendo elas para criar um cliente, listar
todos os clientes cadastrados no sistema, para mostrar um cliente em específico, atualizá-lo e deletá-lo,
com estes três últimos métodos utilizando seu Id como parâmetro.

A seguir está os exemplos de como utilizar a API em seu navegador:
-    [GET]  localhost:8080/api/cliente
-    [GET]  localhost:8080/api/cliente/{id}
-    [POST] localhost:8080/api/cadastro/cliente
-    [PUT]  localhost:8080/api/cliente/{id}
-    [DEL]  localhost:8080/api/cliente/{id}


# JsonCliente

<hr />

Para poder fazer o POST do Cliente, tem que se usar o seguinte JSON:
```
"nome": "qualquernome",
"telefone": "11400289221", (são 11 caracteres, tirando os símbolos)
"cpf": "11122233311", (são 11 caracteres, tirando os símbolos)
"email": "emaildesuaescolha@email.com",
"senha": senhadesuaescola, (tem que ser no mínimo 8 caracteres)
"numeroLogradouro": 00 (são no máximo 4 caracteres)
```
	



# Funcionario

<hr />

O cliente tem 5 métodos para fazer as requisições para a API, sendo elas para criar um cliente, listar
todos os clientes cadastrados no sistema, para mostrar um cliente em específico, atualizá-lo e deletá-lo,
com estes três últimos métodos utilizando seu Id como parâmetro.

A seguir está os exemplos de como utilizar a API em seu navegador:
-    [GET]  localhost:8080/api/cliente
-    [GET]  localhost:8080/api/cliente/{id}
-    [POST] localhost:8080/api/cadastro/funcionario
-    [PUT]  localhost:8080/api/cliente/{id}
-    [DEL]  localhost:8080/api/cliente/{id} 

# JsonFuncionario

<hr />

Para poder fazer o POST do Funcionario, tem que se usar o seguinte JSON:
```
"nome": "qualquernome",
"cpf": "11122233344", (são 11 caracteres, tirando os símbolos)
"dataNascimento": "01/01/0001",
"dataAdmissao": "01/01/0001",
"telefone": "11400289221", (são 11 caracteres, tirando os símbolos)
"email": "emaildesuaescolha@email.com",
"senha": senhadesuaescolha, (tem que ser no mínimo 8 caracteres)
"numeroLogradouro": 00 (são no máximo 4 caracteres)
```