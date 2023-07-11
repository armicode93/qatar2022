package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Goal;
import com.example.qatar2022.service.GoalService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Goal")
@CrossOrigin(origins = "*")
public class GoalController {

  private final GoalService goalService;

  public GoalController(GoalService goalService) {
    this.goalService = goalService;
  }


  @PostMapping("/")
  public ResponseEntity createGoal(@RequestBody Goal goalBody) {
    if (goalBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Goal> goals = Optional.ofNullable(goalService.getGoal(goalBody.getIdGoal()));

    /* Goal goals = goalService.getGoal(goalBody.getIdGoal());
    Goal time = goalService.getTime(goalBody.getTime());

    */

    if (goals.isPresent()) {

      goalService.addGoal(goalBody);
      return ResponseEntity.ok(goalBody);
    }
    return ResponseEntity.badRequest().body("Equipe exist");
  }

  @DeleteMapping(path = "{idGoal}")
  public ResponseEntity deleteGoal(@PathVariable("idGoal") Long idGoal) {

    if (idGoal == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    } else {
      goalService.deleteGoal(idGoal);
      return ResponseEntity.ok("Deleted");
    }
  }

  @PutMapping(path = "{EquipeId}")
  public ResponseEntity updateGoal(@RequestBody Goal goalBody) {
    if (goalBody == null) {
      return ResponseEntity.badRequest().body("Empty request Body");
    }

    Goal goals = goalService.getGoal(goalBody.getIdGoal());

    if (goals != null) {
      Goal createGoal = goalService.updateGoal(goalBody.getIdGoal(), goalBody);
      return ResponseEntity.ok("Updated");
    }
    return ResponseEntity.notFound().build();
  }
}
