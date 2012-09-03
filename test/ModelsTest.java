

import java.io.File;

import model.User;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import os.bson.BsonId;
import os.json.JSON;
import os.mongo.Collection;
import os.mongo.Database;
import os.mongo.Mongo;
import os.mongo.Page;
import os.mongo.Query;



public class ModelsTest {
	
	private static Mongo client;
	private static Database database;
	
	private static Collection<User> collection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client 		= new Mongo();
		client.connect("192.168.1.105", 27017,20);
		database 	= client.getDB("test");
		collection 	= database.getCollection(User.class);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.close();
	}
		
	@Test
	public void testUsersInsert() throws Exception {
		collection.drop();
		
		int count = 1000;
		for(int i=0;i<count;i++){
			User u = new User();
			u.setId(BsonId.get());
			u.setEmail("user."+i+"@email.com");
			Assert.assertTrue(collection.save(u));
		}
		
		Assert.assertEquals(collection.count().intValue(),count);
		
		collection.drop();
	}
	
	@Test
	public void testUserModification() throws Exception {
		
		collection.drop();
		
		int count = 2;
		for(int i=0;i<count;i++){
			User u = new User();
			u.setId(BsonId.get());
			u.setEmail("user."+i+"@email.com");
			Assert.assertTrue(collection.save(u));
		}
		
		Page<User> users = collection.find();
		JSON.print(users);
		Assert.assertEquals(users.size(),count);
		
		User u1 = users.get(0);
		u1.setInbox(new  User.Inbox());
		u1.getInbox().put("R1", new User.Inbox.Value("U1"));
		u1.getInbox().put("R2", new User.Inbox.Value("U2"));
		u1.getInbox().put("R3", new User.Inbox.Value("U3"));
		
		Assert.assertTrue(collection.save(u1));
		
		
		u1.setOutbox(new User.Outbox());
		u1.getOutbox().add("R1");
		u1.getOutbox().add("R2");
		u1.getOutbox().add("R3");
		
		u1.setNeighbors(new User.Neighbors());
		u1.getNeighbors().put("U1",User.Neighbors.Value.ACCEPTED);
		u1.getNeighbors().put("U2",User.Neighbors.Value.NONE);
		u1.getNeighbors().put("U3",User.Neighbors.Value.PENDING);
		
		Assert.assertTrue(collection.save(u1));
		
		User u1m = collection.find(Query.start("_id").is(u1.getId())).get(0);
		
		JSON.print(u1m);
		
		
		User u2 = users.get(1);
		JSON.print(u2);
		collection.drop();
	}

	@SuppressWarnings("unused")
	private void print(Object obj) {
		System.out.println(obj);
	}
	
}
