package com.example.evaluacion.tienda.controller;

import com.example.evaluacion.tienda.model.User;
import com.example.evaluacion.tienda.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //listar usuarios
    @GetMapping
    public String listUsers(
            @RequestParam(name = "buscarUsuario", required = false, defaultValue = "") String name,
            Model model) {

        List<User> users = userService.findUserByName(name);
        model.addAttribute("users", users);
        model.addAttribute("name", name);

        return "pages/userList";
    }

// registra un nuevo usuario
    @GetMapping("/formUser")
    public String formularioUsuario(Model model) {
        model.addAttribute("user", new User());
        return "pages/userForm";
    }

    // ===============================
    // GUARDAR USUARIO
    // ===============================
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user) {
        if(user.getId() != null){
            userService.updateUser(user.getId(), user);
        }else{
            userService.saveUser(user);
        }
        return "redirect:/";
    }

    // ===============================
    // FORMULARIO EDITAR USUARIO
    // ===============================
    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.buscarUsuarioByid(id);
        model.addAttribute("user", user);
        return "pages/userForm";
    }

    // ===============================
    // ELIMINAR USUARIO
    // ===============================
    @PostMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
