package br.com.fiap.cp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.fiap.cp.bean.Usuario;
import br.com.fiap.cp.bo.UsuarioBO;
import br.com.fiap.cp.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	HttpSession session;
	
	@Autowired
	UsuarioRepository uRepository;
	
	@RequestMapping(value="/lista", method= RequestMethod.GET)
	public String listaUsuarioPagina(Model model) {
		if(!this.usuarioLogado()) return "redirect:/";
		
		UsuarioBO ubo = this.getUsuarioBO();
		
		List<Usuario> usuarios = ubo.buscarTodosUsuarios();
		
		model.addAttribute("listaDeUsuarios", usuarios);
		
		return "lista";
	}
	
	@RequestMapping(value="/cadastro", method= RequestMethod.GET)
	public String cadastroUsuarioPagina(Usuario usuario) {
		
		return "cadastro";
	}
	
	@RequestMapping(value="/home", method= RequestMethod.GET)
	public String homeUsuarioPagina() {
		if(!this.usuarioLogado()) return "redirect:/";
		
		return "home";
	}
	
	
	
	@RequestMapping(value="/usuario/cadastroUsuario", method= RequestMethod.POST)
	public String cadastroUsuario(@Valid Usuario usuario, BindingResult bind) {
		
		if(bind.hasErrors()) {
			return "/cadastro";
		}
		
		UsuarioBO ubo = this.getUsuarioBO();
		
		try {
			ubo.cadastroUsuario(usuario);
		} catch (Exception e) {
			return "/cadastro?cadastro=false";
		}
		if (this.usuarioLogado()) {
			return "redirect:/home?cadastro=true";
		}
		return "redirect:/";
	}	
	
	
	
	public boolean usuarioLogado() {
		try {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if(usuario == null) {
				return false;
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public UsuarioBO getUsuarioBO() {
		return new UsuarioBO(this.uRepository);
	}
	
}
