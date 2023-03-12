package com.ingeniemos.controlclientes.web;

import com.ingeniemos.controlclientes.dao.IPersonaDao;
import com.ingeniemos.controlclientes.dominio.Persona;
import com.ingeniemos.controlclientes.servicio.PersonaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private PersonaService personaService;
    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        log.info("Se esta ejecutando el MVC");
        log.info("usuario autenticado " + user);
        var personas = personaService.listarPersonas();
        model.addAttribute("personas",personas);
        model.addAttribute("username", user.getUsername());
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona){

        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errors){
        if(errors.hasErrors()){
            return "modificar";
        }
        personaService.guardar(persona);
        return "redirect:/";
    }
    @GetMapping("/editar/{id}")
    public String editar(Persona persona, Model model){
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    @GetMapping("/eliminar")
    public String  eliminar(Persona persona){
        personaService.eliminar(persona);
        return "redirect:/";
    }

}
