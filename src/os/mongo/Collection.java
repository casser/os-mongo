package os.mongo;

import java.util.List;
import java.util.Map;

import os.bson.BSON;
import os.bson.BsonId;
import os.bson.BsonModel;
import os.mongo.ops.OpDelete;
import os.mongo.ops.OpInsert;
import os.mongo.ops.OpQuery;
import os.mongo.ops.OpReply;
import os.mongo.ops.OpUpdate;
import os.mongo.ops.Result;



public class Collection<T extends BsonModel> {
	
	private Database 	database;
	private String 		name;
	private Class<T>	clazz;
	
	public Database getDatabase() {
		return database;
	}
	
	public String getName() {
		return name;
	}
	
	public Collection( Database database, String name) {
		this(database,name,null);
	}
	
	public Collection( Database database, String name, Class<T> clazz) {
		this.database 	= database;
		this.name 		= name;
		this.clazz 		= clazz;
	}
	
	public T get(Object query) throws Exception {
		Page<T> page = null;
		if(query!=null && IQuery.class.isAssignableFrom(query.getClass())){
			IQuery q = (IQuery)query;
			page = find(q.getQuery(), q.getFields(), 1, q.getSkip(), true);
		}else{
			page = find(query, null, 1, 0, true);
		}
		if(page.size()>0){
			return page.get(0);
		}else {
			return null;
		}
	}
	
	public Page<T> find() throws Exception {
		return find(null);
	}
	
	public Page<T> find(Object query) throws Exception {
		if(query!=null && IQuery.class.isAssignableFrom(query.getClass())){
			IQuery q = (IQuery)query;
			return find(q.getQuery(), q.getFields(), q.getLimit(), q.getSkip());
		}else{
			return find(query, null, 1000, 0);
		}
	}
		
	public Page<T> find(Object query, Object fields, int limit, int skip) throws Exception{
		return find(query,fields,limit,skip,false);
	}
	
	public Page<T> find(Object query, Object fields, int limit, int skip, boolean single) throws Exception{
		OpQuery q = new OpQuery(name(), query, fields, limit, skip, 0);
    	OpReply r = database.getMongo().call(q);
    	
    	List<T> data = (List<T>) r.getResults(clazz);
    	if(single || r.getCursorID()==0){
    		return new Page<T>(new Integer(skip).longValue(),new Integer(data.size()).longValue(),data);
    	}else{
    		return new Page<T>(new Integer(skip).longValue(),count(query),data);
    	}
    }
    
    public Boolean insert(Object[] documents) throws Exception {
    	OpInsert q = new OpInsert(name(),documents);
    	OpReply  r = call(q);
    	return !r.getResult(Result.class).hasError();
    }
    
    public Long count() throws Exception {
    	return count(null);
    }
    
	public Long count(Object query) throws Exception {
		Query q = Query.start("count").is(name);
		if(query!=null){
			q.and("query").is(query);
		};
		Map<String,Object> r = database.command(q.getQuery());
		return ((Double)r.get("n")).longValue();
	}
	
    public Long update(Object query, Object document) throws Exception {
    	Long count = count(query);
		if(count>=0){
	    	OpUpdate q = new OpUpdate(name(),query,document);
	    	OpReply  r = call(q);
	    	return r.getResult(Result.class).hasError()?0:count;
		}
		return 0L;
    }
    
    public Long delete(Object query) throws Exception {
    	if(IQuery.class.isAssignableFrom(query.getClass())){
    		query = ((IQuery)query).getQuery();
		}
    	Long count = count(query);
		if(count>0){
			OpDelete q = new OpDelete(name(),query);
    		OpReply  r = call(q);
    		return r.getResult(Result.class).hasError()?0:count;
		}
    	return 0L;
    }
    
    public Boolean save(Map<String,Object> document) throws Exception {
		if(!document.containsKey("_id")){
			document.put("_id",BsonId.get());
		}
		
		byte[] bytes = BSON.encode(document);
		
		Object query = Query.start("_id").is(document.get("_id")).getQuery();
		OpUpdate q  = new OpUpdate(name(),query,bytes,true,1);
    	OpReply  r  = call(q);
    	Result   rs = r.getResult(Result.class);
    	
    	return !rs.hasError();
    }
    
	public Boolean save(T document) throws Exception {
		
		if(document.id()==null){
			document.id(BsonId.get());
		}
		
		byte[] bytes = BSON.encode(document);
		
		if((document.info()!=null && document.info().isModified()) || document.info()==null){
			Object query = Query.start("_id").is(document.id()).getQuery();
			OpUpdate q  = new OpUpdate(name(),query,bytes,true,1);
	    	OpReply  r  = call(q);
	    	Result   rs = r.getResult(Result.class);
	    	if(document.info()!=null){
	    		document.info().fresh(rs.getUpdatedExisting());
	    	}
	    	return !rs.hasError();
		}
		
		return false;
    }
        
	private OpReply call(Message message) throws Exception{
		return database.getMongo().call(message);
	}
	
	private String name(){
		return database.getName()+"."+name;
	}
	
	public Boolean drop() throws Exception {
		Map<String,Object> r = database.command(Query.start("drop").is(name).getQuery());
		return r.get("ok").equals(1.0);
	}
	
}
