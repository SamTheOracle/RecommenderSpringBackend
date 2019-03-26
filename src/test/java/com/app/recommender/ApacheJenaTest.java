package com.app.recommender;

import com.app.recommender.Model.FoodRdf;
import junit.framework.TestCase;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.junit.Assert;
import org.junit.Test;

public class ApacheJenaTest extends TestCase {
    protected Dataset ds;

    @Override
    protected void setUp() throws Exception {
        ds = TDBFactory.createDataset("tdb");
    }

    @Test
    public void testSetup() {
        Model model = null;

        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel("food");
            FileManager.get().readModel(model, "food.rdf");
            model.write(System.out, "RDF/XML");

            ds.commit();
        } finally {

            ds.end();
        }
        Assert.assertNotNull(model);


    }

    @Test
    public void testSimpleAdd() {
        Model model = null;

        ds.begin(ReadWrite.READ);
        try {
            model = ds.getNamedModel("food");


            Resource r = model.getResource(FoodRdf.foodUri + "Banana");
            StmtIterator stmtIterator = r.listProperties();
            while (stmtIterator.hasNext()) {
                Statement s = stmtIterator.nextStatement();
                Assert.assertNotNull(s);
            }
            Statement s = r.getProperty(FoodRdf.nameRdf);
            Assert.assertNotNull(s);
            Assert.assertTrue(s.getSubject().getURI().contains("Banana"));
            ds.commit();
        } finally {

            ds.end();

        }
        Assert.assertNotNull(model);


    }

    @Test
    public void testSimpleRemove() {
        Model model = null;

        ds.begin(ReadWrite.READ);
        try {
            model = ds.getNamedModel("food");
            Resource r = model.getResource(FoodRdf.foodUri + "Banana");
            Statement s = r.getProperty(FoodRdf.nameRdf);
            Assert.assertTrue(s.getSubject().getURI().contains("Banana"));
            ds.commit();
        } finally {

            ds.end();
        }
        ds.begin(ReadWrite.WRITE);
        try {
            model = ds.getNamedModel("food");
            Assert.assertNotNull(model);
            model.removeAll();
            model.write(System.out, "RDF/XML");
            ds.commit();
        } finally {
            ds.end();
        }
        ds.begin(ReadWrite.READ);
        Resource test;
        try {
            model = ds.getNamedModel("food");
            test = model.getResource(FoodRdf.foodUri + "Banana");
            String a = test.getLocalName();
            String b = test.getProperty(FoodRdf.nameRdf).getObject().toString();
            System.out.println(test.getLocalName() + " " + test.getProperty(FoodRdf.nameRdf).getObject().toString());
//            Assert.assertNull(r);
            ds.commit();
        } finally {

            ds.end();
        }

    }
}
