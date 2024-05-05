package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajadorServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Enlaza a la página principal de trabajadores
    @GetMapping("")
    public String indexTrabajadores(Model model) {
        model.addAttribute("titulo", "Trabajadores");
        return "trabajadores/indexTrabajadores";
    }

    // Lista todos los trabajadores
    @GetMapping("/listar")
    public String listarTrabajadores(Model model) {
        model.addAttribute("titulo", "Listado de trabajadores");
        model.addAttribute("trabajadores", trabajadorServices.findAll());
        return "trabajadores/trabajadoresListar";
    }

    // Muestra los detalles de un trabajador
    @GetMapping("/{id}")
    public String verTrabajador(Model model, @PathVariable String id) {
        model.addAttribute("titulo", "Trabajador " + id);
        model.addAttribute("trabajador", trabajadorServices.findById(id));
        return "trabajadores/trabajadorVer";
    }

    // Enlaza al formulario para añadir un trabajador
    @GetMapping("/nuevo")
    public String anyadirTrabajador(Model model) {
        model.addAttribute("titulo", "Añadir trabajador");
        addAtributes(model);
        model.addAttribute("trabajador", new Trabajador());
        return "trabajadores/trabajadoresCrear";
    }

    // Enlaza al formulario para editar un trabajador
    @GetMapping("/editar")
    public String editarTrabajador(Model model, @RequestParam String idTrabajador) {
        model.addAttribute("titulo", "Editar trabajador");
        addAtributes(model);
        model.addAttribute("trabajador", trabajadorServices.findById(idTrabajador));
        return "trabajadores/trabajadoresEditar";
    }

    // Crea un trabajador
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
            model.setViewName("trabajadores/trabajadoresCrear");
            model.addObject("titulo", "Error");
            model.addObject("mensaje", "Ha habido un error al añadir el trabajador.");
        }

        addAtributes(mod);
        return model;
    }

    // Edita un trabajador
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
            model.setViewName("trabajadores/trabajadoresEditar");
            model.addObject("titulo", "Error");
            model.addObject("mensaje", "Ha habido un error al actualizar el trabajador.");
        }

        addAtributes(mod);
        return model;
    }

    // Elimina un trabajador
    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam String idTrabajador) {
        ModelAndView model = new ModelAndView();
        model.setViewName("ready");
        model.addObject("titulo", "Trabajador eliminado");
        model.addObject("mensaje", "El trabajador ha sido eliminado correctamente.");
        trabajadorServices.delete(idTrabajador);
        return model;
    }

    // Método auxiliar para añadir atributos a la vista (formularios añadir y editar)
    private void addAtributes(Model mod) {
        mod.addAttribute("id", "ID:");
        mod.addAttribute("dni", "DNI:");
        mod.addAttribute("nombre", "Nombre:");
        mod.addAttribute("apellidos", "Apellidos:");
        mod.addAttribute("especialidad", "Especialidad:");
        mod.addAttribute("contraseña", "Contraseña:");
        mod.addAttribute("email", "Email:");
    }
}
