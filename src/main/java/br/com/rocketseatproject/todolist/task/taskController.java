package br.com.rocketseatproject.todolist.task;

import br.com.rocketseatproject.todolist.user.IUserRepository;
import br.com.rocketseatproject.todolist.user.UserModel;
import br.com.rocketseatproject.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class taskController {

    @Autowired
    private iTaskRepository taskRepository;

    @PostMapping("/createtask")
    public ResponseEntity create(@RequestBody taskModel taskmodel , HttpServletRequest request){

        var idUser = request.getAttribute("idUser");

        taskmodel.setIdUser((UUID) idUser);
        System.out.println("ID-USER: "+idUser);

        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskmodel.getStartedAt()) || currentDate.isAfter(taskmodel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de Inicio e de Fim deve ser depois do que a data atual");
        }
        if(taskmodel.getStartedAt().isAfter(taskmodel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de Inicio tem que ser antes da de término.");
        }

        var task = this.taskRepository.save(taskmodel);
        System.out.println("Task criada com ID: " + task.getId());
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/createtask")
    public List<taskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/createtask/{id}")
    public ResponseEntity update(@RequestBody taskModel taskmodel, @PathVariable UUID id, HttpServletRequest request){

        var task = this.taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        var iduser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(iduser)){
            System.out.println("taskgetuser :"+ task.getIdUser());
            System.out.println("request: "+request.getAttribute("IdUser"));
            return ResponseEntity.badRequest().body("It's not the same login...");
        }

        Utils.copyNonNullProperties(taskmodel, task);
        var userUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(userUpdated);
    }
}
