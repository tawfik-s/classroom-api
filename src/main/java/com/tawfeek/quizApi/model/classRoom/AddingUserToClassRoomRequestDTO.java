package com.tawfeek.quizApi.model.classRoom;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddingUserToClassRoomRequestDTO {

    @NotNull
    @Size(min = 2,max=70)
    private String memberEmail;
}
