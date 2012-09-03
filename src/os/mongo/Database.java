package os.mongo;

import java.util.Map;

import os.bson.BsonModel;
import os.mongo.ops.OpQuery;
import os.mongo.ops.OpReply;



public class Database {
	
	private Mongo mongo;
	private String name;
	
	public Database(Mongo mongo, String name) {
		this.mongo 	= mongo;
		this.name 	= name;
	}

	public String getName() {
		return name;
	}
	
	public Mongo getMongo() {
		return mongo;
	}
	
	public <T extends BsonModel> Collection<T> getCollection(String name) {
		return new Collection<T>(this, name);
	}
	public <T extends BsonModel> Collection<T> getCollection(Class<T> clazz) {
		return new Collection<T>(this, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> command(Object query) throws Exception {
    	OpQuery q = new OpQuery(name+".$cmd", query, null, -1, 0, 0);
    	OpReply r = mongo.send(q);
    	return (Map<String,Object>) r.getResults(null).get(0);
    }
    
}
