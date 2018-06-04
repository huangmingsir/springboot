package com.jx.example.base;

import java.util.List;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午10:22:40
 *
 */
public interface IBaseService<T> {
	public T selectByPrimaryKey(Integer id);

	public int deleteByPrimaryKey(Integer id);
	
	public int insert(T t);

	public int insertSelective(T t);
	
	public int updateByPrimaryKey(T t);

	public int updateByPrimaryKeySelective(T t);
	
	public List<T> getList(T t);

	public List<T> getListByPage(T t);

	public Long getCount(T t);
}
