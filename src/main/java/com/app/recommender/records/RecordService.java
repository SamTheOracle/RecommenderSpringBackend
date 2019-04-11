package com.app.recommender.records;

import com.app.recommender.Model.PhysicalActivityRecord;
import com.app.recommender.Model.RecordsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordService {
    PhysicalActivityRecord createNewPhysicalActivityRecord(PhysicalActivityRecord record);

    List<PhysicalActivityRecord> getAllRecordsBetweenDates(String userId, String startDate, String endDate, String physicalActivityId, String dietId) throws RecordsNotFoundException;

    double getTotalBurntCaloriesInPeriod(String userId, String startDate, String endDate, String physicalActivityId, String dietId) throws RecordsNotFoundException;
}
