package org.webonise.multihostpoc.daoimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.webonise.multihostpoc.dao.GenericDao;
import org.webonise.multihostpoc.models.Person;
import org.webonise.multihostpoc.session.SessionFactoryBuilder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by webonise on 30/7/15.
 */
public class PersonDAOImplTests {
    private static final Logger LOG = LoggerFactory.getLogger(PersonDAOImplTests.class);

    private final SessionFactoryBuilder builder =  new SessionFactoryBuilder();
    private final GenericDao<Person> personDaoImpl =  new PersonDaoImpl(Person.class,builder.getSessionFactory());

    @Test
    public void testSessionFactoryCreationSucceeds(){
        Assert.assertNotNull(builder.getSessionFactory());
    }

    @Test
    public void testSaveData(){
        LOG.info("running the save data");
        Person p = getFixture();
        personDaoImpl.save(p);

    }

    @Test
    public void testReadData(){
        LOG.info("running the read data");
        List<Person> result =  personDaoImpl.readAll();
        Assert.assertNotNull(result);
        LOG.info(""+result.size());
        for(Person p : result){
            LOG.info(p.toString());
        }

    }


    /* @BeforeClass
     public void createHibernateSession() {

         hibernateSessionFactory = new HibernateSessionManager();
         personDAOImpl = new PersonDAOImpl(hibernateSessionFactory);
     }

     @Test
     public void testSavePerson() {
         String socialId = "ABCD-7712";
         Person fixture = getFixture();
         fixture.setSocialId(socialId);
         fixture.setAddDate(new Date());
         fixture = checkPersonExistAndModify(fixture);

         personDAOImpl.savePerson(fixture);
         Person dbRecord = personDAOImpl.getPersonBySocialId(socialId);
         Assert.assertNotNull(dbRecord);

         LOG.debug("Person social id {} {}", dbRecord, dbRecord.getSocialId());
     }

     @Test
     public void testGetPersonBySocialId(){
         String socialId = "ABCD-222";
         String name = "Anvay";
         Person fixture = getFixture();
         fixture.setName(name);
         fixture.setSocialId(socialId);
         fixture.setAddDate(new Date());
         fixture = checkPersonExistAndModify(fixture);
         personDAOImpl.savePerson(fixture);

         Person dbRecord = personDAOImpl.getPersonBySocialId(socialId);
         Assert.assertEquals(socialId, dbRecord.getSocialId());
         Assert.assertEquals(name, dbRecord.getName());


     }

     @Test
     public void testIsPersonExist() {
         String socialId = "ABCD-333";
         Person fixture = getFixture();
         fixture.setSocialId(socialId);
         fixture.setAddDate(new Date());
         fixture = checkPersonExistAndModify(fixture);
         personDAOImpl.savePerson(fixture);

         Assert.assertTrue(personDAOImpl.isPersonExist(socialId));

     }

     @Test
     public void testDeletePerson() {
         String socialId = "ABCD-444";
         Person fixture = getFixture();
         fixture.setSocialId(socialId);
         fixture= checkPersonExistAndModify(fixture);
         personDAOImpl.savePerson(fixture);

         personDAOImpl.deletePerson(fixture);
         Assert.assertNull(personDAOImpl.getPersonBySocialId(socialId));
     }
 */
    private Person getFixture() {
        Person person = new Person();
        SecureRandom random = new SecureRandom();

        person.setName("John");
        person.setSurname("Doe");
        person.setAddress("Timbuktoo");
        person.setNationality("Timbuktooin");
        person.setOccupation("Forager");
        person.setSocialId(new BigInteger(130, random).toString(32)); //just generating a fancy random string
        return person;
    }
/*
    private Person checkPersonExistAndModify(Person fixture){

        if (personDAOImpl.isPersonExist(fixture.getSocialId())) {
            fixture.setId(personDAOImpl.getPersonBySocialId(fixture.getSocialId()).getId());
        }
        return fixture;
    }
*/
}
