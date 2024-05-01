![PUT](https://img.shields.io/static/v1?label=&message=PUT&color=blue&style=for-the-badge)

> `{{baseUri}}/cliente/{$id}`

~~~ JavaScript

const uri = 'http://localhost:8080/api';

function updateCliente(cliente, id){
    fetch(uri + "/cliente/${id}", {
        method: PUT,
        headers: {
        "Content-type": "application/json; charset=UTF-8"
    },
    body: JSON.stringify({
        nome: cliente.nome(nome),
        telefone: cliente.telefone(telefone),
        cpf: cliente.cpf(cpf),
        email: cliente.email(email),
        senha: cliente.senha(senha),
        numeroLogradouro: cliente.numLogradouro(numLogradouro)
        })
    });
}

// Utilizando Axios
// ($ npm install axios)

function updateCliente(cliente, id){

    axios.put(uri + "/cliente/${id}", {
        nome: cliente.nome(nome),
        telefone: cliente.telefone(telefone),
        cpf: cliente.cpf(cpf),
        email: cliente.email(email),
        senha: cliente.senha(senha),
        numeroLogradouro: cliente.numLogradouro(numLogradouro)
        })
        .then((response) => console.log(response.data))
        .catch((error) => console.log(error));
}