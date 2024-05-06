package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.services.TrabajoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trabajos")
public class TrabajoViewController {
    @Autowired
    TrabajoServices trabajoServices = new TrabajoServices();

    @GetMapping("")
    public String indexTrabajos(Model model) {
        model.addAttribute("titulo", "Trabajos");
        model.addAttribute("trabajos", trabajoServices.findAll());
        return "trabajos/indexTrabajos";
    }

    @GetMapping("/nuevo")
    public String nuevoTrabajo(Model model) {
        return "trabajos/indexTrabajos";
    }
}
