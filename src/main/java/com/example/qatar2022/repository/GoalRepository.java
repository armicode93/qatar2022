package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

  Goal findGoalByTime(long time);
}
