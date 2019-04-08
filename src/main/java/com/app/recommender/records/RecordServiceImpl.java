package com.app.recommender.records;

import com.app.recommender.Model.PhysicalActivityRecord;
import com.app.recommender.Model.RecordsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public PhysicalActivityRecord createNewPhysicalActivityRecord(PhysicalActivityRecord record) {
//        LocalDateTime start = record.getSessionTimeStart();
//        LocalDateTime end = record.getSessionTimeEnd();
//        start.atTime()
//        record.setSessionTimeStart();
        PhysicalActivityRecord physicalActivityRecord = this.recordRepository.insert(record);
        return physicalActivityRecord;
    }

    @Override
    public List<PhysicalActivityRecord> getAllRecordsBetweenDates(String userId, String startDate, String endDate, String physicalActivityId, String dietId) throws RecordsNotFoundException {
        List<PhysicalActivityRecord> records = this.recordRepository.findByUserId(userId);
        if (records.isEmpty()) {
            throw new RecordsNotFoundException("Error. Records for user " + userId + " not found");
        }
        records = records.stream().filter(record -> record.getDietId().equalsIgnoreCase(dietId)).collect(Collectors.toList());
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime localDateStart = LocalDateTime.parse(startDate, simpleDateFormat);
        LocalDateTime localDateEnd = LocalDateTime.parse(endDate,simpleDateFormat);


        List<PhysicalActivityRecord> recordsBetweenDates = records.stream().filter(record -> record.getSessionTimeStart().isAfter(localDateStart)
                && record.getSessionTimeEnd().isBefore(localDateEnd)
                && record.getPhysicalActivityId().equalsIgnoreCase(physicalActivityId)).sorted(Comparator.comparing(PhysicalActivityRecord::getSessionTimeStart)
                .reversed()).collect(Collectors.toList());
        return recordsBetweenDates;
    }
}
