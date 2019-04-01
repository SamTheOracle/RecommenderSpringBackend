package com.app.recommender.physicalactivities;

import com.app.recommender.Model.PhysicalActivityRdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class PhysicalActivityRdfService implements IPhysicalActivityRdfService {
    @Autowired
    private IPhysicalActivityRdfRepository physicalActivityRdfRepository;
    @Override
    public PhysicalActivityRdf addNewPhysicalActivity(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException {
        return this.physicalActivityRdfRepository.createPhysicalActivityRdf(userId, physicalActivityRdf);
    }

    @Override
    public PhysicalActivityRdf updatePhysicalActivityRdf(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException {
        return this.physicalActivityRdfRepository.updatePhysicalActivity(physicalActivityRdf,userId);
    }

    @Override
    public List<PhysicalActivityRdf> getAllPhysicalActivityRdf(String userId) throws FileNotFoundException {

        return this.physicalActivityRdfRepository.getAllPhysicalActivityRdf(userId);
    }

    @Override
    public PhysicalActivityRdf getPhysicalActivityById(String physicalActivityId, String userId) {
        return null;
    }
}
