package com.app.recommender.physicalactivities.ResourceRdfServer;

import com.app.recommender.Model.FoodRdf;
import com.app.recommender.Model.PhysicalActivityRdf;
import org.apache.jena.rdf.model.*;
import org.springframework.stereotype.Repository;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PhysicalActivityRdfRepository implements IPhysicalActivityRdfRepository {

    private static final String FILE_PREFIX = "phactivities";
    private static final String FILE_SUFFIX = ".rdf";

    @Override
    public PhysicalActivityRdf createPhysicalActivityRdf(String userId, PhysicalActivityRdf physicalActivityRdf) throws FileNotFoundException {
        File file = new File(FILE_PREFIX + userId + FILE_SUFFIX);
        Model model = ModelFactory.createDefaultModel();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
        } else {
            try {
                if (file.createNewFile()) {
                    model.setNsPrefix(PhysicalActivityRdf.NSPrefix, PhysicalActivityRdf.physicalActivityUri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Resource phActivity = addPhysicalActivityResourceToModel(physicalActivityRdf, model);

        StringWriter writer = new StringWriter();

        Model newTempModel = ModelFactory.createDefaultModel();
        addPhysicalActivityResourceToModel(physicalActivityRdf, newTempModel);
        newTempModel.write(writer, "RDF/XML");
        try (OutputStream out = new FileOutputStream(FILE_PREFIX + userId + FILE_SUFFIX)) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PhysicalActivityRdf toSendBack = fromRdfToPhysicalActivity(phActivity, model);
        if (toSendBack != null) {
            toSendBack.setRdfOutput(writer.toString());
            return toSendBack;
        }


        return null;
    }


    @Override
    public PhysicalActivityRdf updatePhysicalActivity(PhysicalActivityRdf physicalActivityRdf, String userId) throws FileNotFoundException {
        File file = new File(FILE_PREFIX + userId + FILE_SUFFIX);
        Model model = ModelFactory.createDefaultModel();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
        } else {
            try {
                if (file.createNewFile()) {
                    model.setNsPrefix(PhysicalActivityRdf.NSPrefix, PhysicalActivityRdf.physicalActivityUri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Resource r = null;
        ResIterator resIterator = model.listResourcesWithProperty(PhysicalActivityRdf.idRdf, physicalActivityRdf.getId());
        while (resIterator.hasNext()) {
            r = resIterator.nextResource();
        }
        if (r == null) {
            return null;
        }
        StmtIterator stmtIterator = r.listProperties();

        model.remove(stmtIterator);

        Resource updatedResource = addPhysicalActivityResourceToModel(physicalActivityRdf, model);




        Model newTempModel = ModelFactory.createDefaultModel();

        addPhysicalActivityResourceToModel(physicalActivityRdf, newTempModel);
        StringWriter writer = new StringWriter();
        newTempModel.write(writer, "RDF/XML");
        PhysicalActivityRdf updatedPhysicalActivityRdf = fromRdfToPhysicalActivity(updatedResource,model);
        if(updatedPhysicalActivityRdf!=null){
            updatedPhysicalActivityRdf.setRdfOutput(writer.toString());

        }

        try (OutputStream out = new FileOutputStream(FILE_PREFIX + userId + FILE_SUFFIX)) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updatedPhysicalActivityRdf;
    }

    @Override
    public List<PhysicalActivityRdf> getAllPhysicalActivityRdf(String userId) throws FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();
        List<PhysicalActivityRdf> physicalActivityRdfs = new ArrayList<>();
        File file = new File(FILE_PREFIX + userId + FILE_SUFFIX);
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
            ResIterator resIterator = modelToQuery.listResourcesWithProperty(PhysicalActivityRdf.idRdf);
            while (resIterator.hasNext()) {
                Resource resource = resIterator.nextResource();
                PhysicalActivityRdf rdf = fromRdfToPhysicalActivity(resource, modelToQuery);
                if (rdf != null) {
                    Model newTempModel = ModelFactory.createDefaultModel();
                    StringWriter writer = new StringWriter();
                    addPhysicalActivityResourceToModel(rdf, newTempModel);
                    newTempModel.write(writer, "RDF/XML");
                    rdf.setRdfOutput(writer.toString());
                    physicalActivityRdfs.add(rdf);
                }

            }

            return physicalActivityRdfs;
        } else {
            return null;
        }
    }

    @Override
    public PhysicalActivityRdf getPhysicalActivityById(String physicalActivityId, String userId) {
        return null;
    }

    private Resource addPhysicalActivityResourceToModel(PhysicalActivityRdf physicalActivityRdf, Model model) {
        model.setNsPrefix(PhysicalActivityRdf.NSPrefix, PhysicalActivityRdf.physicalActivityUri);
        String pActivityName = physicalActivityRdf.getName().replaceAll("\\s", "_");
        Resource physicalActivityResource = model.createResource(PhysicalActivityRdf.physicalActivityUri + pActivityName);
        physicalActivityResource.addProperty(PhysicalActivityRdf.idRdf, physicalActivityRdf.getId());
        physicalActivityResource.addProperty(PhysicalActivityRdf.nameRdf, physicalActivityRdf.getName());
        physicalActivityResource.addProperty(PhysicalActivityRdf.userIdRdf, physicalActivityRdf.getUserId());
        physicalActivityResource.addLiteral(PhysicalActivityRdf.caloriesPerHourRdf, physicalActivityRdf.getCaloriesPerHour());
        physicalActivityResource.addProperty(PhysicalActivityRdf.startDateRdf, physicalActivityRdf.getStartDate().toString());
        physicalActivityResource.addProperty(PhysicalActivityRdf.endDateRdf, physicalActivityRdf.getEndDate().toString());
        physicalActivityResource.addProperty(PhysicalActivityRdf.descriptionRdf, physicalActivityRdf.getDescription());
        physicalActivityResource.addProperty(PhysicalActivityRdf.imageUrlRdf, physicalActivityRdf.getImageUrl());


        return physicalActivityResource;


    }

    private PhysicalActivityRdf fromRdfToPhysicalActivity(Resource r, Model model) {
        StmtIterator stmtIterator = model.listStatements(r, null, (RDFNode) null);
        if (r != null && stmtIterator.hasNext()) {
            PhysicalActivityRdf rdfObject = new PhysicalActivityRdf();
            rdfObject.setName(r.getProperty(PhysicalActivityRdf.nameRdf).getObject().toString());
            rdfObject.setId(r.getProperty(PhysicalActivityRdf.idRdf).getObject().toString());
            rdfObject.setUserId(r.getProperty(PhysicalActivityRdf.userIdRdf).getObject().toString());
            rdfObject.setCaloriesPerHour(r.getProperty(PhysicalActivityRdf.caloriesPerHourRdf).getDouble());
            rdfObject.setStartDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.startDateRdf).getObject().toString()));
            rdfObject.setEndDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.endDateRdf).getObject().toString()));
            rdfObject.setDescription(r.getProperty(PhysicalActivityRdf.descriptionRdf).getObject().toString());
            rdfObject.setImageUrl(r.getProperty(PhysicalActivityRdf.imageUrlRdf).getObject().toString());
            rdfObject.setRdfOutput("");
            return rdfObject;
        } else {
            return null;
        }

    }
}
