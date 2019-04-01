package com.app.recommender.physicalactivities;

import com.app.recommender.Model.PhysicalActivityRdf;

import java.io.FileNotFoundException;
import java.util.List;

public interface IPhysicalActivityRdfService {

    PhysicalActivityRdf addNewPhysicalActivity(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException;

    PhysicalActivityRdf updatePhysicalActivityRdf(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException;

    List<PhysicalActivityRdf> getAllPhysicalActivityRdf(String userId) throws FileNotFoundException;

    PhysicalActivityRdf getPhysicalActivityById(String physicalActivityId, String userId);


}
