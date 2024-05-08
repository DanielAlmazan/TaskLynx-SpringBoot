package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajadorServices;
import edu.tasklynx.tasklynxspringboot.models.services.TrabajoServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public String verTrabajo(Model model, @PathVariable String id) {
        model.addAttribute("titulo", "Trabajo " + id);
        model.addAttribute("trabajo", trabajoServices.findById(id));
        return "trabajos/trabajoDetalle";
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
        model.addAttribute("trabajadores", trabajadorServices.findAll());
        model.addAttribute("editar", true);
        return "trabajos/trabajoFormulario";
    }

    @RequestMapping("/processForm")
    public ModelAndView processForm(@Valid Trabajo trabajo, BindingResult result, @RequestParam String _method) {
        if(_method.equals("POST")) return createTrabajo(trabajo, result);
        else return editTrabajo(trabajo, result);
    }

    private ModelAndView createTrabajo(@Valid Trabajo trabajo, BindingResult result) {
        ModelAndView model = new ModelAndView();
        boolean exists = false;
        model.addObject("trabajo", trabajo);
        model.addObject("editar", false);

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
                model.addObject("page", "trabajos");
                model.addObject("mensaje", "El trabajo ha sido añadido correctamente.");
            } else {
                model.setViewName("trabajos/trabajoFormulario");
                model.addObject("titulo", "Nuevo Trabajo");
                model.addObject("error", "Error:");
                model.addObject("trabajadores", trabajadorServices.findAll());
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

    private ModelAndView editTrabajo(@Valid Trabajo trabajo, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.addObject("trabajo", trabajo);
        model.addObject("editar", true);

        Trabajo currentTrabajo = trabajoServices.findById(trabajo.getCodTrabajo());

        if (trabajo.getFecIni() == null && currentTrabajo != null)
            trabajo.setFecIni(currentTrabajo.getFecIni());

        if(!result.hasErrors()) {
            if (currentTrabajo == null) {
                model.setViewName("trabajos/trabajoFormulario");
                model.addObject("titulo", "Editar trabajo - " + trabajo.getCodTrabajo());
                model.addObject("error", "Error:");
                model.addObject("trabajadores", trabajadorServices.findAll());
                model.addObject("mensajeError", "El trabajo con código '" + trabajo.getCodTrabajo()
                        + "' no existe.");
            } else {
                // Update values
                currentTrabajo.setCategoria(trabajo.getCategoria());
                currentTrabajo.setDescripcion(trabajo.getDescripcion());
                currentTrabajo.setFecIni(trabajo.getFecIni());
                currentTrabajo.setFecFin(trabajo.getFecFin());
                currentTrabajo.setTiempo(trabajo.getTiempo());
                currentTrabajo.setIdTrabajador(trabajo.getIdTrabajador());
                currentTrabajo.setPrioridad(trabajo.getPrioridad());

                trabajoServices.save(currentTrabajo);

                model.setViewName("ready");
                model.addObject("titulo", "Trabajo actualizado");
                model.addObject("page", "trabajos");
                model.addObject("mensaje", "El trabajo ha sido actualizado correctamente.");
            }
        } else {
            model.setViewName("trabajos/trabajoFormulario");
            model.addObject("titulo", "Editar trabajo - " + trabajo.getCodTrabajo());
            model.addObject("error", "Errores:");
            model.addObject("trabajadores", trabajadorServices.findAll());
        }

        return model;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam String codTrabajo) {
        ModelAndView model = new ModelAndView();
        model.setViewName("ready");
        model.addObject("titulo", "Trabajo eliminado");
        model.addObject("page", "trabajos");
        model.addObject("mensaje", "El trabajo ha sido eliminado correctamente.");
        trabajoServices.delete(codTrabajo);
        return model;
    }
}
