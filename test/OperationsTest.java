

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import os.mongo.Messages;
import os.mongo.Query;
import os.mongo.ops.OpDelete;
import os.mongo.ops.OpInsert;
import os.mongo.ops.OpQuery;
import os.mongo.ops.OpReply;
import os.mongo.ops.OpUpdate;




public class OperationsTest extends TestCase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOpDelete() throws Exception {
		OpDelete opi = new OpDelete("test.col",
			Query.start("_id").in(Arrays.asList("I1","I2")).getQuery()
		);
		OpDelete opo = (OpDelete) Messages.valueOf(opi.toBinaryMsg());
		assertTrue(opi.equals(opo));
	}
	
	@Test
	public void testOpInsert() throws Exception {
		OpInsert opi = new OpInsert("test.col",
			new Object[]{
				Query.start("_id").is("I1").and("value").is("V1").getQuery(),	
				Query.start("_id").is("I2").and("value").is("V2").getQuery(),	
			}
		);
		OpInsert opo = (OpInsert) Messages.valueOf(opi.toBinaryMsg());
		assertTrue(opi.equals(opo));
	}
	
	@Test
	public void testOpUpdate() throws Exception {
		OpUpdate opi = new OpUpdate("test.col",
			Query.start("_id").is("I1").and("value").is("V1").getQuery(),	
			Query.start("_id").is("I2").and("value").is("V2").getQuery()
		);
		OpUpdate opo = (OpUpdate) Messages.valueOf(opi.toBinaryMsg());
		assertTrue(opi.equals(opo));
	}
	
	@Test
	public void testOpQuery() throws Exception {
		OpQuery opi = new OpQuery("test.col",
			Query.start("_id").is("I1").and("value").is("V1").getQuery(),	
			Query.start("_id").is("I2").and("value").is("V2").getQuery()
		);
		OpQuery opo = (OpQuery) Messages.valueOf(opi.toBinaryMsg());
		assertTrue(opi.equals(opo));
	}
	
	@Test
	public void testOpGetMore() throws Exception {
		assertTrue(false);
	}
	
	@Test
	public void testOpKillCursors() throws Exception {
		assertTrue(false);
	}
	
	@Test
	public void testOpReply() throws Exception {
		List<Object> list = new ArrayList<Object>();
		list.add(Query.start("_id").is("I1").and("value").is("V1").getQuery());	
		list.add(Query.start("_id").is("I2").and("value").is("V2").getQuery());	
		
		OpReply opi = new OpReply(0,1L,1,list);
		OpReply opo = (OpReply) Messages.valueOf(opi.toBinaryMsg());
		assertTrue(opi.equals(opo));
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
}
