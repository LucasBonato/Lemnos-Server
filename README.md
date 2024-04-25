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

|   Headers    | Description                     |
|:------------:|:--------------------------------|
| Content-Type | application/json; charset=UTF-8 |

---

# Endpoints

| **EndPoints** | **Sub Endpoints**                         | **Exemplos**                                                                      | **O que enviar?**                                                                           | **Parâmetros** |
|---------------|-------------------------------------------|-----------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|----------------|
| /cadastro     | /cliente<br/>/funcionario<br/>/fornecedor | [cliente](#cliente)<br/>[funcionario](#funcionario)<br/>[fornecedor](#fornecedor) | [Json Cliente](#jsoncliente)<br>[Json Funcionario](#jsonfuncionario)<br>[Json Fornecedor]() |
| /cliente      |                                           |                                                                                   |                                                                                             |
| /funcionario  |                                           |                                                                                   |                                                                                             |
| /fornecedor   |                                           |                                                                                   |                                                                                             |

---

# Exemplos

## Cadastro

Insere um único Cadastro por vez, ou de Cliente, Funcionario ou Fornecedor, sendo que todas as requisições são de *POST*

### Cliente

#### Parâmetros:
```
 (String) "nome": "Qualquer Nome",
 (String) "telefone": "11400289221",
 (String) "cpf": "11122233311",
 (String) "email": "emailDeSuaEscolha@email.com",
 (String) "senha": "SenhaDoCliente",
(Integer) "numeroLogradouro": 0001
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge) 

> `{{baseUri}}/cadastro/cliente`

JavaScript
~~~javascript
let baseUri = "https://localhost:8080/api";

function cadastrarCliente(cliente){
    
    cliente = tratarDados(cliente);

    fetch(baseUri + "/cadastro/cliente", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
            nome: cliente.nome,
            telefone: cliente.telefone,
            cpf: cliente.cpf,
            email: cliente.email,
            senha: cliente.senha,
            numeroLogradouro: cliente.numeroLogradouro
        })
    });
}

// Utilizando Axios
// ($ npm install axios)
function cadastrarCliente(cliente) {
    
    cliente = tratarDados(cliente);
    
    axios.post(baseUri + "/cadastro/cliente", {
        nome: cliente.nome,
        telefone: cliente.telefone,
        cpf: cliente.cpf,
        email: cliente.email,
        senha: cliente.senha,
        numeroLogradouro: cliente.numeroLogradouro
      })
      .then((response) => console.log(response.data))
      .then((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> cadastrarCliente(Cliente cliente) async{
        var response = await client.post(
            Uri.parse(baseUri + "/cadastro/cliente"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "nome": cliente.nome,
                "telefone": cliente.telefone,
                "cpf": cliente.cpf,
                "email": cliente.email,
                "senha": cliente.senha,
                "numeroLogradouro": cliente.numeroLogradouro
            })
        );
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 | Corpo Devolvido |
|-------------|:-----------:|:------------------------------------:|-----------------|
| 200         |     OK      |        Cadastrou com sucesso         |                 |
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 |
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 |

---

### Funcionario
#### Parâmetros:
```
 (String) "nome": "Qualquer Nome",
 (String) "cpf": "11122233344",
 (String) "telefone": "11400289221",
 (String) "dataNascimento": "01/01/0001",
 (String) "dataAdmissao": "01/01/0001",
 (String) "email": "emailDeSuaEscolha@email.com",
 (String) "senha": "SenhaDeSuaEscolha",
(Integer) "numeroLogradouro": 0001
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/cadastro/funcionario`

JavaScript
~~~javascript
let baseUri = "https://localhost:8080/api";

function cadastrarFuncionario(funcionario){
    
    funcionario = tratarDados(funcionario);

    fetch(baseUri + "/cadastro/funcionario", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
            nome: funcionario.nome,
            cpf: funcionario.cpf,
            telefone: funcionario.telefone,
            dataNascimento: funcionario.dataNascimento,
            dataAdmissao: funcionario.dataAdmissao,
            email: funcionario.email,
            senha: funcionario.senha,
            numeroLogradouro: funcionario.numeroLogradouro
        })
    });
}

// Utilizando Axios
// ($ npm install axios)
function cadastrarFuncionario(cliente) {
    
    cliente = tratarDados(cliente);
    
    axios.post(baseUri + "/cadastro/funcionario", {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        telefone: funcionario.telefone,
        dataNascimento: funcionario.dataNascimento,
        dataAdmissao: funcionario.dataAdmissao,
        email: funcionario.email,
        senha: funcionario.senha,
        numeroLogradouro: funcionario.numeroLogradouro
      })
      .then((response) => console.log(response.data))
      .then((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> cadastrarFuncionario(Funcionario funcionario) async{
        var response = await client.post(
            Uri.parse(baseUri + "/cadastro/funcionario"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "nome": funcionario.nome,
                "cpf": funcionario.cpf,
                "telefone": funcionario.telefone,
                "dataNascimento": funcionario.dataNascimento,
                "dataAdmissao": funcionario.dataAdmissao,
                "email": funcionario.email,
                "senha": funcionario.senha,
                "numeroLogradouro": funcionario.numeroLogradouro
            })
        );
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 | Corpo Devolvido |
|-------------|:-----------:|:------------------------------------:|-----------------|
| 200         |     OK      |        Cadastrou com sucesso         |                 |
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 |
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 |

---

### Fornecedor
#### Parâmetros:
```
 (String) "nome": "Nome Do Fornecedor",
 (String) "cnpj": "09836719874612",
 (String) "telefone": "11999999999",
(Integer) "numeroLogradouro": 0001,
 (String) "email": "emailDoFornecedor@gmail.com",
 (String) "cep": "0000000"
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/cadastro/fornecedor`

JavaScript
~~~javascript
let baseUri = "https://localhost:8080/api";

function cadastrarFornecedor(fornecedor){
    
    fornecedor = tratarDados(fornecedor);

    fetch(baseUri + "/cadastro/fornecedor", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
            nome: fornecedor.nome,
            cnpj: fornecedor.cnpj,
            telefone: fornecedor.telefone,
            numeroLogradouro: fornecedor.numeroLogradouro,
            email: fornecedor.email,
            cep: fornecedor.cep,
        })
    });
}

// Utilizando Axios
// ($ npm install axios)
function cadastrarFornecedor(fornecedor) {
    
    fornecedor = tratarDados(fornecedor);
    
    axios.post(baseUri + "/cadastro/fornecedor", {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone,
        numeroLogradouro: fornecedor.numeroLogradouro,
        email: fornecedor.email,
        cep: fornecedor.cep,
      })
      .then((response) => console.log(response.data))
      .then((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> cadastrarFornecedor(Fornecedor fornecedor) async{
        var response = await client.post(
            Uri.parse(baseUri + "/cadastro/fornecedor"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "nome": fornecedor.nome,
                "cnpj": fornecedor.cnpj,
                "telefone": fornecedor.telefone,
                "numeroLogradouro": fornecedor.numeroLogradouro,
                "email": fornecedor.email,
                "cep": fornecedor.cep,
            })
        );
    }
}
~~~

---

#### Responses:
| Status Code |   Meaning   |                 Why?                 | Corpo Devolvido |
|-------------|:-----------:|:------------------------------------:|-----------------|
| 200         |     OK      |        Cadastrou com sucesso         |                 |
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 |
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 |


---

Nada de importante

![GET](https://img.shields.io/static/v1?label=&message=GET&color=lime&style=for-the-badge)
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)
~~~ javascript
GET
fetch(uri + "/cadastro/cliente")
    .then((response) => {
        return response.json();
    })
    .then((data) => {
        let nome = data['nome'];
        let nome = data['nome'];
        let nome = data['nome'];
        let nome = data['nome'];
        let nome = data['nome'];
        let nome = data['nome'];
    })
    .catch()
~~~