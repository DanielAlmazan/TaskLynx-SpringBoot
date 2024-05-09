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
        model.addAttribute("trabajadores", trabajadorServices.findAll());
        return "trabajadores/indexTrabajadores";
    }

    // Muestra los detalles de un trabajador
    @GetMapping("/{id}")
    public String verTrabajador(Model model, @PathVariable String id) {
        model.addAttribute("titulo", "Trabajador " + id);
        model.addAttribute("trabajador", trabajadorServices.findById(id));
        return "trabajadores/trabajadoresDetalle";
    }

    // Enlaza al formulario para añadir un trabajador
    @GetMapping("/nuevo")
    public String anyadirTrabajador(Model model) {
        model.addAttribute("titulo", "Añadir trabajador");
        addAtributes(model);
        model.addAttribute("trabajador", new Trabajador());
        model.addAttribute("editar", false);
        return "trabajadores/trabajadoresForm";
    }

    // Enlaza al formulario para editar un trabajador
    @GetMapping("/editar")
    public String editarTrabajador(Model model, @RequestParam String idTrabajador) {
        Trabajador trabajador = trabajadorServices.findById(idTrabajador);
        model.addAttribute("titulo", "Editar trabajador - " + trabajador.getIdTrabajador());
        addAtributes(model);
        model.addAttribute("trabajador", trabajador);
        model.addAttribute("editar", true);
        return "trabajadores/trabajadoresForm";
    }

    // Devuelve el formulario de añadir o editar según el método
    @RequestMapping("/processForm")
    public ModelAndView processForm(@Valid Trabajador trabajador, BindingResult result, Model model, @RequestParam String _method) {
        if(_method.equals("POST")) return create(trabajador, result, model);
        else return edit(trabajador, result, model);
    }

    // Crea un trabajador
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public ModelAndView create(@Valid Trabajador trabajador, BindingResult result, Model mod) {
        ModelAndView model = new ModelAndView();
        model.addObject("trabajador", trabajador);

        if (!result.hasErrors()) {
            if (trabajadorServices.findById(trabajador.getIdTrabajador()) != null) {
                model.addObject("titulo", "Error");
                model.addObject("mensaje", "El trabajador ya existe.");
                return model;
            }

            model.setViewName("ready");
            model.addObject("page", "trabajadores");

            trabajadorServices.save(trabajador);
            model.addObject("titulo", "Trabajador añadido");
            model.addObject("mensaje", "El trabajador ha sido añadido correctamente.");
        } else {
            model.setViewName("trabajadores/trabajadoresForm");
            model.addObject("titulo", "Error");
            model.addObject("mensaje", "Ha habido un error al añadir el trabajador.");
        }

        addAtributes(mod);
        return model;
    }

    // Edita un trabajador
    @PutMapping(value="/edit")
    public ModelAndView edit(@Valid Trabajador trabajador, BindingResult result, Model mod) {
        ModelAndView model = new ModelAndView();
        model.addObject("trabajador", trabajador);
        model.setViewName("ready");

        if (!result.hasErrors()) {
            trabajadorServices.save(trabajador);
            model.addObject("titulo", "Trabajador actualizado");
            model.addObject("page", "trabajadores");
            model.addObject("mensaje", "El trabajador ha sido actualizado correctamente.");
        } else {
            model.setViewName("trabajadores/trabajadoresForm");
            model.addObject("titulo", "Actualizar trabajador - " + trabajador.getIdTrabajador());
            model.addObject("error", "Errores: ");
        }

        addAtributes(mod);
        return model;
    }

    // Elimina un trabajador
    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestParam String idTrabajador) {
        ModelAndView model = new ModelAndView();
        model.setViewName("ready");
        model.addObject("titulo", "Trabajador eliminado");
        model.addObject("page", "trabajadores");
        model.addObject("mensaje", "El trabajador ha sido eliminado correctamente.");
        trabajadorServices.delete(idTrabajador);
        return model;
    }

    // Método auxiliar para añadir atributos a la vista (formularios añadir y editar)
    private void addAtributes(Model mod) {
        mod.addAttribute("id", "ID *");
        mod.addAttribute("dni", "DNI *");
        mod.addAttribute("nombre", "Nombre ");
        mod.addAttribute("apellidos", "Apellidos ");
        mod.addAttribute("especialidad", "Especialidad * ");
        mod.addAttribute("contraseña", "Contraseña *");
        mod.addAttribute("email", "Email ");
    }
}
