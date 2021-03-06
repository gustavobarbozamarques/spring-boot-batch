package br.com.gustavobarbozamarques.springbootbatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}
