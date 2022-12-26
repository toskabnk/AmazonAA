package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacia")
    @NotNull(message = "La contraseña es obligatoria")
    private String password;
    private String address;
    private String phoneNumber;
    private LocalDate birthdate;
}
