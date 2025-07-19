package io.github.milhiolo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="users") // É uma boa prática nomear a tabela no plural
public class User extends PanacheEntity {

    // O PanacheEntity já nos dá o campo 'id' do tipo Long automaticamente.

    public String nomeCompleto;

    @Column(unique = true) // Garante que não haverá dois usuários com o mesmo email
    public String email;

    public String senha;

    // Exemplo para o futuro:
    // public String tipoAssinatura;
}