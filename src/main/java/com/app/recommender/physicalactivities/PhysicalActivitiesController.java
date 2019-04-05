package com.app.recommender.physicalactivities;

import com.app.recommender.Model.PhysicalActivityRdf;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PhysicalActivitiesController {

    @Autowired
    IPhysicalActivityRdfService physicalActivityRdfService;
    @Autowired
    private JmsTemplate template;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/testpa")
    public ResponseEntity getPhysicalActivity() {
        return ResponseEntity.status(200).body("ciao");
    }

    @PostMapping(value = "/customizations")
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

    @GetMapping(value = "/customizations/all")
    public ResponseEntity getAllPhysicalActivityByUser(@RequestParam("userId") String userId) {
        List<PhysicalActivityRdf> physicalActivityRdfs;
        try {
            physicalActivityRdfs = this.physicalActivityRdfService.getAllPhysicalActivityRdf(userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.OK).body(physicalActivityRdfs);

    }

    @GetMapping(value = "/customizations/{physicalActivityId}")
    public ResponseEntity getPhRdf(@PathVariable(value = "physicalActivityId") String physicalActivityId,
                                   @RequestParam("userId") String userId) {

        PhysicalActivityRdf physicalActivityRdf;
        try {
            physicalActivityRdf = this.physicalActivityRdfService.getPhysicalActivityById(physicalActivityId, userId);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
        return ResponseEntity.status(HttpStatus.OK).body(physicalActivityRdf);

    }

    @PutMapping(value = "/customizations")
    public ResponseEntity updatePhysicalActivity(@RequestBody PhysicalActivityRdf physicalActivityRdf,
                                                 @RequestParam(value = "userId") String userId) {
        PhysicalActivityRdf physicalActivityRdfToSendBack;
        try {
            physicalActivityRdfToSendBack = physicalActivityRdfService.updatePhysicalActivityRdf(physicalActivityRdf, userId);
            DietUpdatePaMessage dietUpdateMessage = new DietUpdatePaMessage();
            dietUpdateMessage.setPhysicalActivityRdf(physicalActivityRdfToSendBack);
            template.convertAndSend("diet-updates-pa", dietUpdateMessage);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalActivityRdfToSendBack);


    }


}
