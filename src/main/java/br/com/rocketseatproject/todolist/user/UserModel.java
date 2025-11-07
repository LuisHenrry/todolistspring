package br.com.rocketseatproject.todolist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="tb_users")
public class UserModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique =true)
    private String name;
    private int age;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // getters e setters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
