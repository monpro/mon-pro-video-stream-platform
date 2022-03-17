package com.monpro.service;

import com.monpro.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

  @Autowired
  private DemoDao demoDao;

  public Long query(final Long id) {
    return demoDao.query(id);
  }

}
