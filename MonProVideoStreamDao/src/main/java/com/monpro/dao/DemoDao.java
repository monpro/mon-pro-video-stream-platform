package com.monpro.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoDao {

  Long query(final Long id);
}
