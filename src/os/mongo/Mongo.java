
package os.mongo;

import os.mongo.nio.NettyMongoClient;


public class Mongo extends NettyMongoClient {
	
	public String host;
	public Integer port;
	
	public String getHost(){
		return host;
	}
	
	public Integer getPort(){
		return port;	
	}
	
	public Mongo( String host, Integer port) throws InterruptedException {
		super(host,port);
	}
	
	public Database getDB(String name) {
		return new Database(this, name);
	}
	
}

