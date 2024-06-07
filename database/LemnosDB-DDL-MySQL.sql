DROP DATABASE LemnosDB;
CREATE DATABASE LemnosDB;
USE LemnosDB;

CREATE TABLE Cadastro (
	Id int primary key auto_increment,
	Email varchar(100) UNIQUE NOT NULL,
	Senha varchar(100) NOT NULL
);
CREATE TABLE Cliente (
	Id int primary key auto_increment,
	Nome varchar(40) NOT NULL,
	CPF numeric(11) UNIQUE,
	Situacao varchar(7) NOT NULL,
	Role varChar(15) NOT NULL,
	Id_Cadastro int,
    foreign key(Id_Cadastro) references Cadastro(Id),
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Funcionario (
	Id int primary key auto_increment,
	Nome varchar(40) NOT NULL,
	CPF numeric(11) UNIQUE,
	Data_Nascimento date NOT NULL,
	Data_Admissao date NOT NULL,
	Telefone numeric(11),
	Situacao varchar(7) NOT NULL,
	Role varChar(15) NOT NULL,
	Id_Cadastro int,
    foreign key(Id_Cadastro) references Cadastro(Id),
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Carrinho (
	Id int primary key auto_increment,
	Valor decimal(10,2) NOT NULL,
	Quantidade_Produtos int NOT NULL,
	Id_Cadastro int,
	foreign key(Id_Cadastro) references Cadastro (Id),
    constraint check(Valor > 0 AND Quantidade_Produtos > -1)
);
CREATE TABLE Pedido (
	Id int primary key auto_increment,
	Valor_Pedido decimal(10,2) NOT NULL,
	Metodo_Pagamento varchar(20) NOT NULL,
	Valor_Frete numeric(6, 2) NOT NULL,
	Data_Pedido date NOT NULL,
	Valor_Pagamento decimal(10,2) NOT NULL,
	Quantidade_Produtos int NOT NULL,
	Data_Pagamento date NOT NULL,
    Descricao varChar(1024) NOT NULL,
    Status varchar(50) NOT NULL,
    Id_Cadastro int,
    foreign key(Id_Cadastro) references Cadastro(Id),
    constraint check(Valor_Pedido > 0 AND Valor_Pagamento > 0 AND Quantidade_Produtos > -1)
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
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Sub_Categoria (
	Id int primary key auto_increment,
	Nome varchar(30),
    Id_Categoria int,
    foreign key(Id_Categoria) references Categoria(Id),
    constraint check(CHAR_LENGTH(Nome) > 2)
);
CREATE TABLE Desconto (
	Id int primary key auto_increment,
	Valor_Porcentagem varchar(2) NOT NULL
);
CREATE TABLE Fabricante(
	Id int primary key auto_increment,
    Fabricante varChar(100) UNIQUE NOT NULL
);
CREATE TABLE Produto (
	Id char(36) primary key,
	Nome varChar(50) NOT NULL,
	Descricao varchar(200) NOT NULL,
	Cor varchar(30),
	Valor decimal(10,2) NOT NULL,
	Modelo varchar(30) NOT NULL,
    Peso decimal(5,2) NOT NULL,
    Altura decimal(5,2) NOT NULL,
    Comprimento decimal(5,2) NOT NULL,
    Largura decimal(5,2) NOT NULL,
    Id_Fabricante int,
	Id_Imagem int,
    Id_Sub_Categoria int,
    Id_Desconto int,
    foreign key(Id_Imagem) references Imagem(Id),
    foreign key(Id_Sub_Categoria) references Sub_Categoria(Id),
    foreign key(Id_Fabricante) references Fabricante(Id),
    foreign key(Id_Desconto) references Desconto(Id),
    constraint check(Valor > 0 AND Peso > 0 AND Altura > 0 AND Comprimento > 0 AND Largura > 0)
);
CREATE TABLE Itens_Carrinho (
	Id int primary key auto_increment,
	Quantidade int,
	Id_Carrinho int,
    Id_Produto char(36),
	foreign key(Id_Carrinho) references Carrinho (Id),
    foreign key(Id_Produto) references Produto (Id)
);
CREATE TABLE Avaliacao (
	Id int primary key auto_increment,
	Data_Avaliacao date,
	Avaliacao decimal(2,1),
	Id_Produto char(36),
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
	CEP char(8) primary key,
	Logradouro varchar(50) NOT NULL,
	Bairro varchar(30) NOT NULL,
    Id_Estado int,
    Id_Cidade int,
    foreign key(Id_Estado) references Estado (Id),
    foreign key(Id_Cidade) references Cidade (Id),
    constraint check(CHAR_LENGTH(Logradouro) > 2 AND CHAR_LENGTH(Bairro) > 2)
);
CREATE TABLE Fornecedor (
	Id int primary key auto_increment,
    Nome varChar(50) NOT NULL,
    Email varChar(100) NOT NULL UNIQUE,
	Telefone numeric(11) NOT NULL,
	CNPJ numeric(14) UNIQUE NOT NULL,
	Situacao varchar(7) NOT NULL,
	Numero_Logradouro int,
	Complemento varChar(20),
	CEP char(8),
	foreign key(CEP) references Endereco(CEP)
);
CREATE TABLE Data_Fornece (
	Data_Fornecimento date NOT NULL,
	Id_Fornecedor int,
	Id_Produto char(36),
	foreign key(Id_Fornecedor) references Fornecedor (Id),
	foreign key(Id_Produto) references Produto (Id)
);
CREATE TABLE Entrega (
	Id int primary key auto_increment,
	Data_Entrega date NOT NULL,
    Status_Entrega varchar(20) NOT NULL,
	Id_Pedido int,
	Foreign key(Id_Pedido) references Pedido (Id)
);
CREATE TABLE Cliente_Possui_Endereco (
	CEP char(8),
	Id_Cliente int,
    Numero_Logradouro int NOT NULL,
    Complemento varChar(20),
	primary key(CEP, Id_Cliente),
	foreign key(CEP) references Endereco (CEP),
	foreign key(Id_Cliente) references Cliente (Id)
);
CREATE TABLE Funcionario_Possui_Endereco (
	CEP char(8),
	Id_Funcionario int,
    Numero_Logradouro int NOT NULL,
    Complemento varChar(20),
	primary key(CEP, Id_Funcionario),
	foreign key(CEP) references Endereco (CEP),
    foreign key(Id_Funcionario) references Funcionario (Id)
);
CREATE TABLE Produtos_Favoritos(
	Id_Produto char(36),
    Id_Cliente int,
    foreign key(Id_Produto) references Produto (Id),
    foreign key(Id_Cliente) references Cliente (Id)
);

INSERT INTO Estado(UF) VALUES
('AC'), ('AL'), ('AP'),
('AM'), ('BA'), ('CE'),
('ES'), ('GO'), ('MA'),
('MT'), ('MS'), ('MG'),
('PA'), ('PB'), ('PR'),
('PE'), ('PI'), ('RJ'),
('RN'), ('RS'), ('RO'),
('RR'), ('SC'), ('SP'),
('SE'), ('TO'), ('DF');

INSERT INTO Categoria(Nome) VALUES
('Casa Inteligente'),
('Computadores'),
('Eletrônicos'),
('Hardware'),
('Kits'),
('Monitores'),
('Notebooks e Portáteis'),
('Periféricos'),
('Realidade Virtual'),
('Redes e wireless'),
('Video Games');

INSERT INTO Sub_Categoria(Nome, Id_Categoria) VALUES
('Assistente Virtual', 1),
('Controles Smarts', 1),
('Lâmpadas Inteligentes', 1),
('Sensores', 1),
('Computadores Gamers', 2),
('Computadores Workstation', 2),
('Acessórios de Console', 3),
('Carregadores', 3),
('Refrigeração', 3),
('Smart Box', 3),
('Armazenamento', 4),
('Coolers', 4),
('Fonte', 4),
('Memória RAM', 4),
('Placa de Video', 4),
('Placa Mãe', 4),
('Processadores', 4),
('Gamer', 5),
('Periféricos', 5),
('Upgrade', 5),
('Monitores Gamers', 6),
('Monitores Workstation', 6),
('Notebooks', 7),
('Smartphones', 7),
('Tablets', 7),
('Smart Watch', 7),
('Caixa de Som', 8),
('Fone de Ouvido', 8),
('Microfone', 8),
('Mouse', 8),
('Mousepad', 8),
('Teclado', 8),
('Óculos de VR', 9),
('Periféricos DE VR', 9),
('Access Point', 10),
('Adaptadores', 10),
('Cabos', 10),
('Cabos de Redes', 10),
('Roteadores', 10),
('Switches', 10),
('Console de Mesa', 11),
('Portátil', 11);

INSERT INTO Desconto(Valor_Porcentagem) VALUES
('0'), ('1'), ('2'), ('3'), ('4'),
('5'), ('6'), ('7'), ('8'), ('9'),
('10'), ('11'), ('12'), ('13'), ('14'),
('15'), ('16'), ('17'), ('18'), ('19'),
('20'), ('21'), ('22'), ('23'), ('24'),
('25'), ('26'), ('27'), ('28'), ('29'),
('30'), ('31'), ('32'), ('33'), ('34'),
('35'), ('36'), ('37'), ('38'), ('39'),
('40'), ('41'), ('42'), ('43'), ('44'),
('45'), ('46'), ('47'), ('48'), ('49'),
('50'), ('51'), ('52'), ('53'), ('54'),
('55'), ('56'), ('57'), ('58'), ('59'),
('60'), ('61'), ('62'), ('63'), ('64'),
('65'), ('66'), ('67'), ('68'), ('69'),
('70'), ('71'), ('72'), ('73'), ('74'),
('75'), ('76'), ('77'), ('78'), ('79'),
('80'), ('81'), ('82'), ('83'), ('84'),
('85'), ('86'), ('87'), ('88'), ('89'),
('90'), ('91'), ('92'), ('93'), ('94'),
('95'), ('96'), ('97'), ('98'), ('99');
