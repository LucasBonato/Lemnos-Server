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
| /cadastro     |                   | [/cliente]()<br/>[/funcionario]()<br/>[/fornecedor]() | [Json Cliente]()<br>[Json Funcionario]()<br>[Json Fornecedor]() |
| /cliente      |                   |                                                       |                                                                 |
| /funcionario  |                   |                                                       |                                                                 |
| /fornecedor   |                   |                                                       |                                                                 |
