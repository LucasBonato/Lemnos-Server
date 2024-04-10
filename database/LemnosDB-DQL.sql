USE LemnosDB;

SELECT c.Id, c.Nome, c.CPF, c.CEP, ca.Email, ca.Senha FROM Cliente c 
INNER JOIN Cadastro ca ON c.Id_Cadastro = ca.Id;