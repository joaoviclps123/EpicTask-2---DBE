package br.com.fiap.cp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.fiap.cp.bean.Usuario;
import br.com.fiap.cp.bo.UsuarioBO;
import br.com.fiap.cp.repository.UsuarioRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@RestController
@RequestMapping("/api/usuario")
public class ApiRestController {
	
	@Autowired
	UsuarioRepository uRepository;
	
	@RequestMapping(value="/cadastroUsuario", produces={"application/json"}, method=RequestMethod.POST)
	public @ResponseBody JSONObject cadastroUsuario(@RequestBody JSONObject jsonCadastro) {
		UsuarioBO ubo = this.getUsuarioBO();
		
		try {
			Usuario usuario = ubo.cadastroUsuario(jsonCadastro);
			return usuario.toJSON();
		} catch (Exception e) {
			
			return this.getJSONErro(e.getMessage());
		
		}
	}
	
	@RequestMapping(value="/alterarUsuario", produces={"application/json"}, method=RequestMethod.POST)
	public @ResponseBody JSONObject alterarUsuario(@RequestBody JSONObject jsonAlterar) {
		UsuarioBO ubo = this.getUsuarioBO();
		
		try {
			Usuario usuario = ubo.alterarUsuario(jsonAlterar);
			return usuario.toJSON();
		} catch (Exception e) {
			
			return this.getJSONErro(e.getMessage());
	
		}
	}
	
	@RequestMapping(value="/apagarUsuario", produces={"application/json"}, method=RequestMethod.POST)
	public @ResponseBody JSONObject apagarUsuario(@RequestBody JSONObject jsonApagar) {
		UsuarioBO ubo = this.getUsuarioBO();
		
		
		boolean apagou = ubo.apagarUsuario(jsonApagar);
		JSONObject jsonApagou = new JSONObject();
		jsonApagou.put("apagou", apagou);
		
		return jsonApagou;	
	
	}
	
	
	@RequestMapping(value="/buscarUsuarioEspecifico", produces={"application/json"}, method=RequestMethod.POST)
	public @ResponseBody JSONObject buscarUsuarioEspecifico(@RequestBody JSONObject jsonBuscar) {
		UsuarioBO ubo = this.getUsuarioBO();
		
		try {
			Usuario usuario = ubo.buscarUsuarioEspecifico(jsonBuscar);
			return usuario.toJSON();
		} catch (Exception e) {
			
			return this.getJSONErro(e.getMessage());
		
		}
	}
	
	@RequestMapping(value="/buscarTodosUsuarios", produces={"application/json"}, method=RequestMethod.POST)
	public @ResponseBody JSONArray buscarTodosUsuarios() {
		UsuarioBO ubo = this.getUsuarioBO();
		
		List<Usuario> usuarios = ubo.buscarTodosUsuarios();
		JSONArray arrayUsuarios = new JSONArray();
		
		for (Usuario usuario : usuarios) {
			arrayUsuarios.add(usuario.toJSON());
		}
		return arrayUsuarios;
	}
	
	
	
	public JSONObject getJSONErro(String erro) {
		JSONObject jsonErro = new JSONObject();
		jsonErro.put("mensagem", erro);
		
		return jsonErro;
	}
	
	
	public UsuarioBO getUsuarioBO() {
		return new UsuarioBO(this.uRepository);
	}
}
