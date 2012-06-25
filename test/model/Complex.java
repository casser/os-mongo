package model;

import java.util.EnumMap;
import java.util.LinkedHashMap;

public class Complex {
		
	public static enum MapKey {
		ONE,TWO,TREE,FOUR
	}
	
	public static class Map extends EnumMap<MapKey,Integer>{
		public Map() {
			super(MapKey.class);
		}
		private static final long serialVersionUID = -5414931712326431882L;
	}
	
	public static class NestedMapKey  {
		String key;
		public NestedMapKey(String key){
			this.key = key;
		}
		public String toString(){
			return key;
		}
		@Override
		public boolean equals(Object obj) {
			return  obj.toString().equals(this.toString());
		}
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
	}
	
	public static class NestedMapValue {
		String time;
		String sender;
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
	}
	
	public static class NestedMap extends LinkedHashMap<NestedMapKey,NestedMapValue>{
		private static final long serialVersionUID = 5167473659919275020L;
	}
	
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private Basic basic;
	public Basic getBasic() {
		return basic;
	}
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	
	private Map map;
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
	private NestedMap nestedMap;
	public NestedMap getNestedMap() {
		return nestedMap;
	}
	public void setNestedMap(NestedMap map) {
		this.nestedMap = map;
	}
}
