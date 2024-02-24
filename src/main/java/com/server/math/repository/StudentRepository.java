package com.server.math.repository;

import com.server.math.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByTelegramId(Long telegramId);

    List<Student> findByClassNumber(Integer classNumber);

    List<Student> findByClassNumberAndClassLetter(Integer classNumber, String classLetter);

    @Transactional
    @Modifying
    @Query("update Student s set s.isBan = ?1 where s.telegramId = ?2")
    int updateIsBanByTelegramId(boolean isBan, Long telegramId);

    boolean existsByNameAndLastNameAndClassLetterAndClassNumber(String name, String lastName, String classLetter, Integer classNumber);

    boolean existsByTelegramId(Long telegramId);

    @Transactional
    List<Student> deleteByTelegramId(Long telegramId);
}