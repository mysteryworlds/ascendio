package com.mysteryworlds.ascendio.quest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

final class QuestTest {
  @Inject
  private QuestFactory questFactory;
  @Inject
  private QuestSessionRegistry sessionRegistry;

  @BeforeEach
  void setUp() {
    var injector = Guice.createInjector(QuestModule.create());
    injector.injectMembers(this);
  }

  @Test
  void testStartProhibitedByGuard() {
    var neverBeginningQuest = questFactory.createBuilder()
      .withDescription("Quest that will never be started")
      .withName("NeverBeginningQuest")
      .withGuard((player) -> false)
      .setObjectives(Lists.newArrayList())
      .setRewards(Lists.newArrayList())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    Executable executable = () -> neverBeginningQuest.start(player);
    assertThrows(QuestRequirementsException.class, executable);
  }

  @Test
  void testSessionRegistration()
    throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var quest = questFactory.createBuilder()
      .withDescription("Standard quest")
      .withName("Standardquest")
      .setObjectives(Lists.newArrayList())
      .setRewards(Lists.newArrayList())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    var session = quest.start(player);
    var sessions = sessionRegistry.findByPlayer(player);
    assertEquals(1, sessions.size());
    assertSame(session, sessions.stream().findFirst().get());
  }

  @Test
  void testStartWithAlreadyActiveSession()
    throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var quest = questFactory.createBuilder()
      .withDescription("Standardquest")
      .withName("Standardquest")
      .withId(UUID.randomUUID())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    quest.start(player);

    Executable executable = () -> quest.start(player);
    assertThrows(QuestSessionAlreadyExistsException.class, executable);
  }
}