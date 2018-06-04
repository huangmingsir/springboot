package com.jx.example.base;
/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午10:24:25
 *
 */

import java.util.List;

public class BaseServiceImpl<T> implements IBaseService<T> {
	
	private BaseMapper<T> baseMapper;
	
	public void setBaseMapper(BaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public T selectByPrimaryKey(Integer id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(T t) {
		return baseMapper.insert(t);
	}

	@Override
	public int insertSelective(T t) {
		return baseMapper.insertSelective(t);
	}

	@Override
	public int updateByPrimaryKey(T t) {
		return baseMapper.updateByPrimaryKey(t);
	}

	@Override
	public int updateByPrimaryKeySelective(T t) {
		return baseMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public List<T> getList(T t) {
		return baseMapper.getList(t);
	}

	@Override
	public List<T> getListByPage(T t) {
		return baseMapper.getListByPage(t);
	}

	@Override
	public Long getCount(T t) {
		return baseMapper.getCount(t);
	}
	
	
}
