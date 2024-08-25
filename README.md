<div align="center">
    <br />
    <img src="src/main/resources/imagens/Logo-Lemnos-Horizontal-Branco.png" alt="Logo" />
    <hr />
    <img src="https://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge" alt="..." />
    <img src="https://img.shields.io/static/v1?label=Projeto%20de&message=TCC&color=blue&style=for-the-badge" alt="..." />
    <hr />
    <p>
        Servidor para o projeto de TCC do curso de Desenvolvimento de Sistemas na 
        <a href="https://basilides.com.br">Etec Professor Basilides de Godoy</a>, desenvolvida com o ecossistema do 
        <a href="https://spring.io">Framework Spring</a>, em conjunto do <a href="https://lemnos.vercel.app">site</a> 
        utilizando React.
    </p>
    <hr />
</div>

# Súmario

- [Desenvolvedores](#Desenvolvedores)
- [Como Utilizar](#Como-utilizar)
  - [BaseUrl](#-Link-Base-)
- [Como rodar](#Como-Rodar-a-Aplicação)
  - [Docker](#-docker-)
  - [Local](#-Local-)
- [Headers](#Headers)
- [EndPoints](#Endpoints)
  - [Tabela](#Tabela-de-Endpoints)
  - [Swagger](#Swagger)
- [Erros](#Erros)

---

# Desenvolvedores

<div align="center">
  <a href="https://github.com/lucas7silva"><img style="margin: 0 1% 0 1%" src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/141294696?v=4&h=100&w=100&fit=cover&mask=circle&maxage=1y&trim=10" width="100" alt="Lucas Rocha"></a>
  <a href="https://github.com/lucasatdriano"><img style="margin: 0 1% 0 1%" src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/134859091?v=4&h=100&w=100&fit=cover&mask=circle&maxage=1y&trim=10" width="100" alt="Lucas Adriano"></a>
  <a href="https://github.com/lucasbonato"><img style="margin: 0 1% 0 1%" src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/95046421?v=4&h=100&w=100&fit=cover&mask=circle&maxage=1y&trim=10" alt="Lucas Bonato"></a>
  <a href="https://github.com/leankk"><img style="margin: 0 1% 0 1%" src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/108305442?v=4&h=100&w=100&fit=cover&mask=circle&maxage=1y&trim=10" width="100" alt="Leankk"></a>
  <a href="https://github.com/gi-colucci"><img style="margin: 0 1% 0 1%" src="https://images.weserv.nl/?url=avatars.githubusercontent.com/u/138801921?v=4&h=100&w=100&fit=cover&mask=circle&maxage=1y&trim=10" width="100" alt="Giovanna Colucci"></a>
</div>

---

# Como utilizar

API criada com o intuito de facilitar o trabalho na criação do e-commerce [Lemnos](https://lemnos.vercel.app).

## 🔗 Link base 🔗

### _Url Base_: `https://lemnos-server.onrender.com/api`

---

# Como Rodar a Aplicação

## 🐳 Docker 🐳

### 1. Clone o repositório
~~~bash
  git clone https://github.com/LucasBonato/Lemnos-Server.git
~~~

### 2. Vá até o diretorio do projeto
~~~bash
  cd Lemnos-Server
~~~

### 3. Crie um arquivo .env
~~~bash
  # Comando no Linux
  touch .env
  
  # Comando no Windows
  echo. > .env
~~~

### 4. Defina as variáveis de ambiente necessárias no .env
~~~bash
  FIREBASE_CREDENTIALS= 
  POSTGRES_USERNAME= 
  POSTGRES_PASSWORD= 
  DB_NAME=
  DB_HOST=
  DB_PORT=
~~~

### 5. Suba os containers
~~~bash
  docker-compose up
~~~

## 💻 Local 💻

### 1. Clone o repositório
~~~bash
  git clone https://github.com/LucasBonato/Lemnos-Server.git
~~~

### 2. Vá até o diretorio do projeto
~~~bash
  cd Lemnos-Server
~~~

### 3. Vá até o arquivo `script_bar.bat` e defina as variáveis de ambiente
~~~shell
  # ...
  echo Setting environment variables...
  set POSTGRES_USERNAME=
  set POSTGRES_PASSWORD=
  set FIREBASE_CREDENTIALS=
  set DB_HOST=
  set DB_PORT=
  set DB_NAME=
  
  echo Building the project...
  # ...
~~~

### 4. Execute o `script_bar.bat`
~~~bash
  # bar significa "Build and Run"
  # Comando no Linux
  ./script_bar.bat
  
  # Comando no Windows
  script_bar
~~~

### 5. Se quiser limpar o executavel, execute o `script_sa.bat`
~~~bash
  # sa significa "Stop Application"
  # Comando no Linux
  ./script_sa.bat
  
  # Comando no Windows
  script_sa
~~~

# Headers

|    Headers    |           Description            |
|:-------------:|:--------------------------------:|
| Content-Type  | application/json; charset=UTF-8  |
| Authorization |          {Access Token}          |

---

# Endpoints

##  Tabela de endpoints

| **EndPoints** | **Sub Endpoints**                                                                                                      |                                         Descrição                                          |
|---------------|------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------:|
| /auth         | /login<br/>/login-firebase<br/>/register<br/>/register/funcionario<br/>/register/fornecedor<br/>/register/**/verificar |            Permite o login e registro de clientes, funcionários e fornecedores             |
| /cliente      | /find?email=<br/>/endereco                                                                                             |            Possui a forma de conseguir procurar clientes, alterar ou desativar             |
| /funcionario  | /find?email=<br/>/endereco<br/>/by?nome=                                                                               |          Possui a forma de conseguir procurar funcionários, alterar ou desativar           |
| /fornecedor   | /find?email=<br/>/endereco<br/>/by?nome=                                                                               |          Possui a forma de conseguir procurar fornecedores, alterar ou desativar           |
| /endereco     | /verificar                                                                                                             |       Possui a forma de cadastrar, atualizar ou remover um endereço de uma entidade        |
| /produto      | /discount<br/>/{id}<br/>/find<br/>/fav<br/>/desconto/{id}<br/>/avaliar/{id}                                            | Possui a forma de conseguir procurar produtos, alterar, deletar, favoritar ou desfavoritar |
| /carrinho     | /quantidade                                                                                                            |                    Permite a criação, inserção e remoção de um carrinho                    |
| /pedido       | ?email=<br/>/{id}                                                                                                      |        Permite a criação de um novo pedido, visualizar os pedidos e alterar status         |

## Swagger

### _Endpoint_: `https://lemnos-server.onrender.com/api/swagger`

---

# Erros

~~~ JSON
{
    "id": 0
    "error": "Mensagem de erro."
}
~~~

## Tabela de Erros

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