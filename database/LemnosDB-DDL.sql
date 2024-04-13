DROP DATABASE LemnosDB;
CREATE DATABASE LemnosDB;
USE LemnosDB;

CREATE TABLE Cadastro (
	Id int primary key auto_increment,
	Email varchar(40) UNIQUE NOT NULL,
	Senha varchar(16) NOT NULL,
    constraint check(CHAR_LENGTH(Senha) > 7)
);
CREATE TABLE Cliente (
	Id int primary key auto_increment,
	Nome varchar(40) NOT NULL,
	CPF char(11) UNIQUE NOT NULL,
	Numero_Logradouro int NOT NULL,
	Id_Cadastro int,
    foreign key(Id_Cadastro) references Cadastro(Id),
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Funcionario (
	Id int primary key auto_increment,
	Nome varchar(40) NOT NULL,
	CPF char(11) UNIQUE NOT NULL,
	Data_Nascimento date NOT NULL,
	Data_Admissao date NOT NULL,
	Numero_Logradouro int NOT NULL,
	Telefone char(11),
	Id_Cadastro int,
    foreign key(Id_Cadastro) references Cadastro(Id),
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Fornecedor (
	Id int primary key auto_increment,
    Nome varChar(50) NOT NULL,
	Telefone char(11) NOT NULL,
	CNPJ char(14) UNIQUE NOT NULL,
	CEP char(11),
	Numero_Logradouro int NOT NULL
);
CREATE TABLE Pedido (
	Id int primary key auto_increment,
	Valor_Pedido decimal(10,2) NOT NULL,
	Metodo_Pagamento varchar(20) NOT NULL,
	Data_Pedido date NOT NULL,
	Valor_Pagamento decimal(10,2) NOT NULL,
	Quantidade_Produtos int NOT NULL,
	Data_Pagamento date NOT NULL,
    Descricao varChar(255) NOT NULL,
    constraint check(Valor_Pedido > 0 AND Valor_Pagamento > 0 AND Quantidade_Produtos > -1)
);
CREATE TABLE Carrinho (
	Id int primary key auto_increment,
	Valor decimal(10,2) NOT NULL,
	Quantidade_Produtos int NOT NULL,
	Id_Cadastro int,
	foreign key(Id_Cadastro) references Cadastro (Id),
    constraint check(Valor > 0 AND Quantidade_Produtos > -1)
);
CREATE TABLE Itens_Carrinho (
	Id int primary key auto_increment,
	Id_Carrinho int,
	Id_Pedido int,
	foreign key(Id_Carrinho) references Carrinho (Id),
    foreign key(Id_Pedido) references Pedido (Id)
);
CREATE TABLE Imagem (
	Id int primary key auto_increment,
	Imagem_Principal varchar(255) NOT NULL
);
CREATE TABLE Imagens (
	Id int primary key auto_increment,
	Imagem varchar(255) NOT NULL,
    Id_Imagem int,
    foreign key(Id_Imagem) references Imagem (Id)
);
CREATE TABLE Categoria (
	Id int primary key auto_increment,
	Nome varchar(30),
    constraint check(Nome > 2)
);
CREATE TABLE Sub_Categoria (
	Id int primary key auto_increment,
	Nome varchar(30),
    Id_Categoria int,
    foreign key(Id_Categoria) references Categoria(Id),
    constraint check(Nome > 2)
);
CREATE TABLE Sub_Sub_Categoria (
	Id int primary key auto_increment,
	Nome varchar(30),
    Id_Sub_Categoria int,
    foreign key(Id_Sub_Categoria) references Sub_Categoria(Id),
    constraint check(Nome > 2)
);
CREATE TABLE Descontos (
	Id int primary key auto_increment,
	Valor_Porcentagem varchar(2) NOT NULL,
	Id_Categoria int,
	foreign key(Id_Categoria) references Categoria (Id)
);
CREATE TABLE Produto (
	Id int primary key auto_increment,
	Descricao varchar(200) NOT NULL,
	Cor varchar(30),
	Valor decimal(10,2) NOT NULL,
	Id_Itens_Carrinho int,
	Id_Imagem int,
    Id_Sub_Sub_Categoria int,
    foreign key(Id_Itens_Carrinho) references Itens_Carrinho(Id),
    foreign key(Id_Imagem) references Imagem(Id),
    foreign key(Id_Sub_Sub_Categoria) references Sub_Sub_Categoria(Id),
    constraint check(Valor > 0)
);
CREATE TABLE Avaliacao (
	Id int primary key auto_increment,
	Data_Avaliacao date,
	Avaliacao decimal(2,1),
	Id_Produto int,
    foreign key(Id_Produto) references Produto (Id)
);
CREATE TABLE Estado(
	Id int primary key auto_increment,
    UF char(2) UNIQUE NOT NULL
);
CREATE TABLE Cidade(
	Id int primary key auto_increment,
    Cidade varChar(30) UNIQUE NOT NULL
);
CREATE TABLE Endereco (
	CEP int primary key,
	Logradouro varchar(50) NOT NULL,
	Bairro varchar(30) NOT NULL,
	Id_Fornecedor int,
    Id_Estado int,
    Id_Cidade int,
	foreign key(Id_Fornecedor) references Fornecedor (Id),
    foreign key(Id_Estado) references Estado (Id),
    foreign key(Id_Cidade) references Cidade (Id),
    constraint check(CHAR_LENGTH(Logradouro) > 2 AND CHAR_LENGTH(Bairro) > 2)
);
CREATE TABLE Fabricante(
	Id int primary key auto_increment,
    Fabricante varChar(50) UNIQUE NOT NULL
);
CREATE TABLE Especificacao (
	Id int primary key auto_increment,
    Modelo varchar(30) NOT NULL,
	Peso decimal(5,2) NOT NULL,
	Altura decimal(5,2) NOT NULL,
	Comprimento decimal(5,2) NOT NULL,
	Largura decimal(5,2) NOT NULL,
	Id_Produto int,
    Id_Fabricante int,
	foreign key(Id_Produto) references Produto (Id),
    foreign key(Id_Fabricante) references Fabricante (Id),
    constraint check(Peso > 0 AND Altura > 0 AND Comprimento > 0 AND Largura > 0)
);
CREATE TABLE Data_Fornece (
	Data_Fornecimento date NOT NULL,
	Id_Fornecedor int,
	Id_Produto int,
	foreign key(Id_Fornecedor) references Fornecedor (Id),
	foreign key(Id_Produto) references Produto (Id)
);
CREATE TABLE Entrega (
	Id int primary key auto_increment,
	Data_Entrega date NOT NULL,
	Id_Pedido int,
	Foreign key(Id_Pedido) references Pedido (Id)
);
CREATE TABLE Cliente_Possui_Endereco (
	CEP int,
	Id_Cliente int,
	primary key(CEP, Id_Cliente),
	foreign key(CEP) references Endereco (CEP),
	foreign key(Id_Cliente) references Cliente (Id)
);
CREATE TABLE Funcionario_Possui_Endereco (
	CEP int,
	Id_Funcionario int,
	primary key(CEP, Id_Funcionario),
	foreign key(CEP) references Endereco (CEP),
    foreign key(Id_Funcionario) references Funcionario (Id)
);