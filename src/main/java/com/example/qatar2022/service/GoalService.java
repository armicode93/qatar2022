package com.example.qatar2022.service;

import com.example.qatar2022.entities.Goal;
import com.example.qatar2022.repository.GoalRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

  private final GoalRepository goalRepository;

  public GoalService(GoalRepository goalRepository) {
    this.goalRepository = goalRepository;
  }

  public List<Goal> getAllGoals() {
    List<Goal> goals = new ArrayList<>();

    goalRepository.findAll().forEach(goals::add);

    return goals;
  }

  public Goal getGoal(Long idGoal) {
    return goalRepository.findById(idGoal).orElse(null);
  }

  public Goal getTime(Long time) {
    return goalRepository.findGoalByTime(time);
  }

  public void addGoal(Goal goal) {
    goalRepository.save(goal);
  }

  public Goal updateGoal(Long idGoal, Goal goal) {
    goalRepository.save(goal);
    return goal;
  }

  public void deleteGoal(Long idGoal) {
    goalRepository.deleteById(idGoal);
  }
}
