package com.saadoun.e_learning.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentReqDto {
      private String name;
      private String email;
      private String city;
      private String country;
      private String street;
}
