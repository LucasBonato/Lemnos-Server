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
  - [Cadastro](#Cadastro)
  - [Cliente](#Cliente)
  - [Funcionário](#Funcionário)
  - [Fornecedor](#Fornecedor)
  - [Endereço](#Endereço)
  - [Produto](#Produto)


# Como utilizar a API

API criada nesse projeto vem com a ideia de facilitar o trabalho na criação do e-commerce Lemnos, possuindo formas 
de cadastro de Clientes e Funcionários, gerenciamento, listagem e verificação de produtos, tratamento e 
gerenciamento de carrinho.

> _<ins>Ainda não foi feito o Deploy da API</ins>_

### _Url Base_: `http://localhost:8080/api/`

# Headers

|   Headers    | Description                     |
|:------------:|:--------------------------------|
| Content-Type | application/json; charset=UTF-8 |

---

# Endpoints

| **EndPoints** | **Sub Endpoints**                         | **Exemplos**                                                                                                             | **Body**                                                                                       |                                         Descrição                                          |
|---------------|-------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------:|
| /cadastro     | /cliente<br/>/funcionario<br/>/fornecedor | [cliente](#body-cliente)<br/>[funcionario](#body-funcionario)<br/>[fornecedor](#body-fornecedor)                         | [Cliente](#body-cliente)<br>[Funcionario](#body-funcionario)<br>[Fornecedor](#body-fornecedor) |                    Permite realizar o cadastro das entidades do sistema                    |
| /cliente      | /{id}<br/>/endereco                       | [cliente](#Cliente)<br/>[endereço](#Endereço)                                                                            | [Cliente](#body-put-cliente)<br/>[Endereço](#body-endereço)                                    |            Possui a forma de conseguir procurar clientes, alterar ou desativar             |
| /funcionario  | /{id}<br/>/endereco                       | [funcionario](#Funcionário)<br/>[endereço](#Endereço)                                                                    | [Funcionário](#body-put-funcionário)<br/>[Endereço](#body-endereço)                            |          Possui a forma de conseguir procurar funcionários, alterar ou desativar           |
| /fornecedor   | /{id}<br/>/endereco                       | [fornecedor](#Fornecedor)<br/>[endereço](#Endereço)                                                                      | [Fornecedor](#body-put-fornecedor)<br/>[Endereço](#body-endereço)                              |          Possui a forma de conseguir procurar fornecedores, alterar ou desativar           |
| /produto      | /{id}<br/>/fav<br/>/desconto/{id}         | [produto](#body-produto)<br/>[favoritar](#Favoritar)<br/>[desfavoritar](#Desfavoritar)<br/>[desconto](#Retirar-Desconto) | [Produto](#produto)                                                                            | Possui a forma de conseguir procurar produtos, alterar, deletar, favoritar ou desfavoritar |

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

| Id |        Campo representado         |
|----|:---------------------------------:|
| 0  | Não implementado/Mais de um campo |
| 1  |               Email               |
| 2  |               Senha               |
| 3  |               Nome                |
| 4  |             Telefone              |
| 5  |          dataNascimento           |
| 6  |           dataAdmissão            |
| 7  |                CPF                |
| 8  |               CNPJ                |
| 9  |                CEP                |
| 10 |            Logradouro             |
| 11 |              Cidade               |
| 12 |              Bairro               |
| 13 |                UF                 |
| 14 |       Número do Logradouro        |
| 15 |            Complemento            |
| 16 |             Descrição             |
| 17 |                Cor                |
| 18 |               Valor               |
| 19 |              Modelo               |
| 20 |               Peso                |
| 21 |              Altura               |
| 22 |            Comprimento            |
| 23 |              Largura              |
| 24 |            Fabricante             |
| 25 |           SubCategoria            |
| 26 |         Imagem Principal          |
| 27 |        Imagens Secundárias        | 
| 28 |             Desconto              |
---

# Exemplos

## Cadastro

Insere um único Cadastro por vez, ou de Cliente, Funcionario ou Fornecedor, sendo que todas as requisições são de *POST*

### Body Cliente:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
"email": "emailDeSuaEscolha@email.com",
"senha": "SenhaDoCliente"
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge) 

> `{{baseUri}}/cadastro/cliente`

JavaScript
~~~ javascript
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
            senha: cliente.senha
        })
    });
}

import axios from 'axios';
const axios = require("axios");

function cadastrarCliente(cliente) {
    
    cliente = tratarDados(cliente);
    
    axios.post(baseUri + "/cadastro/cliente", {
        nome: cliente.nome,
        telefone: cliente.telefone,
        cpf: cliente.cpf,
        email: cliente.email,
        senha: cliente.senha
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
                "senha": cliente.senha
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 201         |   CREATED   |        Cadastrou com sucesso         |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 
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

> `{{baseUri}}/cadastro/funcionario`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarFuncionario(cliente) {
    
    cliente = tratarDados(cliente);
    
    axios.post(baseUri + "/cadastro/funcionario", {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        telefone: funcionario.telefone,
        dataNascimento: funcionario.dataNascimento,
        dataAdmissao: funcionario.dataAdmissao,
        email: funcionario.email,
        senha: funcionario.senha
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
                "senha": funcionario.senha
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 201         |   CREATED   |        Cadastrou com sucesso         |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                   

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

> `{{baseUri}}/cadastro/fornecedor`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarFornecedor(fornecedor) {
    
    fornecedor = tratarDados(fornecedor);
    
    axios.post(baseUri + "/cadastro/fornecedor", {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone,
        numeroLogradouro: fornecedor.numeroLogradouro,
        email: fornecedor.email
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
            Uri.parse(baseUri + "/cadastro/fornecedor"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
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
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 201         |   CREATED   |        Cadastrou com sucesso         |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |                      

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
    axios.get(baseUri + "/cliente")
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
    axios.get(baseUri + "/cliente/${id}")
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
    axios.put(baseUri + "/cliente/${cliente.id}", {
        nome: cliente.nome,
        cpf: cliente.cpf,
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
    axios.delete(baseUri + "/cliente/${id}")
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
    axios.get(baseUri + "/funcionario")
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
    axios.get(baseUri + "/funcionario/${id}")
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
    axios.put(baseUri + "/funcionario/${funcionario.id}", {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        dataNascimento: funcionario.dtNasc,
        dataAdmissao: funcionario.dtAdmi,
        telefone: funcionario.telefone
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
    axios.delete(baseUri + "/funcionario/${id}")
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
    axios.get(baseUri + "/fornecedor")
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
    axios.get(baseUri + "/fornecedor/${id}")
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
    axios.put(baseUri + "/fornecedor/${fornecedor.id}", {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone
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
    axios.delete(baseUri + "/fornecedor/${id}")
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

## Endereço

Permite o cadastro de novos endereços ou alteração de endereços já existentes, o método mostrado a seguir funciona 
com as entidade, Cliente, Funcionário e Fornecedor, Cliente e Funcionário podem possuir mais de um endereço, 
o Fornecedor só pode possui um endereço. Utilizando uma API externa para consulta de cep: [ViaCep](https://viacep.com.br/)

### Body Endereço:
``` JSON
"cep": "11111222",
"numeroLogradouro": 0001,
"complemento": "Apto x bloco y/ casa x",
"entidade": "(cliente ou funcionario ou fornecedor)"
```
###### Se nada for passado em "entidade" será cadastrado para o cliente.
### Parâmetros:
| Key | Tipo | Descrição                                              |
|-----|------|--------------------------------------------------------|
| id  | int  | Id da entidade que se deseja adicionar o Endereço novo |

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/endereco?id=`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarEndereco(idEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/endereco",
      headers: {'Content-Type': 'application/json; charset=UTF-8'}
      data: {
        cep: endereco.cep,
        numeroLogradouro: endereco.numLogradouro,
        complemento: endereco.complemento,
        entidade: entidade
      },
      params: {
        id: idEntidade
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
    
    Future<dynamic> cadastrarEndereco(int idEntidade, Endereco endereco, String entidade) async{
        var response = await client.post(
            Uri.parse(baseUri + "/endereco?id=$idEntidade"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                cep: endereco.cep,
                numeroLogradouro: endereco.numLogradouro,
                complemento: endereco.complemento,
                entidade: entidade
            })
        );
    }
}
~~~

#### Responses:
| Status Code |     Significado     |               Por quê?                |
|-------------|:-------------------:|:-------------------------------------:|
| 201         |       CREATED       |         Cadastrou com sucesso         |                 
| 400         |     BAD REQUEST     | Alguma informação foi enviada errada  |               
| 404         |      NOT FOUND      | A entidade buscada não foi encontrada |
| 500         |   INTERNAL SERVER   | A entidade buscada não foi encontrada |
| 503         | SERVICE UNAVAILABLE |   A API ViaCep não está disponivel    |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Alterar um Endereço

O campo cep tem que ser de um endereço já existente, só sendo possível alterar os campos complemento e numero de Logradouro

### Body Put Endereço:
``` JSON
"cep": "11111222",
"numeroLogradouro": 0001,
"complemento": "Apto x bloco y/ casa x",
"entidade": "(cliente ou funcionario ou fornecedor)"
```
###### Se nada for passado em "entidade" será cadastrado para o cliente.

### Parâmetros:
| Key | Tipo | Descrição                                         |
|-----|------|---------------------------------------------------|
| id  | int  | Id da entidade que se deseja atualizar o Endereço |

#### Exemplos:
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/endereco?id=`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function updateEndereco(idEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/endereco",
      headers: {'Content-Type': 'application/json; charset=UTF-8'}
      data: {
        cep: endereco.cep,
        numeroLogradouro: endereco.numLogradouro,
        complemento: endereco.complemento,
        entidade: entidade
      },
      params: {
        id: idEntidade
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
    
    Future<dynamic> updateEndereco(int idEntidade, Endereco endereco, String entidade) async{
        var response = await client.put(
            Uri.parse("$baseUri/endereco?id=$idEntidade"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
              cep: endereco.cep,
              numeroLogradouro: endereco.numLogradouro,
              complemento: endereco.complemento,
              entidade: entidade
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?                |
|-------------|:-----------:|:-------------------------------------:|
| 200         |     OK      |        Atualizado com sucesso         |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada  |               
| 404         |  NOT FOUND  | A entidade buscada não foi encontrada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Remover um Endereço

Possíbilita remover o endereço de uma entidade

### Parâmetros:
| Key |  Tipo  |                    Descrição                    |
|:---:|:------:|:-----------------------------------------------:|
| id  |  int   | Id da entidade que se deseja remover o Endereço |
| cep | String |         Cep do endereço a ser removido          |
|  e  | String |    A entidade que se quer remover o Endereço    |
###### O parâmetro "e" aceita três valores, "cliente", "funcionario" ou "fornecedor", e se nada for passado será removido de um cliente

#### Exemplos:
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/endereco?id=&cep=&e`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function updateEndereco(idEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/endereco",
      headers: {'Content-Type': 'application/json; charset=UTF-8'}
      params: {
        id: idEntidade,
        cep: endereco.cep,
        e: entidade
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
    
    Future<dynamic> updateEndereco(int idEntidade, Endereco endereco, String entidade) async{
        var response = await client.delete(
            Uri.parse("$baseUri/endereco?id=$idEntidade&cep=${endereco.cep}&e=$entidade"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            }
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?                |
|-------------|:-----------:|:-------------------------------------:|
| 200         |     OK      |        Atualizado com sucesso         |            
| 404         |  NOT FOUND  | A entidade buscada não foi encontrada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Produto

### Body Produto:

``` JSON
"nome": "Nome do Produto",
"descricao": "Descrição do Produto",
"Cor": "Qualquer cor",
"valor": 9999.99,
"modelo": "Modelo do Produto",
"peso": 1.0,
"altura": 1.0,
"comprimento": 1.0,
"largura": 1.0,
"fabricante": "Nome do Fabricante",
"fornecedor": "Nome do Fornecedor",
"subCategoria": "Subcategoria do Produto",
"imagemPrincipal": "linkDeUmaImagem",
"imagens": [
     "linkDeUmaImagem",
     "linkDeUmaImagem",
     ...
]
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto`

JavaScript
~~~javascript
import axios from "axios";
const axios = require(axios);

let baseUri = "http://localhost:8080/api";

function cadastrarProduto(produto){
    tratarDados(produto);

    axios.post(baseUri + "/produto",{
        "nome": produto.nome,
        "descricao": produto.descricao,
        "cor": produto.cor,
        "valor": produto.valor,
        "modelo": produto.modelo,
        "peso": produto.peso,
        "altura": produto.altura,
        "comprimento": produto.comprimento,
        "largura": produto.largura,
        "fabricante": produto.fabricante,
        "fornecedor": produto.fornecedor,
        "subCategoria": produto.subCategoria,
        "ImagemPrincipal": produto.imgPrinc,
        "imagens": produto.imagens
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
    
    Future<dynamic> cadastrarProduto(Produto produto) async{
        var response = await client.post(
            Uri.parse(baseUri + "/produto"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            },
            body: jsonEncode({
                "nome": produto.nome,
                "descricao": produto.descricao,
                "cor": produto.cor,
                "valor": produto.valor,
                "modelo": produto.modelo,
                "peso": produto.peso,
                "altura": produto.altura,
                "comprimento": produto.comprimento,
                "largura": produto.largura,
                "fabricante": produto.fabricante,
                "fornecedor": produto.fornecedor,
                "subCategoria": produto.subCategoria,
                "ImagemPrincipal": produto.imgPrinc,
                "imagens": produto.imagens
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?               |
|-------------|:-----------:|:------------------------------------:|
| 201         |   CREATED   |        Cadastrou com sucesso         |                 
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |    
###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/produto`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getProdutos() {
    axios.get(baseUri + "/produto")
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
    
    Future<Produto> getProduto() async{
        var uri = Uri.parse(baseUri + "/produto");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Produto produto = jsonResponse.map((json) => produto.fromJson(json));
        return produto;
    }
}
~~~

#### Responses:
| Status Code | Significado |     Por quê?     |
|-------------|:-----------:|:----------------:|
| 200         |     OK      | Retornou o valor |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/produto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getProdutos(id) {
    axios.get(baseUri + "/produto/${id}")
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
    
    Future<Produto> getProduto(int id) async{
        var uri = Uri.parse(baseUri + "/produto/$id");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Produto produto = jsonResponse.map((json) => Produto.fromJson(json));
        return produto;
    }
}
~~~

#### Responses:
| Status Code | Significado |               Por quê?                |
|-------------|:-----------:|:-------------------------------------:|
| 200         |     OK      |           Retornou o valor            |            
| 404         |  NOT FOUND  | A entidade buscada não foi encontrada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Body Put Produto:

O body do put só precisa ter pelo menos uma campo, se não houver ele mantem os dados que já estavam anteriormente.

``` JSON
"nome": "Nome do Produto",
"descricao": "Descrição do Produto",
"Cor": "Qualquer cor",
"valor": 999,99,
"modelo": "Modelo do Produto",
"peso": 1.0,
"altura": 1.0,
"comprimento": 1.0,
"largura": 1.0,
"fabricante": "Nome do Fabricante",
"fornecedor": "Nome do Fornecedor",
"subCategoria": "Subcategoria do Produto",
"imagemPrincipal": "linkDeUmaImagem",
"imagens": [
     "linkDeUmaImagem",
     "linkDeUmaImagem",
     ...
]
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)  

>`{{baseUri}}/produto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarProduto(produto) {
    axios.put(baseUri + "/produto/${id}", {
        "nome": produto.nome,
        "valor": produto.valor
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
    
    Future<dynamic> alterarProduto(Produto produto) async{
        var response = await client.put(
          Uri.parse("$baseUri/produto/{$produto.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode({
                "nome": produto.nome,
                "valor": produto.valor
          }),
        );
        if(response.statusCode != 200){
          dynamic body = jsonDecode(utf8.decode(response.body.runes.toList()));
          return body;
        }
        return null;
      }
    }
~~~

#### Responses:
| Status Code |   Meaning   |                           Why?                           |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                   Alterou com sucesso                    |
| 209         |  CONFLICT   |         Algum dado único já foi cadastrado antes         |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |          O objeto procurado não foi encontrado           |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

>`{{baseUri}}/produto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirProduto(id) {
    axios.delete(baseUri + "/produto/${id}")
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
    
    Future<dynamic> excluirProduto(int id) async{
        var response = await client.delete(Uri.parse("$baseUri/produto/${id}"));
        if(response.statusCode != 200){
          return "Produto não encontrado";
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |  Meaning  |                 Why?                  |
|-------------|:---------:|:-------------------------------------:|
| 200         |    OK     |     Excluiu o objeto com sucesso      |
| 404         | NOT FOUND | O objeto procurado não foi encontrado |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Favoritar Produtos

### Favoritar:

### Parâmetros:
| Key        |     Tipo      | Descrição                      |
|------------|:-------------:|--------------------------------|
| id_cliente |      int      | Id do cliente                  |
| id_prod    |  UUID/String  | Id do Produto a ser favoritado |

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto/fav?id_cliente=&id_prod=`

JavaScript
~~~javascript
import axios from "axios";
const axios = require(axios);

let baseUri = "http://localhost:8080/api";

function adicionarFavorito(produto, cliente){

    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/produto/fav",
      headers: {'Content-Type': 'application/json; charset=UTF-8'}
      params: {
        id_cliente: cliente.id,
        id_prod: produto.id
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
    
    Future<dynamic> favoritarProduto(Cliente cliente, Produto produto) async{
        var response = await client.post(
            Uri.parse(baseUri + "/produto/fav?id_cliente=${cliente.id}&id_prod=${Produto.id}"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |                Por quê?                 |
|-------------|:-----------:|:---------------------------------------:|
| 201         |   CREATED   |          Cadastrou com sucesso          |                 
| 209         |  CONFLICT   |   Algum dado único já foi cadastrado    |                 
| 400         | BAD REQUEST |  Alguma informação foi enviada errada   |
| 404         |  NOT FOUND  | Alguma das entidades não foi encontrada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Desfavoritar:

### Parâmetros:
| Key        |     Tipo      | Descrição                               |
|------------|:-------------:|-----------------------------------------|
| id_cliente |      int      | Id do cliente                           |
| id_prod    |  UUID/String  | Id do Produto a se retirar o favoritado |

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto/fav?id_cliente=&id_prod=`

JavaScript
~~~javascript
import axios from "axios";
const axios = require(axios);

let baseUri = "http://localhost:8080/api";

function dasfavoritarProduto(produto, cliente){

    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/produto/fav",
      headers: {'Content-Type': 'application/json; charset=UTF-8'}
      params: {
        id_cliente: cliente.id,
        id_prod: produto.id
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
    
    Future<dynamic> desfavoritarProduto(Cliente cliente, Produto produto) async{
        var response = await client.delete(
            Uri.parse(baseUri + "/produto/fav?id_cliente=${cliente.id}&id_prod=${Produto.id}"),
            headers: <String, String>{
                "Content-type": "application/json; charset=UTF-8"
            })
        );
    }
}
~~~

#### Responses:
| Status Code | Significado |                Por quê?                 |
|-------------|:-----------:|:---------------------------------------:|
| 200         |     OK      |          Deletado com sucesso           |
| 400         | BAD REQUEST |  Alguma informação foi enviada errada   |
| 404         |  NOT FOUND  | Alguma das entidades não foi encontrada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Retirar-Desconto

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

>`{{baseUri}}/produto/desconto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function retirarDesconto(id) {
    axios.delete(baseUri + "/produto/desconto/${id}")
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
    
    Future<dynamic> retirarDesconto(int id) async{
        var response = await client.delete(Uri.parse("$baseUri/produto/desconto/${id}"));
        if(response.statusCode != 200){
          return "Produto não encontrado";
        }
        return null;
      }
    }
}
~~~

#### Responses:
| Status Code |  Meaning  |                 Why?                  |
|-------------|:---------:|:-------------------------------------:|
| 200         |    OK     |    Retirou o desconto com sucesso     |
| 404         | NOT FOUND | O objeto procurado não foi encontrado |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---


Nada de importante

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)
