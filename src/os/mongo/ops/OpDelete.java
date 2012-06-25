package os.mongo.ops;

import os.bson.BsonByteArray;
import os.bson.BsonDecoder;
import os.bson.BsonEncoder;
import os.mongo.Messages;


public class OpDelete extends CollMsg {
	
	public static final int SingleRemove = 1;
	
	private Object selector;
	public Object getSelector() {
		return selector;
	}
	public void setSelector(Object selector) {
		this.selector = selector;
	}
	
	public OpDelete(String collection, Object selector) {
		this(collection, selector, true);
	}
	
	public OpDelete(String collection, Object selector, boolean safe) {
		this(collection, selector, safe, 0);
	}
	
	public OpDelete(String collection, Object selector, boolean safe, int flags) {
		super(collection, safe, flags);
		this.selector = selector;
	}
		
	public OpDelete(byte[] bytes)  throws Exception{
		super(bytes);
	}
	
	@Override
	public int getCode() {
		return Messages.OP_DELETE.getCode();
	}
	
	@Override
	protected void writeBody(BsonByteArray bin)  {
		bin.writeInt( 0 ); // ZERO
		bin.writeCString( getCollection() );
		bin.writeInt( getFlags() );
		bin.writeBytes(new BsonEncoder().encode( selector ) );
	}
	
	@Override
	protected void readBody(BsonByteArray bin) {
		bin.readInt();// ZERO
		setCollection(bin.readCString());
		setFlags(bin.readInt());
		setSelector(new BsonDecoder().decode(bin.readBytes()));
	}
	
}
