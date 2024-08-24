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

# Súmario

- [Como Utilizar](#Como-utilizar-a-API)
- [Headers](#Headers)
- [EndPoints](#Endpoints)
- [Erros](#Erros)
- [Exemplos](#Exemplos)
  - [Auth](#Auth)
    - [Verificação](#Verificar-Registro)
  - [Cliente](#Cliente)
  - [Funcionário](#Funcionário)
  - [Fornecedor](#Fornecedor)
  - [Endereço](#Endereço)
    - [Verificação](#Verificar-Endereço)
  - [Produto](#Produto)
  - [Carrinho](#Carrinho)
  - [Pedido](#Pedido)

# Como utilizar a API

API criada nesse projeto vem com a ideia de facilitar o trabalho na criação do e-commerce Lemnos, possuindo formas 
de cadastro de Clientes e Funcionários, gerenciamento, listagem e verificação de produtos, tratamento e 
gerenciamento de carrinho.


### _Url Base_: `https://lemnos-server.up.railway.app/api`

# Headers

|    Headers    | Description                     |
|:-------------:|:--------------------------------|
| Content-Type  | application/json; charset=UTF-8 |
| Authorization | token                           |

---

# Endpoints

| **EndPoints** | **Sub Endpoints**                                                                                            | **Exemplos**                                                                                                                                                                                                                  | **Body**                                                                                                                   |                                         Descrição                                          |
|---------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------:|
| /auth         | /login<br/>/login-firebase<br/>/register<br/>/register/funcionario<br/>/register/fornecedor<br/>**/verificar | [login](#Login)<br/>[login google](#Login-Google)<br/>[register](#Registrar-Cliente)<br/>[funcionario](#body-funcionario)<br/>[fornecedor](#body-fornecedor)<br/>[verificação](#Verificar-Registro)                           | [login](#body-login)<br/>[register](#body-cliente)<br/>[funcionario](#body-funcionario)<br/>[fornecedor](#body-fornecedor) |            Permite o login e registro de clientes, funcionários e fornecedores             |
| /cliente      | /find?email=<br/>/endereco                                                                                   | [cliente](#Cliente)<br/>[endereço](#Endereço)                                                                                                                                                                                 | [Cliente](#body-put-cliente)                                                                                               |            Possui a forma de conseguir procurar clientes, alterar ou desativar             |
| /funcionario  | /find?email=<br/>/endereco<br/>/by?nome=                                                                     | [funcionario](#Funcionário)<br/>[endereço](#Endereço)                                                                                                                                                                         | [Funcionário](#body-put-funcionário)                                                                                       |          Possui a forma de conseguir procurar funcionários, alterar ou desativar           |
| /fornecedor   | /find?email=<br/>/endereco<br/>/by?nome=                                                                     | [fornecedor](#Fornecedor)<br/>[endereço](#Endereço)                                                                                                                                                                           | [Fornecedor](#body-put-fornecedor)                                                                                         |          Possui a forma de conseguir procurar fornecedores, alterar ou desativar           |
| /endereco     | /verificar                                                                                                   | [endereco](#Endereço)<br/>[verificação](#Verificar-Endereço)                                                                                                                                                                  | [Endereço](#body-endereço)                                                                                                 |       Possui a forma de cadastrar, atualizar ou remover um endereço de uma entidade        |
| /produto      | /discount<br/>/{id}<br/>/find<br/>/fav<br/>/desconto/{id}<br/>/avaliar/{id}                                  | [produto](#body-produto)<br/>[desconto](#Produtos-Com-Desconto)<br/>[filtro](#Filtro-Produto)<br/>[favoritar](#Favoritar)<br/>[desfavoritar](#Desfavoritar)<br/>[desconto](#Retirar-Desconto)<br/>[avaliar](#Avaliar-Produto) | [Produto](#produto)                                                                                                        | Possui a forma de conseguir procurar produtos, alterar, deletar, favoritar ou desfavoritar |
| /carrinho     | /quantidade                                                                                                  | [carrinho](#Carrinho)<br/>[quantidade](#Quantidade-de-itens)                                                                                                                                                                  | [Carrinho](#Carrinho-Body)                                                                                                 |                    Permite a criação, inserção e remoção de um carrinho                    |
| /pedido       | ?email=<br/>/{id}                                                                                            | [pedido](#Pedido)                                                                                                                                                                                                             | [Pedido](#Pedido-Body)                                                                                                     |        Permite a criação de um novo pedido, visualizar os pedidos e alterar status         |

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
| 29 |             Avaliação             |
| 30 |             Favorito              |
| 31 |             Carrinho              |
| 32 |              Pedido               |
---

# Exemplos

## Auth

### Registrar Cliente

Insere um único Cadastro por vez, ou de Cliente, Funcionario ou Fornecedor, sendo que todas as requisições são de *POST*

#### Body Cliente:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
"email": "emailDeSuaEscolha@email.com",
"senha": "SenhaDoCliente"
```
##### Verificar Registro:
###### Se quiser verificar se está tudo correto sem cadastrar uma entidade diretamente é só utitlizar o mesmo corpo, mas com "/verificar" depois do endpoint, se der 200 está tudo correto e pode cadastrar.

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
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
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
| 209         |  CONFLICT   |  Algum dado único já foi cadastrado  |                 
| 400         | BAD REQUEST | Alguma informação foi enviada errada |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Login

Quando logado a API irá retornar um token para ser utlizado posteriormente no [Header](#Headers) Authorization.

#### Body Login
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
      url: "/auth/login",
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
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
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

### Login Google

Quando logado a API irá retornar um token para ser utlizado posteriormente no [Header](#Headers) Authorization.
Aqui será necessário enviar o accessToken devolvido pelo firebase ao endpoint

#### Body Login
``` JSON
"token": "acessTokenGoogle"
```

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/auth/login-firebase`

JavaScript
~~~ javascript
let baseUri = "https://localhost:8080/api";

import axios from 'axios';
const axios = require("axios");

function loginGoogle() {
    
    let token = "";
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/auth/login-firebase",
      headers: {'Content-Type': 'application/json; charset=UTF-8'},
      body: {
        token: localStorage.getItem('authTokenGoogle')
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
    
    static String googleToken;
    
    Future<String> loginGoogle() async{
        String token = "";
      
        var response = await client.post(
            Uri.parse(baseUri + "/auth/login-firebase"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
            },
            body: jsonEncode({
                "email": googleToken 
            })
        );
        
        token = response.body;
        return token;
    }
}
~~~

#### Responses:
| Status Code | Significado  |               Por quê?               |
|-------------|:------------:|:------------------------------------:|
| 200         |      OK      |          Logado com sucesso          |                 
| 400         | BAD REQUEST  | Alguma informação foi enviada errada |             
| 401         | UNAUTHORIZED |       O token enviado expirou        |     

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Registrar um Funcionário

#### Body Funcionario:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233344",
"telefone": "11400289221",
"dataNascimento": "01/01/0001",
"dataAdmissao": "01/01/0001",
"email": "emailDeSuaEscolha@email.com",
"senha": "SenhaDeSuaEscolha"
```

##### Verificar Registro:
###### Se quiser verificar se está tudo correto sem cadastrar uma entidade diretamente é só utitlizar o mesmo corpo, mas com "/verificar" depois do endpoint, se der 200 está tudo correto e pode cadastrar.

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
| Status Code |   Meaning    |                 Why?                 |
|-------------|:------------:|:------------------------------------:|
| 200         |      OK      |        Cadastrou com sucesso         |                 
| 209         |   CONFLICT   |  Algum dado único já foi cadastrado  |                 
| 400         | BAD REQUEST  | Alguma informação foi enviada errada |
| 401         | UNAUTHORIZED |         Não foi autenticado          |
| 403         |  FORBIDDEN   |         Não possui permissão         |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Registrar Fornecedor

#### Body Fornecedor:
``` JSON
"nome": "Nome Do Fornecedor",
"cnpj": "09836719874612",
"telefone": "11999999999",
"numeroLogradouro": 0001,
"email": "emailDoFornecedor@gmail.com"
```

##### Verificar Registro:
###### Se quiser verificar se está tudo correto sem cadastrar uma entidade diretamente é só utitlizar o mesmo corpo, mas com "/verificar" depois do endpoint, se der 200 está tudo correto e pode cadastrar.

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
| Status Code |   Meaning    |                 Why?                 |
|-------------|:------------:|:------------------------------------:|
| 200         |      OK      |        Cadastrou com sucesso         |
| 209         |   CONFLICT   |  Algum dado único já foi cadastrado  |
| 400         | BAD REQUEST  | Alguma informação foi enviada errada |
| 401         | UNAUTHORIZED |         Não foi autenticado          |
| 403         |  FORBIDDEN   |         Não possui permissão         |

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
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
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

> `{{baseUri}}/cliente/find`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getCliente(email) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/cliente/find`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
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

#### Body Put Cliente:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
```

### Parâmetros:
| Key   | Tipo   | Descrição                                              |
|-------|--------|--------------------------------------------------------|
| email | String | Email da entidade que se deseja alterar as informações |

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/cliente`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarCliente(cliente) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: `/cliente`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
      data: {
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

### Parâmetros:
| Key   | Tipo   | Descrição                                               |
|-------|--------|---------------------------------------------------------|
| email | String | Email da entidade que se deseja inativar as informações |

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/cliente`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirCliente(email) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: `/cliente`,
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
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
      url: "/funcionario"
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

### Parâmetros:
| Key   | Tipo   | Descrição                                            |
|-------|--------|------------------------------------------------------|
| email | String | Email da entidade que se deseja pegar as informações |

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/funcionario/find?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFuncionario(funcionario) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/funcionario/find`,
      params: {
        email: funcionario.email
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

### Parâmetros:
| Key  | Tipo   | Descrição                                     |
|------|--------|-----------------------------------------------|
| nome | String | Nome da entidade que se deseja pegar os nomes |

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/funcionario/by`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFuncionariosByNome(filtro) {
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/funcionario/by",
      data: {
        nome: filtro.nome
      },
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
    
    Future<Produto> getProdutoFilter(FuncionarioFiltro filtro) async{
        var uri = Uri.parse(baseUri + "/funcionario/by");
        var response = await client.post(uri, 
        body: jsonEnconde({
          "nome": filtro.nome
        }));
}
~~~

#### Responses:
| Status Code |   Meaning   |                 Why?                 |
|-------------|:-----------:|:------------------------------------:|
| 200         |     OK      |         Retornou os valores          |                  

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

#### Body Put Funcionário:
``` JSON
"nome": "Qualquer Nome",
"cpf": "11122233311",
"dataNascimento": "01/01/0001",
"dataAdmissao": "01/01/0001",
"telefone": "11912345678"
```

### Parâmetros:
| Key   | Tipo   | Descrição                               |
|-------|--------|-----------------------------------------|
| email | String | Email da entidade que se deseja alterar |

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/funcionario?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarFuncionario(funcionario) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: `/funcionario`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        nome: funcionario.nome,
        cpf: funcionario.cpf,
        dataNascimento: funcionario.dtNasc,
        dataAdmissao: funcionario.dtAdmi,
        telefone: funcionario.telefone
      },
      params: {
        email: funcionario.email
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
            'Authorization': token
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

### Parâmetros:
| Key   | Tipo   | Descrição                                               |
|-------|--------|---------------------------------------------------------|
| email | String | email da entidade que se deseja inativar                |

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/funcionario?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirFuncionario(email) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: `/funcionario`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      params: {
        email: email
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
      url: "/fornecedor"
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

### Parâmetros:
| Key   | Tipo   | Descrição                                            |
|-------|--------|------------------------------------------------------|
| email | String | Email da entidade que se deseja pegar as informações |

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/fornecedor/find?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFornecedor(email) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/fornecedor/find`,
      params: {
        email: email
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

### Parâmetros:
| Key  | Tipo   | Descrição                                     |
|------|--------|-----------------------------------------------|
| nome | String | Nome da entidade que se deseja pegar os nomes |

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/fornecedor/by?nome=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getFornecedoresByNome(nome) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/fornecedor/by",
      params: {
        nome: nome
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
    
    Future<List<Fornecedor>> getFornecedoresByNome(String nome) async{
        var uri = Uri.parse(baseUri + "/fornecedor/by?nome=${nome}");
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

#### Body Put Fornecedor:
``` JSON
"nome": "Qualquer Nome",
"cnpj": "11222333444455",
"telefone": "11912345678"
```

### Parâmetros:
| Key   | Tipo   | Descrição                                              |
|-------|--------|--------------------------------------------------------|
| email | String | Email da entidade que se deseja alterar as informações |

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/fornecedor/find?email`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarFornecedor(fornecedor) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: `/fornecedor/find`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        nome: fornecedor.nome,
        cnpj: fornecedor.cnpj,
        telefone: fornecedor.telefone
      },
      params: {
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
    
    Future<dynamic> alterarFornecedor(Fornecedor fornecedor) async{
        var response = await client.put(
          Uri.parse("$baseUri/fornecedor/${fornecedor.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
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

### Parâmetros:
| Key   | Tipo   | Descrição                                              |
|-------|--------|--------------------------------------------------------|
| email | String | Email da entidade que se deseja inativar               |

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/fornecedor?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function excluirFornecedor(email) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: `/fornecedor`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      params: {
        email: email
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
        var response = await client.delete(Uri.parse("$baseUri/fornecedor/${id}", 
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
            },
        ));
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

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/endereco`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getEndereco(endereco) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/endereco",
      params: {
        cep: endereco.cep
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
    
    Future<Endereco> getEndereco(String cep) async{
        var uri = Uri.parse(baseUri + "/endereco?cep=$cep");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes);
        var jsonResponse = json.decode(responseBodyUtf8);
        Endereco endereco = jsonResponse.map((json) => Endereco.fromJson(json));
        return endereco;
    }
}
~~~

#### Responses:
| Status Code |       Meaning       |                 Why?                  |
|-------------|:-------------------:|:-------------------------------------:|
| 200         |         OK          |          Retornou os valores          |
| 404         |      NOT FOUND      | A entidade buscada não foi encontrada |
| 500         |   INTERNAL SERVER   | A entidade buscada não foi encontrada |
| 503         | SERVICE UNAVAILABLE |   A API ViaCep não está disponivel    |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

#### Body Endereço:
``` JSON
"email": "emailDaEntidade@email.com"
"cep": "11111222",
"numeroLogradouro": 0001,
"complemento": "Apto x bloco y/ casa x",
"entidade": "(cliente ou funcionario ou fornecedor)"
```
###### Se nada for passado em "entidade" será cadastrado para o cliente.
##### Verificar Endereço:
###### Se quiser verificar se está tudo correto sem cadastrar em uma entidade é só utitlizar o mesmo e parâmetros, mas com "/verificar" depois do "/endereço", se der 200 está tudo correto e pode cadastrar.

#### Exemplos:
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/endereco`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function cadastrarEndereco(emailEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/endereco",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        email: emailEntidade
        cep: endereco.cep,
        numeroLogradouro: endereco.numLogradouro,
        complemento: endereco.complemento,
        entidade: entidade
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
    
    Future<dynamic> cadastrarEndereco(String emailEntidade, Endereco endereco, String entidade) async{
        var response = await client.post(
            Uri.parse(baseUri + "/endereco"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
            },
            body: jsonEncode({
                email: emailEntidade,
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

### Alterar um Endereço

O campo cep tem que ser de um endereço já existente, só sendo possível alterar os campos complemento e numero de Logradouro

#### Body Put Endereço:
``` JSON
"email": "emailDaEntidade@email.com"
"cep": "11111222",
"numeroLogradouro": 0001,
"complemento": "Apto x bloco y/ casa x",
"entidade": "(cliente ou funcionario ou fornecedor)"
```
###### Se nada for passado em "entidade" será cadastrado para o cliente.

#### Exemplos:
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/endereco`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function updateEndereco(emailEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/endereco",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        email: emailEntidade
        cep: endereco.cep,
        numeroLogradouro: endereco.numLogradouro,
        complemento: endereco.complemento,
        entidade: entidade
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
    
    Future<dynamic> updateEndereco(String emailEntidade, Endereco endereco, String entidade) async{
        var response = await client.put(
            Uri.parse("$baseUri/endereco"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
            },
            body: jsonEncode({
              email: emailEntidade
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

### Remover um Endereço

Possíbilita remover o endereço de uma entidade

### Parâmetros:
|  Key  |  Tipo  |                     Descrição                      |
|:-----:|:------:|:--------------------------------------------------:|
| email | String | email da entidade que se deseja remover o Endereço |
|  cep  | String |           Cep do endereço a ser removido           |
|   e   | String |     A entidade que se quer remover o Endereço      |
###### O parâmetro "e" aceita três valores, "cliente", "funcionario" ou "fornecedor", e se nada for passado será removido de um cliente

#### Exemplos:
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/endereco?email=&cep=&e`

JavaScript
~~~ javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function updateEndereco(emailEntidade, endereco, entidade) {
    
    endereco = tratarDados(endereco);
    
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/endereco",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      params: {
        email: emailEntidade,
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
    
    Future<dynamic> updateEndereco(String emailEntidade, Endereco endereco, String entidade) async{
        var response = await client.delete(
            Uri.parse("$baseUri/endereco?email=${emailEntidade}&cep=${endereco.cep}&e=${entidade}"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
            },
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

#### Body Produto:

``` JSON
"nome": "Nome do Produto",
"descricao": "Descrição do Produto",
"cor": "Qualquer cor",
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
    
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/produto",
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        nome: produto.nome,
        descricao: produto.descricao,
        cor: produto.cor,
        valor: produto.valor,
        modelo: produto.modelo,
        peso: produto.peso,
        altura: produto.altura,
        comprimento: produto.comprimento,
        largura: produto.largura,
        fabricante: produto.fabricante,
        fornecedor: produto.fornecedor,
        subCategoria: produto.subCategoria,
        desconto: produto.desconto, //Opcional
        imagemPrincipal: produto.imgPrinc,
        imagens: produto.imagens
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
    
    Future<dynamic> cadastrarProduto(Produto produto) async{
        var response = await client.post(
            Uri.parse(baseUri + "/produto"),
            headers: <String, String>{
              'Content-Type': 'application/json; charset=UTF-8',
              'Authorization': token
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
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/produto"
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

### Produto com Desconto

Neste endpoint é possível pegar só os produtos que possuem desconto.

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/produto/discount`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getProdutos() {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/produto/discount"
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
    
    Future<Produto> getProduto() async{
        var uri = Uri.parse(baseUri + "/produto/discount");
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

### Filtro Produto

Nenhum dos campos são obrigatórios, todos são opcionais, sendo que o único que é em conjunto são os de menor e maior Preço, se passado só o maiorPreço o menorPreço será 0 por padrão, o contrário não acontecerá.

``` JSON
  "nome": ""
  "categoria": "",
  "subCategoria": "",
  "marca": "",
  "menorPreco": 0.0,
  "maiorPreco": 0.0,
  "page": 0 // Número da página, padrão é 0
  "size": 10 // Quantidade de itens na página, padrão é 10
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto/find`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getProdutosFilter(filtro) {
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/produto/find",
      data: {
        nome: filtro.nome
        categoria: filtro.categoria,
        subCategoria: filtro.subCategoria,
        marca: filtro.marca,
        menorPreco: filtro.menorPreco,
        maiorPreco: filtro.maiorPreco,
        page: filtro.page,
        size: filtro.size
      },
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
    
    Future<Produto> getProdutoFilter(ProdutoFiltro filtro) async{
        var uri = Uri.parse(baseUri + "/produto/find");
        var response = await client.get(uri, 
        body: jsonEnconde({
          "nome": filtro.nome,
          "categoria": filtro.categoria,
          "subCategoria": filtro.subCategoria,
          "marca": filtro.marca,
          "menorPreco": filtro.menorPreco,
          "maiorPreco": filtro.maiorPreco,
          "page": filtro.page,
          "size": filtro.size
        }));
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        List<dynamic> jsonResponse = json.decode(responseBodyUtf8);
        List<Produto> produtos = jsonResponse.map((json) => produto.fromJson(json)).toList();
        return produtos;
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
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/produto/${id}`
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

#### Body Put Produto:

O body do put só precisa ter pelo menos uma campo, se não houver ele mantem os dados que já estavam anteriormente.

``` JSON
"nome": "Nome do Produto",
"descricao": "Descrição do Produto",
"cor": "Qualquer cor",
"valor": 999,99,
"modelo": "Modelo do Produto",
"peso": 1.0,
"altura": 1.0,
"comprimento": 1.0,
"largura": 1.0,
"fabricante": "Nome do Fabricante",
"fornecedor": "Nome do Fornecedor",
"subCategoria": "Subcategoria do Produto"
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)  

>`{{baseUri}}/produto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function alterarProduto(produto, id) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: `/produto/${id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        nome: produto.nome,
        valor: produto.valor
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
    
    Future<dynamic> alterarProduto(Produto produto, int id) async{
        var response = await client.put(
          Uri.parse("$baseUri/produto/${id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
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
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: `/produto/${id}`,
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

### Favoritar Produtos

### Favoritar:

### Parâmetros:
| Key     |    Tipo     | Descrição                      |
|---------|:-----------:|--------------------------------|
| email   |   String    | email do cliente               |
| id_prod | UUID/String | Id do Produto a ser favoritado |

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto/fav?email=&id_prod=`

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
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      params: {
        email: cliente.email,
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
            Uri.parse(baseUri + "/produto/fav?email=${cliente.email}&id_prod=${Produto.id}"),
            headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
          },)
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

### Pegar os favoritos

### Parâmetros:
| Key     |    Tipo     | Descrição                      |
|---------|:-----------:|--------------------------------|
| email   |   String    | email do cliente               |

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/produto/fav?email=`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getProdutosFavoritos(email) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/produto/fav`,
      params: {
        email: email
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
    
    Future<List<String>> getProdutosFavoritos(String email) async{
        var uri = Uri.parse(baseUri + "/produto/fav?email=${email}");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        List<String> ids = jsonResponse.map((json) => String.fromJson(json));
        return ids;
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

### Desfavoritar:

### Parâmetros:
| Key     |    Tipo     | Descrição                               |
|---------|:-----------:|-----------------------------------------|
| email   |   String    | email do cliente                        |
| id_prod | UUID/String | Id do Produto a se retirar o favoritado |

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/produto/fav?email=&id_prod=`

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
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      params: {
        email: cliente.email,
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
            Uri.parse(baseUri + "/produto/fav?email=${cliente.email}&id_prod=${Produto.id}"),
            headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
          },)
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

### Retirar Desconto

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

>`{{baseUri}}/produto/desconto/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function retirarDesconto(id) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: `/produto/desconto/${id}`,
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

### Avaliar Produto:

``` JSON
"avaliacao": 1.0
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

>`{{baseUri}}/produto/avaliar/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function avaliarProduto(produto, valorAvaliacao) {
   axios({
      baseURL: baseUri,
      method: "POST",
      url: `/produto/avaliar/${produto.id}`,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': token
      },
      data: {
        avaliacao: valorAvaliacao,
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
    
    Future<dynamic> alterarProduto(Produto produto, double valorAvaliacao) async{
        var response = await client.post(
          Uri.parse("$baseUri/produto/avaliacao/{$produto.id}"),
          headers: <String, String>{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
          },
          body: jsonEncode({
                "avaliacao": valorAvaliacao
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
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |          O objeto procurado não foi encontrado           |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Carrinho

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/carrinho`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getCarrinho() {
    axios({
      baseURL: baseUri,
      method: "GET",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
      url: "/carrinho"
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
    
    Future<Carrinho> getCarrinho() async{
        var uri = Uri.parse(baseUri + "/carrinho");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Carrinho carrinho = jsonResponse.map((json) => Carrinho.fromJson(json));
        return carrinho;
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Quantidade de itens

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/carrinho/quantidade`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getCarrinho() {
    axios({
      baseURL: baseUri,
      method: "GET",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
      url: "/carrinho/quantidade"
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
    
    Future<Carrinho> getCarrinho() async{
        var uri = Uri.parse(baseUri + "/carrinho/quantidade");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Carrinho carrinho = jsonResponse.map((json) => Carrinho.fromJson(json));
        return carrinho;
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Carrinho Body:

``` JSON
"id": "idDoProdutoASeInserirNoCarrinho",
"quantidade": 1 // Se não for passado será 1 por padrão
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/carrinho`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function adicionarProdutoCarrinho(produto, qntd) {
    axios({
      baseURL: baseUri,
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
      url: "/carrinho",
      data: {
        id: produto.id,
        quantidade: qntd
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
    
    Future<Void> adicionarProdutoCarrinho(Entidade entidade, Produto produto, int qntd) async{
        var uri = Uri.parse(baseUri + "/carrinho");
        var response = await client.post(uri, body: jsonEncode({
                "id": produto.id,
                "email": cliente.email,
                "quantidade": qntd
            }));
        
        if(response.statusCode != 200) {
          return null;
        }
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Cadastrou o item                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Carrinho Body Delete:

Remove um tipo de produto ou certa quantidade desse produto

``` JSON
"id": "idDoProdutoASeRetirarDoCarrinho",
"quantidade": 1 // Se não for passado será 1 por padrão
```

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/carrinho`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function removerProdutoCarrinho(produto, qntd) {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/carrinho",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
      data: {
        id: produto.id,
        quantidade: qntd
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
    
    Future<Void> removerProdutoCarrinho(Entidade entidade, Produto produto, int qntd) async{
        var uri = Uri.parse(baseUri + "/carrinho");
        var response = await client.delete(uri, body: jsonEncode({
                "id": produto.id,
                "quantidade": qntd
            }));
        
        if(response.statusCode != 200) {
          return null;
        }
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Deu certo a ação                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

Com essa requisição o carrinho será deletado por completo

![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)

> `{{baseUri}}/carrinho/tudo`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function apagarCarrinho() {
    axios({
      baseURL: baseUri,
      method: "DELETE",
      url: "/carrinho/tudo"
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
        "Authorization": token
      },
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
    
    Future<Void> apagarCarrinho() async{
        var uri = Uri.parse(baseUri + "/carrinho/tudo");
        var response = await client.delete(uri);
        
        if(response.statusCode != 200) {
          return null;
        }
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Deu certo a ação                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

## Pedido

### Lista de Pedidos

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)

> `{{baseUri}}/pedido`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getPedidos(email) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: "/pedido",
      headers: {
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
    
    Future<List<Pedido>> getPedidos(email) async{
        var uri = Uri.parse(baseUri + "/pedido?email=${email}");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        List<Pedido> pedidos = jsonResponse.map((json) => Pedido.fromJson(json)).toList();
        return pedidos;
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Pedido especifico

> `{{baseUri}}/pedido/{id}`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function getPedido(pedido) {
    axios({
      baseURL: baseUri,
      method: "GET",
      url: `/pedido/${pedido.id}`
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
    
    Future<Pedido> getPedido(Pedido pedido) async{
        var uri = Uri.parse(baseUri + "/pedido/${pedido.id}");
        var response = await client.get(uri);
    
        var responseBodyUtf8 = utf8.decode(response.body.runes.toList());
        dynamic jsonResponse = json.decode(responseBodyUtf8);
        Pedido pedido = jsonResponse.map((json) => Pedido.fromJson(json));
        return pedido;
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Retornou o valor                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Novo Pedido

### Pedido Body:

``` JSON
"metodoPagamento": "",
"valorPagamento": 1.0,
"valorFrete": 1.0
```

![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)

> `{{baseUri}}/pedido`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function novoPedido(pedido, cliente) {
    axios({
      baseURL: baseUri,
      method: "POST",
      url: "/pedido",
      headers: {
        'Authorization': token
      },
      data: {
        metodoPagamento: pedido.metodoPagamento,
        valorPagamento: pedido.valor,
        valorFrete: pedido.frete
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
    
    Future<Void> novoPedido(Pedido pedido, Cliente cliente) async{
        var uri = Uri.parse(baseUri + "/pedido");
        var response = await client.post(uri, body: jsonEncode({
                "email": cliente.email,
                "metodoPagamento": pedido.email
            }));
        
        if(response.statusCode != 200) {
          return null;
        }
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Cadastrou o item                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

### Atualizar Status do Pedido

### Pedido Body:

``` JSON
"id": 0 // Id do Pedido
```

![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/pedido`

JavaScript
~~~javascript
import axios from 'axios';
const axios = require("axios");

let baseUri = "https://localhost:8080/api";

function atualizarStatus(pedido, cliente) {
    axios({
      baseURL: baseUri,
      method: "PUT",
      url: "/pedido",
      data: {
        id: pedido.id
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
    
    Future<Void> atualizarStatus(Pedido pedido, Cliente cliente) async{
        var uri = Uri.parse(baseUri + "/pedido");
        var response = await client.put(uri, body: jsonEncode({
                "email": cliente.email,
                "id": pedido.id
            }));
        
        if(response.statusCode != 200) {
          return null;
        }
    }
}
~~~

#### Responses:
| Status Code | Significado |                         Por quê?                         |
|-------------|:-----------:|:--------------------------------------------------------:|
| 200         |     OK      |                     Cadastrou o item                     |
| 400         | BAD REQUEST | Alguma informação foi enviada errada ou falta informação |
| 404         |  NOT FOUND  |    A entidade do objeto procurado não foi encontrada     |

###### Alguma Dúvida sobre o corpo de um erro? [Erros](#Erros)

---

Props:

![GET](https://img.shields.io/static/v1?label=&message=GET&color=&style=for-the-badge)
![POST](https://img.shields.io/static/v1?label=&message=POST&color=yellow&style=for-the-badge)
![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)
![DELETE](https://img.shields.io/static/v1?label=&message=DEL&color=red&style=for-the-badge)