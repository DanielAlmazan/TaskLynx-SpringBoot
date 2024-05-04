package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajadorServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadoresViewController {

    @Autowired
    TrabajadorServices trabajadorServices = new TrabajadorServices();

    @GetMapping("")
    public String indexTrabajadores(Model model) {
        model.addAttribute("titulo", "Trabajadores");
        return "trabajadores";
    }

    // This method lists all workers
    @GetMapping("/listar")
    public String listarTrabajadores(Model model) {
        model.addAttribute("titulo", "Listado de trabajadores");
        model.addAttribute("trabajadores", trabajadorServices.findAll());
        return "trabajadoresListar";
    }

    // This method shows the details of a worker
    @GetMapping("/{id}")
    public String verTrabajador(Model model, @PathVariable String id) {
        model.addAttribute("titulo", "Trabajador");
        model.addAttribute("trabajador", trabajadorServices.findById(id));
        return "trabajadorVer";
    }

    // This method links to the form to add a new worker
    @GetMapping("/nuevo")
    public String anyadirTrabajador(Model model) {
        addAtributes(model);
        model.addAttribute("trabajador", new Trabajador());
        return "trabajadoresForm";
    }

    // This method links to the form to edit a worker
    @GetMapping("/editar")
    public String editarTrabajador(Model model, @RequestParam String idTrabajador) {
        addAtributes(model);
        model.addAttribute("trabajador", trabajadorServices.findById(idTrabajador));
        return "trabajadoresForm";
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public ModelAndView updateOrCreate(@Valid Trabajador trabajador, BindingResult result, Model mod, HttpServletRequest request) {
        if ("put".equals(request.getParameter("_method"))) {
            return edit(trabajador, result, mod);
        } else {
            return create(trabajador, result, mod);
        }
    }

    // This method creates a new worker
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView create(@Valid Trabajador trabajador, BindingResult result, Model mod) {
        ModelAndView model = new ModelAndView();
        boolean exists = false;
        model.addObject("trabajador", trabajador);

        if (!result.hasErrors()) {
            for (Trabajador t: trabajadorServices.findAll()) {
                if (t.getIdTrabajador().equals(trabajador.getIdTrabajador())) {
                    exists = true;
                    break;
                }
            }

            model.setViewName("ready");

            if (!exists) {
                trabajadorServices.save(trabajador);
                model.addObject("titulo", "Trabajador añadido");
                model.addObject("mensaje", "El trabajador ha sido añadido correctamente.");
            } else {
                model.addObject("titulo", "Error");
                model.addObject("mensaje", "El trabajador ya existe.");
            }
        } else {
            model.setViewName("trabajadoresForm");
            model.addObject("titulo", "Error");
            model.addObject("mensaje", "Ha habido un error al añadir el trabajador.");
        }

        addAtributes(mod);
        return model;
    }

    // This method updates a worker
    @RequestMapping(value="/edit", method=RequestMethod.PUT)
    public ModelAndView edit(@Valid Trabajador trabajador, BindingResult result, Model mod) {
        ModelAndView model = new ModelAndView();
        model.addObject("trabajador", trabajador);
        model.setViewName("ready");

        if (!result.hasErrors()) {
            trabajadorServices.save(trabajador);
            model.addObject("titulo", "Trabajador actualizado");
            model.addObject("mensaje", "El trabajador ha sido actualizado correctamente.");
        } else {
            model.setViewName("trabajadoresForm");
            model.addObject("titulo", "Error");
            model.addObject("mensaje", "Ha habido un error al actualizar el trabajador.");
        }

        addAtributes(mod);
        return model;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam String idTrabajador) {
        ModelAndView model = new ModelAndView();
        model.setViewName("ready");
        model.addObject("titulo", "Trabajador eliminado");
        model.addObject("mensaje", "El trabajador ha sido eliminado correctamente.");
        trabajadorServices.delete(idTrabajador);
        return model;
    }

    private void addAtributes(Model mod) {
        mod.addAttribute("titulo", "Añadir trabajador");
        mod.addAttribute("id", "ID:");
        mod.addAttribute("dni", "DNI:");
        mod.addAttribute("nombre", "Nombre:");
        mod.addAttribute("apellidos", "Apellidos:");
        mod.addAttribute("especialidad", "Especialidad:");
        mod.addAttribute("contraseña", "Contraseña:");
        mod.addAttribute("email", "Email:");
    }
}
