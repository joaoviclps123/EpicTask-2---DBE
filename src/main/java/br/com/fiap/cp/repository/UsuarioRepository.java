package br.com.fiap.cp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cp.bean.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByEmailAndSenha(String email, String senha);
	Usuario findByEmail(String email);
	Usuario findByIdUsuario(Integer idUsuario);
	
}
