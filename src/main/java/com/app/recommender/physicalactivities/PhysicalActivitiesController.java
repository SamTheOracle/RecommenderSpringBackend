package com.app.recommender.physicalactivities;

import com.app.recommender.Model.Cycling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class PhysicalActivitiesController {

    @GetMapping(value = "/{physicalActivityName}")
    public ResponseEntity getPhysicalActivity(@PathVariable(value = "physicalActivityName") String physicalActivityName) {
        return ResponseEntity.status(200).body(new Cycling());
    }

    @GetMapping(value = "/uniquetestmapping")
    public int test(){
        return 2;
    }
}
