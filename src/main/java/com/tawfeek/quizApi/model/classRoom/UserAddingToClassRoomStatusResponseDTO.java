package com.tawfeek.quizApi.model.classRoom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddingToClassRoomStatusResponseDTO {

    private String email;

    private String status;
}
