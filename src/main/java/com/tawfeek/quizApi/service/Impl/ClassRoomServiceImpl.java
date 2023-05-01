package com.tawfeek.quizApi.service.Impl;

import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.entity.User;
import com.tawfeek.quizApi.exception.ConflictException;
import com.tawfeek.quizApi.exception.RecordNotFoundException;
import com.tawfeek.quizApi.mapper.ClassRoomMapper;
import com.tawfeek.quizApi.mapper.UserMapper;
import com.tawfeek.quizApi.model.classRoom.ClassRoomRequestDTO;
import com.tawfeek.quizApi.model.classRoom.ClassRoomResponseDTO;
import com.tawfeek.quizApi.model.classRoom.UserAddingToClassRoomStatusResponseDTO;
import com.tawfeek.quizApi.model.user.UserResponseDTO;
import com.tawfeek.quizApi.repository.ClassRoomRepository;
import com.tawfeek.quizApi.repository.UserRepository;
import com.tawfeek.quizApi.service.ClassRoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRoomMapper classRoomMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public ClassRoomResponseDTO createClassRoom(ClassRoomRequestDTO classRoomRequestDTO) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow();
        ClassRoom classRoom = classRoomMapper.toEntity(classRoomRequestDTO);
        classRoomRepository.save(classRoom);
        user.getClassRooms().add(classRoom);
        userRepository.save(user);
        return classRoomMapper.toDTO(classRoom);
    }

    @Override
    public List<ClassRoomResponseDTO> getOwnedClassRooms() {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        return userRepository.findByEmail(email).orElseThrow()
                .getClassRooms().stream()
                .map(classRoom -> classRoomMapper.toDTO(classRoom))
                .toList();
    }

    @Override
    public List<ClassRoomResponseDTO> getClassRoomsUserMemberOf() {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow();
        return classRoomRepository.findClassRoomUserMemberOf(user)
                .orElseThrow().stream()
                .map(classRoom ->
                        classRoomMapper.toDTO(classRoom))
                .toList();
    }

    @Override
    public void deleteClassRoom(Long classRoomId) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow();
        if (classRoomRepository.isUserAdminOfClassRoom(classRoomId, user.getId())) {
            classRoomRepository.deleteById(classRoomId);
        } else {
            throw new RecordNotFoundException("not find ClassRoom in your class rooms");
        }

    }

    @Override
    public List<UserResponseDTO> getClassRoomMembers(Long classRoomId) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow();
        if (classRoomRepository.isUserAdminOfClassRoom(classRoomId, user.getId()) ||
                classRoomRepository.isUserMemberOfClassRoom(classRoomId, user.getId())) {
            return classRoomRepository.getClassRoomMembers(classRoomId)
                    .orElseThrow().stream()
                    .map(u -> userMapper.toDTO(u)).toList();
        } else {
            throw new RecordNotFoundException("not find ClassRoom in your class rooms");
        }
    }

    @Override
    public void deleteClassRoomMember(Long classRoomId, Long memberId) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        var user = userRepository.findByEmail(email).orElseThrow();
        if (!classRoomRepository.isUserAdminOfClassRoom(classRoomId, user.getId())) {
            throw new RecordNotFoundException("you are not the class room owner");
        }
        classRoomRepository.deleteMemberFromClassRoom(classRoomId,memberId);
    }

    @Override
    public UserAddingToClassRoomStatusResponseDTO addMemberToClassRoom(Long classRoomId, String memberEmail) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        var user = userRepository.findByEmail(email).orElseThrow();
        if (!classRoomRepository.isUserAdminOfClassRoom(classRoomId, user.getId())) {
            throw new RecordNotFoundException("you are not the class room owner");
        }
        var classRoom = classRoomRepository.findById(classRoomId).orElseThrow();
        var member = userRepository.findByEmail(memberEmail).orElseThrow();
        classRoom.getMembers().add(member);
        classRoomRepository.save(classRoom);
        return new UserAddingToClassRoomStatusResponseDTO(member.getEmail(), "done");
    }

    @Override
    public List<UserAddingToClassRoomStatusResponseDTO> addMembersToClassRoom(Long classRoomId, List<String> emails) {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        var user = userRepository.findByEmail(email).orElseThrow();
        if (!classRoomRepository.isUserAdminOfClassRoom(classRoomId, user.getId())) {
            throw new RecordNotFoundException("you are not the class room owner");
        }
        var classRoom = classRoomRepository.findById(classRoomId).orElseThrow();
        List<UserAddingToClassRoomStatusResponseDTO> res = new ArrayList<>();
        for (var memberEmail : emails) {
            try {
                var member = userRepository.findByEmail(memberEmail).orElseThrow();
                classRoom.getMembers().add(member);
                res.add(new UserAddingToClassRoomStatusResponseDTO(memberEmail, "done"));
            } catch (Exception ex) {
                res.add(new UserAddingToClassRoomStatusResponseDTO(memberEmail, "fail"));

            }
        }
        classRoomRepository.save(classRoom);

        return res;
    }
}
