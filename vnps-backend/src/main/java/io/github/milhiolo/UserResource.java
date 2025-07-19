package io.github.milhiolo;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/users") // Define o endereço base para todos os métodos nesta classe
public class UserResource {

    @POST
    @Path("/register") // O endereço completo será /api/users/register
    @Transactional // Essencial para qualquer operação que modifica o banco de dados
    public Response registerUser(User user) {
        // Adicionar lógica para criptografar a senha antes de salvar
        // Ex: user.senha = Bcrypt.hash(user.senha);

        user.persist(); // A "mágica" do Panache para salvar o usuário no banco

        // Retorna uma resposta HTTP 201 (Created) com os dados do usuário criado
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}