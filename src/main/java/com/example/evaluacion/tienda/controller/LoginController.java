package com.example.evaluacion.tienda.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    //INDICAR A DONDE SE DIRIGE UN USUARIO DESPUES DE LA AUTENTICACION
    @GetMapping("/postLogin")
    //OBTIENE EL OBJETO DEL USUARIO QUE ACABA DE INICIAR SESION EXITOSAMENTE
    public String dirigirPorRol(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String role = user.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }else if(role.equals("ROLE_EMPLEADO")){
            return "redirect:/products";
        }else if(role.equals("ROLE_CLIENTE")){
            return "pages/panelCliente";
        }
        return "redirect:/login?error";
    }

}
