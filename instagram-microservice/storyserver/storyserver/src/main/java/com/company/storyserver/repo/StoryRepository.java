package com.company.storyserver.repo;

import com.company.storyserver.modal.Story;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    @Query("select s from Story s where s.user.id=:userId")
    List<Story> findAllStoryByUserId(@Param("userId") Integer userId);
}
