package com.app.recommender.physicalactivities.GoalServer;

import com.app.recommender.Model.Goal;
import com.app.recommender.Model.GoalNotFoundException;
import com.app.recommender.Model.NoGoalFoundException;
import com.app.recommender.Model.PhysicalActivityRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class GoalController {
    @Autowired
    private GoalService service;

    @Autowired
    private JmsTemplate template;
    @GetMapping(value = "/testgoals")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.OK).body(new Random().nextInt());
    }

    @GetMapping(value = "/")
    public ResponseEntity getGoal(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "dietId") String dietId) {
        Goal toSendBack;
        try {
            toSendBack = this.service.getGoal(dietId, userId);
        } catch (GoalNotFoundException | NoGoalFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(toSendBack);
    }

    @PostMapping(value = "/")
    public ResponseEntity postGoal(@RequestBody Goal goal) {
        Goal g = this.service.createNewGoal(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }
    @PutMapping(value = "/")
    public ResponseEntity updateGoal(@RequestBody Goal goal) {
        Goal g = null;
        try {
            g = this.service.updateGoal(goal);
            DietUpdateGoalMessage goalMessage = new DietUpdateGoalMessage();
            goalMessage.setGoal(g);
            this.template.convertAndSend("diet-updates-goal",goalMessage);
        } catch (NoGoalFoundException | GoalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }
    @PutMapping(value="/{goalId}/adherence")
    public ResponseEntity updateAdherence(@PathVariable(value = "goalId") String goalId,
                                          @RequestBody List<PhysicalActivityRecord> goals){
        Goal toSendBack;
        try {
            toSendBack = this.service.updateGoalAdherence(goalId,goals);
        } catch (GoalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toSendBack);
    }

}
