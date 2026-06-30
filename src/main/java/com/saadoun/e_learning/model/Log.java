package com.saadoun.e_learning.model;

import com.saadoun.e_learning.model.enums.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long model_id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ActionType type;

}
