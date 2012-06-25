

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import os.bson.BsonId;
import os.json.JSON;
import os.mongo.Collection;
import os.mongo.Database;
import os.mongo.Mongo;
import os.mongo.Page;
import os.mongo.Query;



public class MongoTest {
	
	private static Mongo client;
	private static Database database;
	private static Collection<?> collection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client 		= new Mongo("127.0.0.1", 27017);
		database 	= client.getDB("test");
		collection 	= database.getCollection("col");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.shutdown();
	}
	
	@Test
	public void testInsert() throws Exception {
		collection.drop();
		Boolean result = collection.insert(
			new Object[]{
				Query.start("_id").is(new BsonId("ae19844f000000000cbc8bde")).and("value").is("V1").getQuery(),	
				Query.start("_id").is(new BsonId("ae19844f000000000cbc8bdf")).and("value").is("V2").getQuery(),	
			}
		);
		print(result);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testUpdate() throws Exception {
		Long result = collection.update(
			Query.start("_id").in(Arrays.asList(
				new BsonId("ae19844f000000000cbc8bde"),
				new BsonId("ae19844f000000000cbc8bdf")
			)).getQuery(), 
			Query.start("$set").is(
				Query.start("value").is("updated").and("generated").is(UUID.fromString("00000000-7690-4535-0000-0000aece4b30")).and("date").is(new Date(1334064845619L)).getQuery()
			).getQuery()
		);
		print(result);
		Assert.assertEquals(2L,result.longValue());
	}
	
	@Test
	public void testFind() throws Exception {
		Query q = 
			Query.start().
			select("_id").limit(1);
		print(q.getQuery());
		Page<?> result = collection.find(q);
		print(result);
		Assert.assertEquals(1L,result.size());
	}
	
	@Test
	public void testDelete() throws Exception {
		Long result = collection.delete(
			Query.start("_id").in(Arrays.asList(
				new BsonId("ae19844f000000000cbc8bde"),
				new BsonId("ae19844f000000000cbc8bdf")
			)).getQuery()
		);
		print(result);
		Assert.assertEquals(2L,result.longValue());
	}
		
	@Test
	public void testCursor() throws Exception {
		collection.drop();
		Object[] docs = new Object[100];
		for(int i=0;i<docs.length;i++){
			docs[i] = Query.start("_id").is(BsonId.get()).and("value").is("V"+i).getQuery();
		}
		print(collection.insert(docs));
		Page<?> result = collection.find(Query.start().skip(10).limit(10));
		JSON.print(result);
	}
	
	@Test
	public void testSingle() throws Exception {
		JSON.print(collection.get(Query.start().skip(10)));
	}
	
	private static void print(Object o){
		System.out.println(o);
	}
	
}
