package os.mongo.ops;

import os.bson.BSON;

public class Result {
	
	private Integer connectionId;
	private String err;
	private Integer n;
	private Double ok;
	private Boolean updatedExisting;
	private Object upserted;
	private Long lastOp;
	
	public Integer getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public Integer getN() {
		return n;
	}
	public void setN(Integer n) {
		this.n = n;
	}
	public Double getOk() {
		return ok;
	}
	public void setOk(Double ok) {
		this.ok = ok;
	}
	public Boolean getUpdatedExisting() {
		return updatedExisting;
	}
	public void setUpdatedExisting(Boolean updatedExisting) {
		this.updatedExisting = updatedExisting;
	}
	public Object getUpserted() {
		return upserted;
	}
	public void setUpserted(Object upserted) {
		this.upserted = upserted;
	}
	
	public Long getLastOp() {
		return lastOp;
	}
	public void setLastOp(Long lastOp) {
		this.lastOp = lastOp;
	}
	
	public Boolean hasError() {
		return err!=null;
	}
	
	@Override
	public String toString() {
		return BSON.decode(BSON.encode(this)).toString();
	}
}
