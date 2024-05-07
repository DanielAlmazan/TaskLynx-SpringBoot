package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajadorServices;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajoServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/trabajos")
public class TrabajoViewController {
    @Autowired
    TrabajoServices trabajoServices = new TrabajoServices();

    @Autowired
    TrabajadorServices trabajadorServices = new TrabajadorServices();

    @GetMapping("")
    public String indexTrabajos(Model model) {
        model.addAttribute("titulo", "Trabajos");
        model.addAttribute("trabajos", trabajoServices.findAll());
        return "trabajos/indexTrabajos";
    }

    @GetMapping("/nuevo")
    public String nuevoTrabajo(Model model) {
        model.addAttribute("titulo", "Nuevo Trabajo");
        model.addAttribute("trabajo", new Trabajo());
        model.addAttribute("trabajadores", trabajadorServices.findAll());
        model.addAttribute("editar", false);
        return "trabajos/trabajoFormulario";
    }

    @GetMapping("/editar")
    public String editarTrabajo(Model model, @RequestParam String codTrabajo) {
        Trabajo trabajo = trabajoServices.findById(codTrabajo);
        model.addAttribute("titulo", "Editar trabajo - " + trabajo.getCodTrabajo());
        model.addAttribute("trabajo", trabajo);
        model.addAttribute("editar", true);
        return "trabajos/trabajoFormulario";
    }

    @RequestMapping("/create")
    public ModelAndView createTrabajo(@Valid Trabajo trabajo, BindingResult result, Model mod) {
        ModelAndView model = new ModelAndView();
        boolean exists = false;
        model.addObject("trabajo", trabajo);

        if(!result.hasErrors()) {
            for (Trabajo t : trabajoServices.findAll()) {
                if (t.getCodTrabajo().equals(trabajo.getCodTrabajo())) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                model.setViewName("ready");
                trabajoServices.save(trabajo);
                model.addObject("titulo", "Trabajo añadido");
                model.addObject("mensaje", "El trabajo ha sido añadido correctamente.");
            } else {
                model.setViewName("trabajos/trabajoFormulario");
                model.addObject("titulo", "Nuevo Trabajo");
                model.addObject("error", "Error:");
                model.addObject("mensajeError", "El trabajo con código '" + trabajo.getCodTrabajo()
                        + "' ya existe.");
            }
        } else {
            model.setViewName("trabajos/trabajoFormulario");
            model.addObject("titulo", "Nuevo Trabajo");
            model.addObject("error", "Errores:");
            model.addObject("trabajadores", trabajadorServices.findAll());
        }

        return model;
    }
}
