package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requesterId;

    private Long todoId;

    private Long managerUserId;

    private String message;

    private String status;

    public Log(Long requesterId, Long todoId, Long managerUserId, String message, String status) {
        this.requesterId = requesterId;
        this.todoId = todoId;
        this.managerUserId = managerUserId;
        this.message = message;
        this.status = status;
    }
}
