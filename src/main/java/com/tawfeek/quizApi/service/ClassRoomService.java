package com.tawfeek.quizApi.service;

import com.tawfeek.quizApi.model.classRoom.AddingUserToClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomResponseDTO;
import com.tawfeek.quizApi.model.classRoom.UserAddingToClassRoomStatusResponseDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;

import java.util.List;

public interface ClassRoomService {

    public ClassRoomResponseDTO createClassRoom(ClassRoomRequestDTO classRoomRequestDTO);

    public List<ClassRoomResponseDTO> getOwnedClassRooms();

    public List<ClassRoomResponseDTO> getClassRoomsUserMemberOf();

    public void deleteClassRoom(Long classRoomId);

    public List<UserResponseDTO> getClassRoomMembers(Long classRoomId);

    public void deleteClassRoomMember(Long classRoomId, Long memberId);

    public UserAddingToClassRoomStatusResponseDTO addMemberToClassRoom(Long classRoomId, AddingUserToClassRoomRequestDTO addingUserToClassRoomRequestDTO);

    public List<UserAddingToClassRoomStatusResponseDTO> addMembersToClassRoom(Long classRoomId,List<AddingUserToClassRoomRequestDTO> emails);

}
