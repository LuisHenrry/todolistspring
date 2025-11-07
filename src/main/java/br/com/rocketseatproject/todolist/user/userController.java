package br.com.rocketseatproject.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/mostrar")
    public String mostrar(){

        return "Mostrando";
    }
    @PostMapping("/testeUser")
    public ResponseEntity<String> teste(HttpServletRequest request){
        String ip = request.getRemoteAddr(); // pega o IP do cliente
        String userAgent = request.getHeader("User-Agent"); // pega o navegador/sistema
        System.out.println("Requisição feita do IP: " + ip);
        System.out.println("User-Agent: " + userAgent);

        return ResponseEntity.ok("Task criada com sucesso!");
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel usermodel) {

        // Verifica se o usuário já existe
        var user = this.userRepository.findByname(usermodel.getName());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe esse usuário");
        }
        var passwordModified = BCrypt.withDefaults().hashToString(12, usermodel.getPassword().toCharArray());
        usermodel.setPassword(passwordModified);
        // Salva no banco
        var userCreated = this.userRepository.save(usermodel);

        // Retorna o usuário criado
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }



}
