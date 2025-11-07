package br.com.rocketseatproject.todolist.task;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


     public interface iTaskRepository extends JpaRepository<taskModel, UUID> {
     List<taskModel> findByIdUser(UUID idUser);

}
