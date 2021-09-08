package br.com.fiap.cp.bo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fiap.cp.bean.Usuario;
import br.com.fiap.cp.repository.UsuarioRepository;
import net.sf.json.JSONObject;

public class UsuarioBO {
	
	private UsuarioRepository uRepository;
	
	//privado para nunca instanciar sem passar o repositório
	private UsuarioBO() {
		
	}

	public UsuarioBO(UsuarioRepository uRepository) {
		this.uRepository = uRepository;
	}
	
	public Usuario consultaUsuarioPorEmailESenha (JSONObject consultaPorEmailESenhaJson) {
		String email = consultaPorEmailESenhaJson.getString("email");
		String senha = consultaPorEmailESenhaJson.getString("senha");
		return this.uRepository.findByEmailAndSenha(email, senha);
	}
	
	public final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return !matcher.find();
	}
	
	public Usuario cadastroUsuario (JSONObject usuarioJson) throws Exception {
		String email = usuarioJson.getString("email");
		if (email.equals("")) {
			throw new Exception("E-mail não pode ser vazio");
		}else if(this.validateEmail(email)) {
			throw new Exception("E-mail inválido");
		}
		Usuario usuario = this.uRepository.findByEmail(email);
		if (usuario == null) {
			Usuario novoUsuario = new Usuario();
			String senha = usuarioJson.getString("senha");
			if (senha.equals("")) {
				throw new Exception("Senha não pode ser vazio");
			}else if(senha.length() < 7) {
				throw new Exception("Senha deve ter entre 8 e 32 caracteres");
			}
			String nome = usuarioJson.getString("nome");
			if(nome.equals("")) {
				throw new Exception("O nome não pode ser vazio");
			}
			novoUsuario.setEmail(email);
			novoUsuario.setSenha(senha);
			novoUsuario.setNome(nome);
			return this.uRepository.save(novoUsuario);
		} 
		//Lançando exceção genérica
		throw new Exception("Já Possui Cadastro");		
	}
	
	public Usuario cadastroUsuario(Usuario novoUsuario) {
		return this.uRepository.save(novoUsuario);
	}
	
	public Usuario alterarUsuario (JSONObject alterarJson) throws Exception {
		Integer idUsuario = alterarJson.getInt("id");
		Usuario usuario = this.uRepository.findByIdUsuario(idUsuario);
		if(usuario != null) {
			String senha = alterarJson.getString("senha");
			if (senha.equals("")) {
				throw new Exception("Senha não pode ser vazio");
			}else if(senha.length() < 7) {
				throw new Exception("Senha deve ter entre 8 e 32 caracteres");
			}
			String email = alterarJson.getString("email");
			Usuario usuarioEmail = this.uRepository.findByEmail(email);
			if (email.equals("")) {
				throw new Exception("E-mail não pode ser vazio");
			}else if(this.validateEmail(email)) {
				throw new Exception("E-mail inválido");
			}else if(usuarioEmail != null && !email.equals(usuario.getEmail())){
				throw new Exception("Você não pode alterar o e-mail para um existente");
			}
			String nome = alterarJson.getString("nome");
			if(nome.equals("")) {
				throw new Exception("O nome não pode ser vazio");
			}
			if(!senha.equals("")) {
				usuario.setSenha(senha);
			}
			usuario.setEmail(email);
			usuario.setNome(nome);
			return this.uRepository.save(usuario);
		}
		throw new Exception("Ocorreu um erro no sistema");
	}
	
	public boolean apagarUsuario(JSONObject apagarJson) {
		Integer idUsuario = apagarJson.getInt("id");
		Usuario usuario = this.uRepository.findByIdUsuario(idUsuario);
		if(usuario != null) {
			uRepository.delete(usuario);
			return true;
		}
		return false;
	}
	
	public List<Usuario> buscarTodosUsuarios(){
		return this.uRepository.findAll();
	}
	
	public Usuario buscarUsuarioEspecifico (JSONObject buscarJson) throws Exception {
		Integer idUsuario = buscarJson.getInt("id");
		Usuario usuario = this.uRepository.findByIdUsuario(idUsuario);
		if (usuario != null) {
			return usuario;
		}
		throw new Exception("id não encontrado");
	}
	
	
}
