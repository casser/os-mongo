import os.mongo.nio.MongoPool;
import os.mongo.ops.OpQuery;


public class SoketTest {
	 public static void main(String argv[]) throws Exception {
		MongoPool pool = new MongoPool("192.168.1.103", 27017, 40);
		for(int i=1;i<100;i++){
			new TestThread(pool).start();
		}
		//pool.close();
    }
    
    public static class TestThread extends Thread {
    	MongoPool channel;
    	public TestThread(MongoPool channel){
    		this.channel = channel;
    	}
    	@Override
    	public void run() {
    		try {
				for(int i=1;i<5;i++){
		    		System.out.println(
			    		channel.send(new OpQuery("test.col", null)).getDocuments()
			    	);
	    		}
				sleep((long)(Math.random()*200));
    		} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		
    	}
    }
}
