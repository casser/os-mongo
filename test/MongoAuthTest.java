

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
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
import os.utils.MD5;



public class MongoAuthTest {
	
	private static Mongo client;
	private static Database database;
	private static Collection<?> collection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client 		= new Mongo("ds037387.mongolab.com", 37387);
		database 	= client.getDB("tetatet");
		collection 	= database.getCollection("col");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.close();
	}
	
	@Test
	public void testAuth() throws Exception {
		Boolean result =  database.authenticate("mongouser","mongopassword");
		print(collection.count());
		print(result);
	}
	
	@SuppressWarnings("unused")
	private static void print(Object o){
		System.out.println(o);
	}
	
	

}
