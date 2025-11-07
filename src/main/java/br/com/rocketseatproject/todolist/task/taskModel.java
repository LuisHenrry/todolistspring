package br.com.rocketseatproject.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name= "tb_tasks")
public class taskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    private String priority;
    @Column(length = 50)
    private String title;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        if(title.length() > 50) {
        throw new Exception("Title have more than 50 words...");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
