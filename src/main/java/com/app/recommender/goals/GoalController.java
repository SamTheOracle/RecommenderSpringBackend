package com.app.recommender.goals;

import com.app.recommender.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class GoalController {
    @Autowired
    private GoalService service;

    @Autowired
    private JmsTemplate template;
    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping(value = "/testgoals")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.OK).body(new Random().nextInt());
    }

    @GetMapping(value = "/weekly")
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

    @PostMapping(value = "/weekly")
    public ResponseEntity postGoal(@RequestBody Goal goal) {
        Goal g = this.service.createNewGoal(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }
    @PutMapping(value = "/weekly")
    public ResponseEntity updateGoal(@RequestBody Goal goal) {
        Goal g = null;
        try {
            g = this.service.updateGoal(goal);
            DietUpdateGoalMessage goalMessage = new DietUpdateGoalMessage();
            goalMessage.setGoal(g);
            List<ServiceInstance> instances = this.discoveryClient.getInstances("diets-microservice");
            if(!instances.isEmpty()){
                ServiceInstance instance = instances.stream().findFirst().get();
                RestTemplate dietClient = new RestTemplate();





                String uri = "http://"+instance.getHost()+":"+instance.getPort()+"/goals";

                HttpEntity<Goal> entity = new HttpEntity<>(g);

                ResponseEntity<Goal> response = dietClient.exchange(uri, HttpMethod.PUT, entity, Goal.class);
                g = response.getBody();

            }
            //this.template.convertAndSend("diet-updates-goal",goalMessage);
        } catch (NoGoalFoundException | GoalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }
    @PutMapping(value="/weekly/{goalId}/adherence")
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
