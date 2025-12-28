package com.example.evaluacion.tienda.service;


import com.example.evaluacion.tienda.model.Product;
import com.example.evaluacion.tienda.model.User;
import com.example.evaluacion.tienda.repository.UserRepository;
import com.example.evaluacion.tienda.roles.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JsonMapper.Builder builder;

    //leer
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //buscar por id
    public Optional<User> buscarUsuarioByid(Long id) {
        return userRepository.findById(id);
    }

    //buscar por nombre
    public List<User> findUserByName(String name) {
        if (name == null || name.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.findByNameContainingIgnoreCase(name);
        }
    }

    //guardar usuario

    public User saveUser(User user) {
        //Encriptar contraseña antes de guardar el usuario
        String passwordEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);
        //Asignar el rol de cliente por defoult a todos los usuraios que se registren
        user.setRol(Rol.ROLE_CLIENTE);
        return userRepository.save(user);
    }

    //ACTUALIZAR
    public User updateUser(Long id, User user) {
       User usuarioExistente = buscarUsuarioByid(id).orElseThrow(() -> new RuntimeException("Usuario no existente"));

      usuarioExistente.setName(user.getName());
      usuarioExistente.setUsername(user.getUsername());
      //Actualizacion del password solo si el usuraio la modifica
        if(user.getPassword() != null && !user.getPassword().trim().isEmpty()){
            usuarioExistente.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(usuarioExistente);
    }

    //eliminar
    public void deleteUserById(Long id) {
        User user = buscarUsuarioByid(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Uusuario  no encontrado"));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar en la base de datos el usuraio que se esta autenticando
        //Si no exite se lanza una excepcion para denegar el acceso
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        //Usar metodo builder para contruir el objeto que sprint security
        //identifica como ususrio autenticado
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRol().name())
                .build();

    }
}
