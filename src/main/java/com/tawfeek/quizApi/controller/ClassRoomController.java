package com.tawfeek.quizApi.controller;


import com.tawfeek.quizApi.model.classRoom.AddingUserToClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomResponseDTO;
import com.tawfeek.quizApi.model.classRoom.UserAddingToClassRoomStatusResponseDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import com.tawfeek.quizApi.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @PostMapping("")
    public ResponseEntity<ClassRoomResponseDTO> createClassRoom(@RequestBody ClassRoomRequestDTO classRoomRequestDTO) {
        ClassRoomResponseDTO classRoomResponseDTO = classRoomService.createClassRoom(classRoomRequestDTO);
        return new ResponseEntity<>(classRoomResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/owned")
    public ResponseEntity<List<ClassRoomResponseDTO>> getOwnedClassRooms() {
        List<ClassRoomResponseDTO> classRoomResponseDTOList = classRoomService.getOwnedClassRooms();
        return new ResponseEntity<>(classRoomResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/member-of")
    public ResponseEntity<List<ClassRoomResponseDTO>> getClassRoomsUserMemberOf() {
        List<ClassRoomResponseDTO> classRoomResponseDTOList = classRoomService.getClassRoomsUserMemberOf();
        return new ResponseEntity<>(classRoomResponseDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{classRoomId}")
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Long classRoomId) {
        classRoomService.deleteClassRoom(classRoomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{classRoomId}/members")
    public ResponseEntity<List<UserResponseDTO>> getClassRoomMembers(@PathVariable Long classRoomId) {
        List<UserResponseDTO> userResponseDTOList = classRoomService.getClassRoomMembers(classRoomId);
        return new ResponseEntity<>(userResponseDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{classRoomId}/members/{memberId}")
    public ResponseEntity<Void> deleteClassRoomMember(@PathVariable Long classRoomId, @PathVariable Long memberId) {
        classRoomService.deleteClassRoomMember(classRoomId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{classRoomId}/members")
    public ResponseEntity<UserAddingToClassRoomStatusResponseDTO> addMemberToClassRoom(@PathVariable Long classRoomId, @RequestBody AddingUserToClassRoomRequestDTO addingUserToClassRoomRequestDTO) {
        UserAddingToClassRoomStatusResponseDTO userAddingToClassRoomStatusResponseDTO = classRoomService.addMemberToClassRoom(classRoomId, addingUserToClassRoomRequestDTO);
        return new ResponseEntity<>(userAddingToClassRoomStatusResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{classRoomId}/members/batch")
    public ResponseEntity<List<UserAddingToClassRoomStatusResponseDTO>> addMembersToClassRoom(@PathVariable Long classRoomId, @RequestBody List<AddingUserToClassRoomRequestDTO> emails) {
        List<UserAddingToClassRoomStatusResponseDTO> userAddingToClassRoomStatusResponseDTOList = classRoomService.addMembersToClassRoom(classRoomId, emails);
        return new ResponseEntity<>(userAddingToClassRoomStatusResponseDTOList, HttpStatus.CREATED);
    }

}
