package model;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import os.bson.BsonEncodable;
import os.bson.BsonId;
import os.bson.BsonModel;
import os.bson.BsonModel.Entity;
import os.utils.Types.Property.Index;


@Entity(collection="users",version=1)
public class User implements BsonModel{
	
	@Override
	public Object id() {
		return id;
	}
	
	@Override
	public void id(Object value) {
		id = (BsonId) value;
	}
	
	public static class Mappings extends EnumMap<Mappings.Key,Mappings.Value> {
		private static final long serialVersionUID = -805184175429688860L;
		
		public Mappings() {
			super(Key.class);
		}
		
		public static enum Key{
			FB,DC,GP,VK,OD;
		}
		
		public static class Value{
			public String id;
			public String token;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getToken() {
				return token;
			}
			public void setToken(String token) {
				this.token = token;
			}
		}
	}
	
	public static class Neighbors extends LinkedHashMap<String,Neighbors.Value> {
		private static final long serialVersionUID = -6528783000089047183L;
		public static enum Value {NONE,PENDING,ACCEPTED;}
	}
	
	public static class Outbox extends HashSet<String> {
		private static final long serialVersionUID = 4269769049791151040L;
	}
	
	public static class Inbox extends LinkedHashMap<String,Inbox.Value> {
		private static final long serialVersionUID = -5610753277425335005L;
		public static class Value{
			public String sender;
			public Long createdAt;
			
			@Index(0)
			public String getSender() {
				return sender;
			}
			public void setSender(String sender) {
				this.sender = sender;
			}
			
			@Index(1)
			public Long getCreatedAt() {
				return createdAt;
			}
			public void setCreatedAt(Long createdAt) {
				this.createdAt = createdAt;
			}
			
			public Value(){
				createdAt = System.currentTimeMillis();
			}
			public Value(String sender){
				this();
				setSender(sender);
			}
		}
	}
	
	private BsonId id;
	private String email;
	private Mappings mappings;
	private Neighbors neighbors;
	private Outbox outbox;
	private Inbox inbox;
	
	@Index(0)
	@BsonEncodable.Ignore
	public BsonId getId() {
		return id;
	}
	public void setId(BsonId id) {
		this.id = id;
	}
	
	@Index(1)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Index(2)
	public Mappings getMappings() {
		return mappings;
	}
	public void setMappings(Mappings mappings) {
		this.mappings = mappings;
	}
	
	@Index(3)
	public Neighbors getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(Neighbors neighbors) {
		this.neighbors = neighbors;
	}
	
	@Index(4)
	public Inbox getInbox() {
		return inbox;
	}
	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}
	
	@Index(5)
	public Outbox getOutbox() {
		return outbox;
	}
	public void setOutbox(Outbox outbox) {
		this.outbox = outbox;
	}
	
}
