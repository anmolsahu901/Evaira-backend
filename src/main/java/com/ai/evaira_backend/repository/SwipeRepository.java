package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    List<Swipe> findAllByUserId(Long userId);
}

