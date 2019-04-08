package com.app.recommender.records;

import com.app.recommender.Model.PhysicalActivityRdf;
import com.app.recommender.Model.PhysicalActivityRecord;
import com.app.recommender.Model.RecordsNotFoundException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/testrecords")
    public double test() {
        return 12.45;
    }

    @PostMapping(value = "/{userId}/diets/{dietId}/activities/{physicalActivityId}")
    public ResponseEntity postRecord(@PathVariable(value = "userId") String userId,
                                     @PathVariable(value = "physicalActivityId") String physicalActivityId,
                                     @PathVariable(value = "dietId") String dietId,
                                     @RequestBody PhysicalActivityRecord record) {
        record.setUserId(userId);
        record.setPhysicalActivityId(physicalActivityId);
        record.setDietId(dietId);

        List<ServiceInstance> instances = discoveryClient.getInstances("physicalactivity-microservice");
        if (!instances.isEmpty()) {
            ServiceInstance instance = instances.stream().findFirst().get();
            RestTemplate dietClient = new RestTemplate();


            String uri = "http://" + instance.getHost() + ":" + instance.getPort() + "/customizations/" + physicalActivityId+"?userId="+userId;


            ResponseEntity<PhysicalActivityRdf> response = dietClient.getForEntity(uri, PhysicalActivityRdf.class);
            PhysicalActivityRdf physicalActivityRdf = response.getBody();
            if (physicalActivityRdf != null) {
                LocalDateTime start = record.getSessionTimeStart();
                LocalDateTime end = record.getSessionTimeEnd();
                double timeDiffHour = Math.abs(end.getHour() - start.getHour());
                double timeDiffMinute = Math.abs(end.getMinute() - start.getMinute());
                double timeDiffSecond = Math.abs(end.getSecond()-start.getSecond());
                double caloriesHourDiff = physicalActivityRdf.getCaloriesPerHour() * timeDiffHour;
                double caloriesMinuteDiff = physicalActivityRdf.getCaloriesPerHour() * timeDiffMinute / 60;
                double caloriesSecondDiff = physicalActivityRdf.getCaloriesPerHour() *timeDiffSecond/3600;
                record.setBurntCalories(caloriesHourDiff + caloriesMinuteDiff + caloriesSecondDiff);
            }

            PhysicalActivityRecord physicalActivityRecord = this.recordService.createNewPhysicalActivityRecord(record);
            return ResponseEntity.status(HttpStatus.CREATED).body(physicalActivityRecord);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No instances found");
        }
    }

    @GetMapping(value = "/{userId}/activities/{physicalActivityId}")
    public ResponseEntity getRecordsAmongDates(@PathVariable(value = "userId") String userId,
                                               @PathVariable(value = "physicalActivityId") String physicalActivityId,
                                               @RequestParam(value = "startDate") String startDate,
                                               @RequestParam(value = "endDate") String endDate,
                                               @RequestParam(value = "dietId") String dietId) {
        List<PhysicalActivityRecord> records;
        try {

            records = this.recordService.getAllRecordsBetweenDates(userId, startDate, endDate, physicalActivityId, dietId);
        } catch (RecordsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(records);
    }
}
