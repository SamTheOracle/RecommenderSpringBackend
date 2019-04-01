package com.app.recommender.physicalactivities;

import com.app.recommender.Model.PhysicalActivityRdf;

import java.io.FileNotFoundException;
import java.util.List;

public interface IPhysicalActivityRdfRepository {

    PhysicalActivityRdf createPhysicalActivityRdf(String userId,PhysicalActivityRdf physicalActivityRdf) throws FileNotFoundException;

    PhysicalActivityRdf updatePhysicalActivity(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException;

    List<PhysicalActivityRdf> getAllPhysicalActivityRdf(String userId) throws FileNotFoundException;

    PhysicalActivityRdf getPhysicalActivityById(String physicalActivityId, String userId);
}
