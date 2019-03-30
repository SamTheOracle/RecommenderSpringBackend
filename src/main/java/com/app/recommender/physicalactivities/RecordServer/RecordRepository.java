package com.app.recommender.physicalactivities.RecordServer;

import com.app.recommender.Model.PhysicalActivityRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecordRepository extends MongoRepository<PhysicalActivityRecord,String> {
    List<PhysicalActivityRecord> findByUserId(String userId);
}
