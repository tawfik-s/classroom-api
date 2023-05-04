package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClassRoomRepositoryTest {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;
    private ClassRoom classRoom;

    @BeforeEach
    public void setUp() {
        user = new User();
        userRepository.save(user);
        classRoom = new ClassRoom();
        classRoomRepository.save(classRoom);
    }

    @AfterEach
    public void tearDown() {
        classRoomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testFindClassRoomUserMemberOf() {
        classRoom.addMember(user);
        classRoomRepository.save(classRoom);

        Optional<List<ClassRoom>> result = classRoomRepository.findClassRoomUserMemberOf(user);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(classRoom, result.get().get(0));
    }

    @Test
    public void testIsUserMemberOfClassRoom() {
        classRoom.addMember(user);
        classRoomRepository.save(classRoom);

        boolean result = classRoomRepository.isUserMemberOfClassRoom(classRoom.getId(), user.getId());
        assertTrue(result);
    }

    @Test
    public void testIsUserAdminOfClassRoom() {
        user.addClassRoom(classRoom);
        classRoomRepository.save(classRoom);

        boolean result = classRoomRepository.isUserAdminOfClassRoom(classRoom.getId(), user.getId());
        assertTrue(result);
    }

    @Test
    public void testGetClassRoomMembers() {
        User user1 = new User();
        User user2 = new User();
        userRepository.save(user1);
        userRepository.save(user2);
        classRoom.addMember(user1);
        classRoom.addMember(user2);
        classRoomRepository.save(classRoom);

        Optional<List<User>> result = classRoomRepository.getClassRoomMembers(classRoom.getId());
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertTrue(result.get().containsAll(Arrays.asList(user1, user2)));
    }

    @Test
    public void testDeleteMemberFromClassRoom() {
        classRoom.addMember(user);
        classRoomRepository.save(classRoom);

        Long x=classRoomRepository.deleteByIdAndMembers_Id(classRoom.getId(), user.getId());

        Optional<ClassRoom> result = classRoomRepository.findById(classRoom.getId());
        assertEquals(1,x);
    }
}
