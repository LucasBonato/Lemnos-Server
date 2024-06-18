let baseUri = "http://localhost:8080/api";

async function cadastrarCliente(cliente){

    let response = await fetch(baseUri + "/cadastro/cliente", {
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
    return response.status;
}


class Cliente {
    nome;
    telefone;
    cpf;
    email;
    senha;
    numeroLogradouro;

    constructor(nome, telefone, cpf, email, senha, numeroLogradouro){
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.numeroLogradouro = numeroLogradouro;
    }
}