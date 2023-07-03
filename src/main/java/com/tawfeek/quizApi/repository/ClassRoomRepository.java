package com.tawfeek.quizApi.repository;

import com.tawfeek.quizApi.entity.ClassRoom;
import com.tawfeek.quizApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

  @Query("select c from ClassRoom c where :user MEMBER OF c.members ")
  Optional<List<ClassRoom>> findClassRoomUserMemberOf(@Param("user") User user);

  @Query(
      "SELECT COUNT(c) > 0 FROM ClassRoom c JOIN c.members m "
          + "WHERE c.id = :classRoomId AND m.id = :userId")
  boolean isUserMemberOfClassRoom(
      @Param("classRoomId") Long classRoomId, @Param("userId") Long userId);

  @Query(
      "SELECT COUNT(u)>0 FROM User u join u.classRooms c "
          + "where c.id = :classRoomId and u.id = :userId")
  boolean isUserAdminOfClassRoom(
      @Param("classRoomId") Long classRoomId, @Param("userId") Long userId);

  @Query("SELECT c.members from ClassRoom c")
  Optional<List<User>> getClassRoomMembers(@Param("classRoomId") Long classRoomId);

  long deleteByIdAndMembers_Id(Long classRoomId, Long userId);

  @Modifying
  @Query("DELETE FROM ClassRoom c WHERE c.id = :classroomId AND :member MEMBER OF c.members")
  void deleteMemberFromClassRoom(
      @Param("classroomId") Long classroomId, @Param("member") User member);
}
