package br.com.fiap.cp.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import net.sf.json.JSONObject;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table (name = "TB_USUARIO_CP")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_USUARIO_ID")
    @SequenceGenerator(sequenceName = "SEQ_TB_USUARIO_ID", allocationSize = 1, name = "SEQ_TB_USUARIO_ID")
	@Column(name="id_usuario")
	private Integer idUsuario;
	
	@NotBlank(message = "Nome obrigatório")
	@Column(name="nm_usuario",nullable = false)
	private String nome;
	
	@Length(min = 8, max = 32)
	@NotBlank(message = "Senha obrigatória")
	@Column(name="nr_senha",nullable = false)
	private String senha;
	
	@Email
	@NotBlank(message = "E-mail obrigatório")
	@Column(name="nm_email",nullable = false)
	private String email;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", this.idUsuario);
		json.put("nome", this.nome);
		json.put("email", this.email);
		json.put("senha", this.senha);
		return json;
	}
		
}
