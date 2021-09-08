package br.com.fiap.cp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.fiap.cp.bean.Usuario;
import br.com.fiap.cp.repository.UsuarioRepository;

@Controller
public class LoginController {

	@Autowired
	HttpSession session;
	
	@Autowired
	private UsuarioRepository uRepository;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home(Usuario usuario) {
		return "index";
	}
	
	@RequestMapping(value="/login/validarUsuario", method = RequestMethod.POST)
	public String validarUsuario(Usuario usuario, Model model) {
		Usuario usuarioLogado = this.uRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		
		if(usuarioLogado == null) {
			model.addAttribute("login", "E-mail ou senha inválido");
			return "index";
		}
		
		session.setAttribute("usuario", usuarioLogado);
		
		return "redirect:/home";
	}
	
}
