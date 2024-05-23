package com.apidv.apidv.controllers;

import com.apidv.apidv.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/users")
public class UsersController {
    ArrayList<User> users = new ArrayList<>();


    @GetMapping("/user/{email}")
    public User getUserByEmail(@PathVariable String email){
        if (users.isEmpty()){
            //return "No hay usuarios registrados aun";
            return new User();
        }
        Optional<User> usuarioEncontrado = users.stream().filter(usuario ->
                usuario.getEmail().equalsIgnoreCase(email)).findFirst();
        if(usuarioEncontrado.isPresent()){
            return usuarioEncontrado.get();
        }
        else{
            return new User();
        }
    }

    @GetMapping("/user/{name}/{email}/{age}/{password}")
    public String registerUserByPath (@PathVariable String name, @PathVariable String email, @PathVariable String age, @PathVariable String password){
        String validationResponseMessage = validateUserRegistration(name, email, age, password);
        if(validationResponseMessage.isBlank()){
            users.add(new User(name, email, Integer.parseInt(age), password));
            return "El usuario fue agregado correctamente";
        }
        return validationResponseMessage;

    }

    @GetMapping("/user")
    public String registerUserByQuery(@RequestParam String name, @RequestParam String email, @RequestParam String age, @RequestParam String password){
        String validationResponseMessage = validateUserRegistration(name, email, age, password);
        if(validationResponseMessage.isBlank()){
            users.add(new User(name, email, Integer.parseInt(age), password));
            return "El usuario fue agregado correctamente";
        }
        return validationResponseMessage;

    }
    @PostMapping("/user")
    public String registerUser(@RequestBody Map<String, Object> requestBody) {
        String name;
        String email;
        String age;
        String password;
        try {
            name = requestBody.get("name").toString();
            email = requestBody.get("email").toString();
            age = requestBody.get("age").toString();
            password = requestBody.get("password").toString();
        }catch (Exception e){
            return "No han sido recibido los datos en su totalidad";
        }

        String validationResponseMessage = validateUserRegistration(name, email, age, password);
        if(validationResponseMessage.isBlank()){
            users.add(new User(name, email, Integer.parseInt(age), password));
            return "El usuario fue agregado correctamente";
        }
        return validationResponseMessage;
    }




    private String validateUserRegistration(String name, String email, String age, String password){
        if (name.isBlank() || email.isBlank() || age.isBlank() || password.isBlank() ){
            return "Por favor verifique que no esté enviando ningun valor vacío";
        }
        Integer parsedAge = 0;
        try {
            parsedAge = Integer.parseInt(age);
        }catch (NumberFormatException e){
            return "La edad debe ser un valor numerico";
        }
        if (password.length() < 8 || password.length() > 16){
            return "La contraseña debe contener entre 8 y 16 caracteres";
        }
        if (!users.isEmpty()){
            if (users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().isPresent()){
                return "Ya existe un usuario registrado con este mail";
            }
        }

        return "";
    }
















}
