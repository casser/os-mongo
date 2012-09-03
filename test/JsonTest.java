import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import os.bson.BsonId;
import os.json.JSON;
import os.json.JSON.Hack;
import os.utils.ByteArray;
import os.utils.BytesUtil;
import os.utils.MD5;
import os.utils.Types;



public class JsonTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testJsonTest() throws Exception {
		System.out.println(JSON.decode("{_id:BsonId(087141500000000020a5bad6)}"));
	}
	
}
