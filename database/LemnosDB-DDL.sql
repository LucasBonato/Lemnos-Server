CREATE DATABASE "LemnosDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE Cadastro (
    Id SERIAL PRIMARY KEY,
    Email varchar(40) UNIQUE NOT NULL,
    Senha varchar(16) NOT NULL,
    CHECK(LENGTH(Senha) > 7)
);
CREATE TABLE Cliente (
    Id SERIAL PRIMARY KEY,
    Nome varchar(40) NOT NULL,
    CPF numeric(11) UNIQUE NOT NULL,
    Situacao varchar(7) NOT NULL,
    Id_Cadastro int,
    CONSTRAINT fk_cliente_cadastro FOREIGN KEY(Id_Cadastro) REFERENCES Cadastro(Id),
    CHECK(LENGTH(Nome) > 2)
);
CREATE TABLE Funcionario (
    Id SERIAL PRIMARY KEY,
    Nome varchar(40) NOT NULL,
    CPF numeric(11) UNIQUE NOT NULL,
    Data_Nascimento date NOT NULL,
    Data_Admissao date NOT NULL,
    Telefone numeric(11),
    Situacao varchar(7) NOT NULL,
    Id_Cadastro int,
    CONSTRAINT fk_funcionario_cadastro FOREIGN KEY(Id_Cadastro) REFERENCES Cadastro(Id),
    CHECK(LENGTH(Nome) > 2)
);
CREATE TABLE Carrinho (
    Id SERIAL PRIMARY KEY,
    Valor numeric(10, 2) NOT NULL,
    Quantidade_Produtos int NOT NULL,
    Id_Cadastro int,
    CONSTRAINT fk_carrinho_cadastro FOREIGN KEY(Id_Cadastro) REFERENCES Cadastro(Id),
    CHECK(Valor > 0 AND Quantidade_Produtos > -1)
);
CREATE TABLE Pedido (
    Id SERIAL PRIMARY KEY,
    Valor_Pedido numeric(10, 2) NOT NULL,
    Metodo_Pagamento varchar(20) NOT NULL,
    Data_Pedido date NOT NULL,
    Valor_Pagamento numeric(10, 2) NOT NULL,
    Quantidade_Produtos int NOT NULL,
    Data_Pagamento date NOT NULL,
    Descricao varchar(255) NOT NULL,
    Id_Carrinho int,
    CONSTRAINT fk_pedido_carrinho FOREIGN KEY(Id_Carrinho) REFERENCES Carrinho(Id),
    CHECK(Valor_Pedido > 0 AND Valor_Pagamento > 0 AND Quantidade_Produtos > -1)
);
CREATE TABLE Imagem (
    Id SERIAL PRIMARY KEY,
    Imagem_Principal varchar(255) NOT NULL
);
CREATE TABLE Imagens (
    Id SERIAL PRIMARY KEY,
    Imagem varchar(255) NOT NULL,
    Id_Imagem int,
    CONSTRAINT fk_imagens_imagem FOREIGN KEY(Id_Imagem) REFERENCES Imagem(Id)
);
CREATE TABLE Categoria (
    Id SERIAL PRIMARY KEY,
    Nome varchar(30),
    CHECK(LENGTH(Nome) > 2)
);
CREATE TABLE Sub_Categoria (
    Id SERIAL PRIMARY KEY,
    Nome varchar(30),
    Id_Categoria int,
    CONSTRAINT fk_sub_categoria_categoria FOREIGN KEY(Id_Categoria) REFERENCES Categoria(Id),
    CHECK(LENGTH(Nome) > 2)
);
CREATE TABLE Descontos (
    Id SERIAL PRIMARY KEY,
    Valor_Porcentagem varchar(2) NOT NULL,
    Id_Categoria int,
    CONSTRAINT fk_descontos_categoria FOREIGN KEY(Id_Categoria) REFERENCES Categoria(Id)
);
CREATE TABLE Fabricante(
    Id SERIAL PRIMARY KEY,
    Fabricante varchar(50) UNIQUE NOT NULL
);
CREATE TABLE Produto (
    Id UUID PRIMARY KEY,
    Nome varChar(50) NOT NULL,
    Descricao varchar(200) NOT NULL,
    Cor varchar(30),
    Valor numeric(10,2) NOT NULL,
    Modelo varchar(30) NOT NULL,
    Peso numeric(5,2) NOT NULL,
    Altura numeric(5,2) NOT NULL,
    Comprimento numeric(5,2) NOT NULL,
    Largura numeric(5,2) NOT NULL,
    Id_Fabricante int,
    Id_Imagem int,
    Id_Sub_Categoria int,
    Id_Desconto int,
    CONSTRAINT fk_produto_imagem FOREIGN KEY(Id_Imagem) REFERENCES Imagem(Id),
    CONSTRAINT fk_produto_sub_categoria FOREIGN KEY(Id_Sub_Categoria) REFERENCES Sub_Categoria(Id),
    CONSTRAINT fk_especificacao_fabricante FOREIGN KEY(Id_Fabricante) REFERENCES Fabricante(Id),
    CONSTRAINT fk_desconto FOREIGN KEY(Id_Desconto) REFERENCES Descontos(Id),
    CHECK(Valor > 0 AND Peso > 0 AND Altura > 0 AND Comprimento > 0 AND Largura > 0)
);
CREATE TABLE Itens_Carrinho (
    Id SERIAL PRIMARY KEY,
    Quantidade int,
    Id_Produto UUID,
    Id_Carrinho int,
    CONSTRAINT fk_itens_carrinho_carrinho FOREIGN KEY(Id_Carrinho) REFERENCES Carrinho(Id),
    CONSTRAINT fk_itens_carrinho_produto FOREIGN KEY(Id_Produto) REFERENCES Produto(Id)
);
CREATE TABLE Avaliacao (
    Id SERIAL PRIMARY KEY,
    Data_Avaliacao date,
    Avaliacao numeric(2,1),
    Id_Produto UUID,
    CONSTRAINT fk_avaliacao_produto FOREIGN KEY(Id_Produto) REFERENCES Produto(Id)
);
CREATE TABLE Estado(
    Id SERIAL PRIMARY KEY,
    UF char(2) UNIQUE NOT NULL
);
CREATE TABLE Cidade(
    Id SERIAL PRIMARY KEY,
    Cidade varchar(30) UNIQUE NOT NULL,
    CHECK(LENGTH(Cidade) > 2)
);
CREATE TABLE Endereco (
    CEP char(8) PRIMARY KEY,
    Logradouro varchar(50) NOT NULL,
    Bairro varchar(30) NOT NULL,
    Id_Estado int,
    Id_Cidade int,
    CONSTRAINT fk_endereco_estado FOREIGN KEY(Id_Estado) REFERENCES Estado(Id),
    CONSTRAINT fk_endereco_cidade FOREIGN KEY(Id_Cidade) REFERENCES Cidade(Id),
    CHECK(LENGTH(Logradouro) > 2 AND LENGTH(Bairro) > 2)
);
CREATE TABLE Fornecedor (
    Id SERIAL PRIMARY KEY,
    Nome varchar(50) NOT NULL,
    Email varchar(100) UNIQUE NOT NULL,
    Telefone numeric(11) NOT NULL,
    CNPJ numeric(14) UNIQUE NOT NULL,
    Situacao varchar(7) NOT NULL,
    Numero_Logradouro int,
    Complemento varChar(20),
    CEP char(8),
    CONSTRAINT fk_fornecedor_endereco FOREIGN KEY(CEP) REFERENCES Endereco(CEP)
);
CREATE TABLE Data_Fornece (
    Data_Fornecimento date NOT NULL,
    Id_Fornecedor int,
    Id_Produto UUID,
    CONSTRAINT fk_data_fornece_fornecedor FOREIGN KEY(Id_Fornecedor) REFERENCES Fornecedor(Id),
    CONSTRAINT fk_data_fornece_produto FOREIGN KEY(Id_Produto) REFERENCES Produto(Id)
);
CREATE TABLE Entrega (
    Id SERIAL PRIMARY KEY,
    Data_Entrega date NOT NULL,
    Status_Entrega varchar(20) NOT NULL,
    Id_Pedido int,
    CONSTRAINT fk_entrega_pedido FOREIGN KEY(Id_Pedido) REFERENCES Pedido(Id)
);
CREATE TABLE Cliente_Possui_Endereco (
    CEP char(8),
    Id_Cliente int,
    Numero_Logradouro int NOT NULL,
    Complemento varChar(20),
    PRIMARY KEY(CEP, Id_Cliente),
    CONSTRAINT fk_cliente_possui_endereco_endereco FOREIGN KEY(CEP) REFERENCES Endereco(CEP),
    CONSTRAINT fk_cliente_possui_endereco_cliente FOREIGN KEY(Id_Cliente) REFERENCES Cliente(Id)
);
CREATE TABLE Funcionario_Possui_Endereco (
    CEP char(8),
    Id_Funcionario int,
    Numero_Logradouro int NOT NULL,
    Complemento varChar(20),
    PRIMARY KEY(CEP, Id_Funcionario),
    CONSTRAINT fk_funcionario_possui_endereco_endereco FOREIGN KEY(CEP) REFERENCES Endereco(CEP),
    CONSTRAINT fk_funcionario_possui_endereco_funcionario FOREIGN KEY(Id_Funcionario) REFERENCES Funcionario(Id)
);
CREATE TABLE Produtos_Favoritos(
	Id_Produto UUID,
    Id_Cliente int,
    CONSTRAINT fk_produtos_favoritos_produtos FOREIGN KEY(Id_Produto) references Produto (Id),
    CONSTRAINT fk_produtos_favoritos_cliente FOREIGN KEY(Id_Cliente) references Cliente (Id)
);