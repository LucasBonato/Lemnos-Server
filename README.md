<div align="center">
    <br />
    <img src="src/main/resources/imagens/Logo-Lemnos-Horizontal-Branco.png" alt="Logo" />
    <hr />
    <img src="https://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge" alt="..." />
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

- [Como Utilizar](#Como-utilizar-a-API)
- [Headers](#Headers)
- [EndPoints](#Endpoints)
- [Erros](#Erros)
- [Exemplos](#Exemplos)
  - [Login](#Login)
  - [Registrar](#Register)
  - [Cliente](#Cliente)
  - [Funcionário](#Funcionário)
  - [Fornecedor](#Fornecedor)


# Como utilizar a API

API criada nesse projeto vem com a ideia de facilitar o trabalho na criação do e-commerce Lemnos, possuindo formas 
de cadastro de Clientes e Funcionários, gerenciamento, listagem e verificação de produtos, tratamento e 
gerenciamento de carrinho.

> _<ins>Ainda não foi feito o Deploy da API</ins>_

### _Url Base_: `http://localhost:8080/api/`

# Headers

|    Headers    |           Description            |
|:-------------:|:--------------------------------:|
| Content-Type  | application/json; charset=UTF-8  |
| Authorization |            {{token}}             |

---

# Endpoints

| **EndPoints** | **Sub Endpoints**                                                       | **Exemplos**                                                                                                      | **Body**                                                                                                                    |
|---------------|-------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| /auth         | /login<br/>/register<br/>/register/funcionario<br/>/register/fornecedor | [login](#Login)<br/>[register](#Register)<br/>[funcionario](#body-funcionario)<br/>[fornecedor](#body-fornecedor) | [login](#body-login)<br/>[register](#body-register)<br/>[funcionario](#body-funcionario)<br/>[fornecedor](#body-fornecedor) |
| /cliente      | /{id}                                                                   | [cliente](#Cliente)                                                                                               | [cliente](#body-put-cliente)                                                                                                |
| /funcionario  | /{id}                                                                   | [funcionario](#Funcionário)                                                                                       | [funcionario](#body-put-funcionário)                                                                                        |
| /fornecedor   | /{id}                                                                   | [fornecedor](#Fornecedor)                                                                                         | [fornecedor](#body-put-fornecedor)                                                                                          |

---

# Erros

~~~ JSON
{
    "id": 0
    "error": "Alguma mensagem de erro."
}
~~~

O que é o Id? Bem, é um identificador para saber sobre qual campo o erro se trata, sendo que os valores são 
"universais" na API, ou seja, se o id for igual a 1 representa que deu algum problema no campo de Email. 
Segue a tabela de valores: 

| Id |  Campo que representa   |
|----|:-----------------------:|
| 0  | Não implementado/Global |
| 1  |          Email          |
| 2  |          Senha          |
| 3  |          Nome           |
| 4  |        Telefone         |
| 5  |     dataNascimento      |
| 6  |      dataAdmissão       |
| 7  |           CPF           |
| 8  |          CNPJ           |
| 9  |           CEP           |

# Exemplos

## Auth

Login o Registro de um Cadastro por vez, ou de Cliente, Funcionario ou Fornecedor, sendo que todas as requisições são de *POST*

## Register

### Body Register:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
"email": "emailDeSuaEscolha@email.com",
"senha": "SenhaDoCliente"
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge) 

> `{{baseUri}}/auth/register`

JavaScript
~~~ javascript
let baseUri = "https://localhost:8080/api";

function register(usuario){
    
    usuario = tratarDados(usuario);

    fetch(baseUri + "/auth/register", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify({
            nome: usuario.nome,
            cpf: usuario.cpf,
            email: usuario.email,
            senha: usuario.senha
        })
    });
}

import axios from 'axios';
const axios = require("axios");

function register(usuario) {
    
    usuario = tratarDados(usuario);
    
    axios.post(baseUri + "/auth/register", {
        nome: usuario.nome,
        cpf: usuario.cpf,
        email: usuario.email,
        senha: usuario.senha
      })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> register(Usuario usuario) async{
        var response = await client.post(
            Uri.parse(baseUri + "/auth/register"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "nome": usuario.nome,
                "cpf": usuario.cpf,
                "email": usuario.email,
                "senha": usuario.senha
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |        Cadastrou com sucesso         |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Login

Quando logado a API irá retornar um token para ser utlizado posteriormente no [Header](#Headers) Authorization.

### Body Login
``` JSON
"email": "emailParaRealizarOLogin@email.com"
"senha": "senhaParaRealizarLogin"
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/auth/login`

JavaScript
~~~ javascript
let baseUri = "https://localhost:8080/api";

import axios from 'axios';
const axios = require("axios");

function login(usuario) {
    
    let token = "";
    
    usuario = tratarDados(usuario);
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/auth/login/fav",
      headers: {'Content-Type': 'application/json; charset=UTF-8'},
      body: {
        email: usuario.email,
        senha: usuario.senha
      }
    })
    .then((response) => token = response.data)
    .catch((error) => console.log(error));
    
    return token;  
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<String> login(Usuario usuario) async{
        String token = "";
      
        var response = await client.post(
            Uri.parse(baseUri + "/auth/login"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "email": usuario.email,
                "senha": usuario.senha
            })
        );
        
        token = response.body;
        return token;
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |          Logado com sucesso          |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |             

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Funcionario:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233344",
"telefone": "11400289221",
"dataNascimento": "01/01/0001",
"dataAdmissao": "01/01/0001",
"email": "emailDeSuaEscolha@email.com",
"senha": "SenhaDeSuaEscolha"
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/auth/register/funcionario`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarFuncionario(funcionario) {
    
    funcionario = tratarDados(funcionario);
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/auth/register/funcionario",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      body: {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        telefone: funcionario.telefone,
        dataNascimento: funcionario.dataNascimento,
        dataAdmissao: funcionario.dataAdmissao,
        email: funcionario.email,
        senha: funcionario.senha
      }
    })
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
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
            Uri.parse(baseUri + "/auth/register/funcionario"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": token
            },
            body: jsonEncode({
                "nome": funcionario.nome,
                "cpf": funcionario.cpf,
                "telefone": funcionario.telefone,
                "dataNascimento": funcionario.dataNascimento,
                "dataAdmissao": funcionario.dataAdmissao,
                "email": funcionario.email,
                "senha": funcionario.senha
            })
        );
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |        Cadastrou com sucesso         |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Fornecedor:
``` JSON
"nome": "Nome Do Fornecedor",
"cnpj": "09836719874612",
"telefone": "11999999999",
"numeroLogradouro": 0001,
"email": "emailDoFornecedor@gmail.com"
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/auth/register/fornecedor`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarFornecedor(fornecedor) {
    
    fornecedor = tratarDados(fornecedor);
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/auth/register/fornecedor",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      body: {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone,
        numeroLogradouro: fornecedor.numeroLogradouro,
        email: fornecedor.email
      }
    })
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
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
            Uri.parse(baseUri + "/auth/register/fornecedor"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8",
                "Authorization": token
            },
            body: jsonEncode({
                "nome": fornecedor.nome,
                "cnpj": fornecedor.cnpj,
                "telefone": fornecedor.telefone,
                "numeroLogradouro": fornecedor.numeroLogradouro,
                "email": fornecedor.email
            })
        );
    }
}
~~~

---

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |        Cadastrou com sucesso         |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Cliente

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/cliente`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getClientes() {
    
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/cliente",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<List<Cliente>> getClientes() async{
        var uri = Uri.parse(baseUri + "/cliente");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        List<dynamic> jsonResponse = json.decode(responseBodyUtf8);
        List<Cliente> clientes = jsonResponse.map((json) => Cliente.fromJson(json)).toList();
        return clientes;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |         Retornou os valores          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/cliente/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getCliente(id) {
    
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/cliente/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
    .then((response) => console.log(response.data))
    .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<Cliente> getCliente(int id) async{
        var uri = Uri.parse(baseUri + "/cliente/$id");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Cliente cliente = jsonResponse.map((json) => Cliente.fromJson(json));
        return cliente;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |               Why?                |
|-------------|:-----------:|:---------------------------------:|
| 200         |     OK      |         Retornou o valor          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Put Cliente:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/cliente/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarCliente(cliente) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/cliente/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      body: {
        nome: cliente.nome,
        cpf: cliente.cpf
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> alterarCliente(Cliente cliente) async{
        var response = await client.put(
          Uri.parse("$baseUri/${cliente.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
          },
          body: jsonEncode({
            "nome": cliente.nome,
            "cpf": cliente.cpf,
          }),
        );
        if(response.statusCode != 200){
          dynamic body = jsonDecode(utf8.decode(response.body.runes.toList()));
          return body;
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                           Why?                           |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 209         |  CONFLICT   |         Algum dado único já foi cadastrado antes         |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |          O objeto procurado não foi encontrado           |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/cliente/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirCliente(id) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/cliente/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> excluirCliente(int id) async{
        var response = await client.delete(Uri.parse("$baseUri/cliente/${cliente.id}"));
        if(response.statusCode != 200){
          return "Objeto não encontrado";
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |  Meaning  |                 Why?                  |
|-------------|:---------:|:-------------------------------------:|
| 200         |    OK     |           Retornou o valor            |
| 404         | NOT FOUND | O objeto procurado não foi encontrado |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Funcionário

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/funcionario`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFuncionarios() {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/funcionario",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<List<Funcionario>> getFuncionarios() async{
        var uri = Uri.parse(baseUri + "/funcionario");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        List<dynamic> jsonResponse = json.decode(responseBodyUtf8);
        List<Funcionario> funcionarios = jsonResponse.map((json) => Funcionario.fromJson(json)).toList();
        return funcionarios;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |         Retornou os valores          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/funcionario/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFuncionario(id) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/funcionario/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<Funcionario> getFuncionario(int id) async{
        var uri = Uri.parse(baseUri + "/funcionario/$id");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Funcionario funcionario = jsonResponse.map((json) => Funcionario.fromJson(json));
        return funcionario;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |               Why?                |
|-------------|:-----------:|:---------------------------------:|
| 200         |     OK      |         Retornou o valor          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Put Funcionário:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
"dataNascimento": "01/01/0001",
"dataAdmissao": "01/01/0001",
"telefone": "11912345678"
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/funcionario/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarFuncionario(funcionario) {

    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/funcionario/${funcionario.id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      body: {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        dataNascimento: funcionario.dtNasc,
        dataAdmissao: funcionario.dtAdmi,
        telefone: funcionario.telefone
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> alterarFuncionario(Funcionario funcionario) async{
        var response = await client.put(
          Uri.parse("$baseUri/funcionario/${funcionario.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode({
            "nome": funcionario.nome,
            "cpf": funcionario.cpf,
            "dataNascimento": funcionario.dtNasc,
            "dataAdmissao": funcionario.dtAdmi,
            "telefone": funcionario.telefone
          }),
        );
        if(response.statusCode != 200){
          dynamic body = jsonDecode(utf8.decode(response.body.runes.toList()));
          return body;
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                           Why?                           |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 209         |  CONFLICT   |         Algum dado único já foi cadastrado antes         |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |          O objeto procurado não foi encontrado           |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/funcionario/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirFuncionario(id) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/funcionario/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> excluirFuncionario(int id) async{
        var response = await client.delete(Uri.parse("$baseUri/funcionario/${id}"));
        if(response.statusCode != 200){
          return "Objeto não encontrado";
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |  Meaning  |                 Why?                  |
|-------------|:---------:|:-------------------------------------:|
| 200         |    OK     |           Retornou o valor            |
| 404         | NOT FOUND | O objeto procurado não foi encontrado |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Fornecedor

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/fornecedor`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFornecedores() {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/fornecedor",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<List<Fornecedor>> getFornecedores() async{
        var uri = Uri.parse(baseUri + "/fornecedor");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        List<dynamic> jsonResponse = json.decode(responseBodyUtf8);
        List<Fornecedor> fornecedores = jsonResponse.map((json) => Fornecedor.fromJson(json)).toList();
        return fornecedores;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |         Retornou os valores          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/fornecedor/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFornecedor(id) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/fornecedor/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<Fornecedor> getFornecedor(int id) async{
        var uri = Uri.parse(baseUri + "/fornecedor/$id");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Fornecedor fornecedor = jsonResponse.map((json) => Fornecedor.fromJson(json));
        return fornecedor;
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |               Why?                |
|-------------|:-----------:|:---------------------------------:|
| 200         |     OK      |         Retornou o valor          |                 

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Put Fornecedor:
``` JSON
"nome": "Qualquer Nome",
"cnpj": "11222333444455",
"telefone": "11912345678"
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/fornecedor/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarFornecedor(fornecedor) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/funcionario/${fornecedor.id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      body: {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> alterarFornecedor(Fornecedor fornecedor) async{
        var response = await client.put(
          Uri.parse("$baseUri/fornecedor/${fornecedor.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode({
            "nome": fornecedor.nome,
            "cnpj": fornecedor.cnpj,
            "telefone": fornecedor.telefone
          }),
        );
        if(response.statusCode != 200){
          dynamic body = jsonDecode(utf8.decode(response.body.runes.toList()));
          return body;
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |   Meaning   |                           Why?                           |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 209         |  CONFLICT   |         Algum dado único já foi cadastrado antes         |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |          O objeto procurado não foi encontrado           |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/fornecedor/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirFornecedor(id) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/fornecedor/${id}",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      }
    })
      .then((response) => console.log(response.data))
      .catch((error) => console.log(error));
}
~~~

Dart
~~~dart
import 'dart:convert';
import 'package:http/http.dart' as http;

Class Api{
    var client = http.Client();
    String baseUri = "https//localhost:8080/api";
    
    Future<dynamic> excluirFornecedor(int id) async{
        var response = await client.delete(Uri.parse("$baseUri/fornecedor/${id}"));
        if(response.statusCode != 200){
          return "Objeto não encontrado";
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |  Meaning  |                 Why?                  |
|-------------|:---------:|:-------------------------------------:|
| 200         |    OK     |           Retornou o valor            |
| 404         | NOT FOUND | O objeto procurado não foi encontrado |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

Nada de importante

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)
