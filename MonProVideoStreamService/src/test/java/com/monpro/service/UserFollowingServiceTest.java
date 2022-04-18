package com.monpro.service;

import com.monpro.dao.UserFollowingDao;
import com.monpro.domain.FollowingGroup;
import com.monpro.domain.User;
import com.monpro.domain.UserFollowing;
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
public class UserFollowingServiceTest {

  @Mock
  private UserFollowingDao userFollowingDao;

  @Mock
  private FollowingGroupService followingGroupService;

  @Mock
  private UserService userService;

  private UserFollowingService userFollowingService;

  private final static Long userId = 1l;
  private final static Long followingUserId = 3l;
  private final static Long groupId = 2l;
  private final static User TEST_USER = User.builder().id(followingUserId).build();
  private final static FollowingGroup TEST_FOLLOWING_GROUP = FollowingGroup.builder().id(groupId).build();
  private final static UserFollowing TEST_USER_FOLLOWING = UserFollowing.builder().followingId(followingUserId).userId(userId).build();

  @BeforeAll
  void initTestEnvironment() {
    openMocks(this);
    when(userService.getUserById(any())).thenReturn(TEST_USER);
    when(followingGroupService.getByType(any())).thenReturn(TEST_FOLLOWING_GROUP);
    userFollowingService = new UserFollowingService(userFollowingDao, followingGroupService, userService);
  }

  @Test
  void Given_UserFollowing_When_Add_Then_Succeed() {
    userFollowingService.addUserFollowing(TEST_USER_FOLLOWING);
  }

  @Test
  void Given_getByUserIdReturnNull_When_callUserService_Then_thrownException() {
    when(userService.getUserById(any())).thenReturn(null);
    assertThrows(ConditionException.class, () -> userFollowingService.addUserFollowing(TEST_USER_FOLLOWING));
  }

  @Test
  void Given_getByIdReturnNull_When_callFollowingGroupService_Then_thrownException() {
    when(followingGroupService.getById(any())).thenReturn(null);
    TEST_USER_FOLLOWING.setGroupId(groupId);
    assertThrows(ConditionException.class, () -> userFollowingService.addUserFollowing(TEST_USER_FOLLOWING));
  }

}
