package com.mateus.agendadortarefas.infrastructure.security;

import com.mateus.agendadortarefas.business.dto.UsuarioDTO;
import com.mateus.agendadortarefas.infrastructure.security.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl{

    @Autowired
    private UsuarioClient client;

    // Implementação do metodo para carregar atalhos do usuario pelo e-mail
    public UserDetails carregaDadosUsuario(String email, String token){
        // Busca o usuário no banco de dados pelo e-mail e token
        UsuarioDTO usuarioDTO = client.buscaUsuarioPorEmail(email,token);
        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build();
    }
}
