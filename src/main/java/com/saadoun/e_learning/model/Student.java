package com.saadoun.e_learning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String name;
    private String email;

   @Embedded
   private Address address;

    @Builder.Default
   @OneToMany(mappedBy = "student")
   private List<Enrollment> enrollments = new ArrayList<>();

}
