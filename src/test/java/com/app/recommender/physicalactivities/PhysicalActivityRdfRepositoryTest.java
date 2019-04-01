package com.app.recommender.physicalactivities;

import com.app.recommender.Model.PhysicalActivityRdf;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

import java.util.UUID;

import static org.junit.Assert.*;

public class PhysicalActivityRdfRepositoryTest {
    private static final String FILE_PREFIX = "phyactivities";
    private static final String FILE_SUFFIX = ".rdf";
    private IPhysicalActivityRdfRepository physicalActivityRdfRepository;
    private final String userId = "7d83355c-2173-0ac1-4754-79b9e9900947";
    private PhysicalActivityRdf physicalActivityRdf;

    @Before
    public void setUp() throws Exception {
        LocalDate localDate = LocalDate.parse("2019-12-12");
        File file = new File(FILE_PREFIX + userId + FILE_SUFFIX);
        Model model = ModelFactory.createDefaultModel();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
            Resource r = model.createResource(PhysicalActivityRdf.physicalActivityUri + "jogging");
            r.addLiteral(PhysicalActivityRdf.idRdf, UUID.randomUUID().toString());
            r.addLiteral(PhysicalActivityRdf.nameRdf, "jogging");
            r.addLiteral(PhysicalActivityRdf.userIdRdf, "01923809-2131093");
            r.addLiteral(PhysicalActivityRdf.caloriesPerHourRdf, 1456);
//            r.addLiteral(PhysicalActivityRdf.weeklyGoalRdf, 5600);
            r.addProperty(PhysicalActivityRdf.startDateRdf, localDate.toString());
            r.addProperty(PhysicalActivityRdf.endDateRdf, localDate.toString());
            r.addLiteral(PhysicalActivityRdf.nameRdf, "jogging");
            this.physicalActivityRdf = new PhysicalActivityRdf();
            physicalActivityRdf.setName(r.getProperty(PhysicalActivityRdf.nameRdf).getObject().toString());
            physicalActivityRdf.setId(r.getProperty(PhysicalActivityRdf.idRdf).getObject().toString());
            physicalActivityRdf.setUserId(r.getProperty(PhysicalActivityRdf.userIdRdf).getObject().toString());
//            physicalActivityRdf.setWeeklyGoal(r.getProperty(PhysicalActivityRdf.weeklyGoalRdf).getDouble());
            physicalActivityRdf.setCaloriesPerHour(r.getProperty(PhysicalActivityRdf.caloriesPerHourRdf).getDouble());
//            physicalActivityRdf.setStartDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.startDateRdf).getObject().toString()));
//            physicalActivityRdf.setEndDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.endDateRdf).getObject().toString()));
            try (OutputStream out = new FileOutputStream("phactivities" + userId + ".rdf")) {
                model.write(out, "RDF/XML");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (file.createNewFile()) {
                    model.setNsPrefix(PhysicalActivityRdf.NSPrefix, PhysicalActivityRdf.physicalActivityUri);
                    Resource r = model.createResource(PhysicalActivityRdf.physicalActivityUri + "jogging");
                    r.addLiteral(PhysicalActivityRdf.idRdf, UUID.randomUUID().toString());
                    r.addLiteral(PhysicalActivityRdf.nameRdf, "jogging");
                    r.addLiteral(PhysicalActivityRdf.userIdRdf, UUID.randomUUID().toString());
                    r.addLiteral(PhysicalActivityRdf.caloriesPerHourRdf, 1456);
//                    r.addLiteral(PhysicalActivityRdf.weeklyGoalRdf, 5600);
                    r.addProperty(PhysicalActivityRdf.startDateRdf, localDate.toString());
                    r.addProperty(PhysicalActivityRdf.endDateRdf, localDate.toString());
                    r.addLiteral(PhysicalActivityRdf.nameRdf, "jogging");
                    this.physicalActivityRdf = new PhysicalActivityRdf();
                    physicalActivityRdf.setName(r.getProperty(PhysicalActivityRdf.nameRdf).getObject().toString());
                    physicalActivityRdf.setId(r.getProperty(PhysicalActivityRdf.idRdf).getObject().toString());
                    physicalActivityRdf.setUserId(r.getProperty(PhysicalActivityRdf.userIdRdf).getObject().toString());
//                    physicalActivityRdf.setWeeklyGoal(r.getProperty(PhysicalActivityRdf.weeklyGoalRdf).getDouble());
                    physicalActivityRdf.setCaloriesPerHour(r.getProperty(PhysicalActivityRdf.caloriesPerHourRdf).getDouble());
//                    physicalActivityRdf.setStartDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.startDateRdf).getObject().toString()));
//                    physicalActivityRdf.setEndDate(LocalDate.parse(r.getProperty(PhysicalActivityRdf.endDateRdf).getObject().toString()));
                    try (OutputStream out = new FileOutputStream(FILE_PREFIX + userId + FILE_SUFFIX)) {
                        model.write(out, "RDF/XML");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(file.exists());
        this.physicalActivityRdfRepository = new PhysicalActivityRdfRepository();

        Assert.assertNotNull(this.physicalActivityRdfRepository);

    }

    @Test
    public void createPhysicalActivityRdf() throws FileNotFoundException {

        PhysicalActivityRdf testRdf = this.physicalActivityRdfRepository.createPhysicalActivityRdf(this.userId, this.physicalActivityRdf);
        Assert.assertNotNull(testRdf);
        Assert.assertTrue(testRdf.getId().equalsIgnoreCase(this.physicalActivityRdf.getId()));
        assertEquals(testRdf.getCaloriesPerHour(), this.physicalActivityRdf.getCaloriesPerHour(), 0.0);
//        assertEquals(testRdf.getWeeklyGoal(), this.physicalActivityRdf.getWeeklyGoal(), 0.0);


    }

    @Test
    public void createPhysicalActivityRdf1() {
    }

    @Test
    public void updatePhysicalActivityRdf() {
    }

    @Test
    public void getAllPhysicalActivityRdf() {
    }

    @Test
    public void getPhysicalActivityById() {
    }
}