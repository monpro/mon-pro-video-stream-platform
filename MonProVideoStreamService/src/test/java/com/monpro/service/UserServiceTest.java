package com.monpro.service;

import com.monpro.domain.User;
import com.monpro.dao.UserDao;
import com.monpro.domain.exception.ConditionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

  @Mock
  private static UserDao userDao;

  @Mock
  private User user;

  private UserService userService;

  @BeforeAll
  void initTestEnvironment() {
    openMocks(this);
    userService = new UserService(userDao);
    when(user.getPhone()).thenReturn("testPhone");
    when(userDao.getUserByPhone(any())).thenReturn(null);
    when(userDao.addUser(any())).thenReturn(1);
    when(userDao.addUserInfo(any())).thenReturn(2);
  }

  @Test
  void When_userGetPhoneIsEmpty_Then_ThrownException() {
    when(user.getPhone()).thenReturn("");
    assertThrows(ConditionException.class, () -> userService.addUser(user));
  }

  @Test
  void When_userAlreadyRegistered_Then_ThrownException() {
    when(userDao.getUserByPhone(any())).thenReturn(user);
    assertThrows(ConditionException.class, () -> userService.addUser(user));
  }

}
