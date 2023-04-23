package com.tawfeek.quizApi.mapper.Impl;

import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.mapper.ClassRoomMapper;
import com.tawfeek.quizApi.model.classRoom.ClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ClassRoomMapperImpl implements ClassRoomMapper {


    @Override
    public ClassRoomResponseDTO toDTO(ClassRoom classRoom) {
        return new ClassRoomResponseDTO(classRoom.getId(),classRoom.getName());
    }

    @Override
    public ClassRoom toEntity(ClassRoomRequestDTO classRoomRequestDTO) {
        return new ClassRoom(classRoomRequestDTO.getName());
    }
}
