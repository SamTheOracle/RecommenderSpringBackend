package com.app.recommender.physicalactivities.ResourceRdfServer;

import com.app.recommender.Model.PhysicalActivityRdf;
import com.app.recommender.Model.DietUpdates.DietUpdateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@CrossOrigin
@RestController
public class PhysicalActivitiesController {

    @Autowired
    IPhysicalActivityRdfService physicalActivityRdfService;
    @Autowired
    private JmsTemplate template;
    @GetMapping(value = "/testpa")
    public ResponseEntity getPhysicalActivity() {
        return ResponseEntity.status(200).body("ciao");
    }

    @PostMapping(value = "/")
    public ResponseEntity createNewPhysicalActivityRdf(@RequestBody PhysicalActivityRdf physicalActivityRdf,
                                                       @RequestParam(value = "userId") String userId) {

        PhysicalActivityRdf physicalActivityRdfToSendBack;
        try {
            physicalActivityRdfToSendBack = this.physicalActivityRdfService.addNewPhysicalActivity(physicalActivityRdf, userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalActivityRdfToSendBack);

    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllPhysicalActivityByUser(@RequestParam("userId") String userId) {
        List<PhysicalActivityRdf> physicalActivityRdfs;
        try {
            physicalActivityRdfs = this.physicalActivityRdfService.getAllPhysicalActivityRdf(userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.OK).body(physicalActivityRdfs);

    }

    @PutMapping(value = "/")
    public ResponseEntity updatePhysicalActivity(@RequestBody PhysicalActivityRdf physicalActivityRdf,

                                                 @RequestParam(value = "userId") String userId){
        PhysicalActivityRdf physicalActivityRdfToSendBack;
        try {
            physicalActivityRdfToSendBack = physicalActivityRdfService.updatePhysicalActivityRdf(physicalActivityRdf,userId);
            DietUpdatePaMessage dietUpdateMessage = new DietUpdatePaMessage();
            dietUpdateMessage.setPhysicalActivityRdf(physicalActivityRdfToSendBack);
            template.convertAndSend("diet-updates-pa", dietUpdateMessage);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalActivityRdfToSendBack);


    }


}
