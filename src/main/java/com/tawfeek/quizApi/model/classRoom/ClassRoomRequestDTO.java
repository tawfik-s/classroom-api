package com.tawfeek.quizApi.model.classRoom;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * contain data required to create classroom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomRequestDTO {

    @NotNull
    @Size(min = 2,max=70)
    private String name;

}
