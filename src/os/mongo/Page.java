package os.mongo;


import java.util.List;

import os.utils.Types.Property.Index;


public class Page<T> {
	
	private Long index;
	private Long count;
	private List<T> data;
	
	public Page(){
	}
	
	public Page(Long index, Long count, List<T> data){
		setIndex(index);
		setCount(count);
		setData(data);
	}
	
	@Index(0)
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	
	@Index(1)
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	@Index(2)
	public Long getSize() {
		return new Integer(size()).longValue();
	}
	
	@Index(3)
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public int size() {
		return (getData()!=null)?getData().size():0;
	}

	public T get(int index) {
		return getData().get(index);
	}
}
