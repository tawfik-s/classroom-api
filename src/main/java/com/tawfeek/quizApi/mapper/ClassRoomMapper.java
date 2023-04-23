package com.tawfeek.quizApi.mapper;

import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.model.classRoom.ClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomResponseDTO;

public interface ClassRoomMapper {

    public ClassRoomResponseDTO toDTO(ClassRoom classRoom);

    public ClassRoom toEntity(ClassRoomRequestDTO classRoomRequestDTO);
}
