package com.mysteryworlds.ascendio.quest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Guice;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class QuestSessionTest {
  @Inject
  private QuestFactory questFactory;

  @BeforeEach
  void setUp() {
    var injector = Guice.createInjector(QuestModule.create());
    injector.injectMembers(this);
  }

  @Test
  void testRemainingObjectives()
    throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var quest = questFactory.createBuilder()
      .withDescription("Standardquest")
      .withName("Standardquest")
      .withId(UUID.randomUUID())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    var session = quest.start(player);
    var remainingObjectives = session.remainingObjectives();
    assertTrue(remainingObjectives.isEmpty());
  }

  @Test
  void testCloseSession()
    throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var quest = questFactory.createBuilder()
      .withDescription("Standardquest")
      .withName("Standardquest")
      .withId(UUID.randomUUID())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    var session = quest.start(player);
    session.close();
    assertEquals(QuestSessionStatus.FINISHED, session.status());
  }

  @Test
  void testCancelSession()
    throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var quest = questFactory.createBuilder()
      .withDescription("Standardquest")
      .withName("Standardquest")
      .withId(UUID.randomUUID())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    var session = quest.start(player);
    session.cancel();
    assertEquals(QuestSessionStatus.CANCELLED, session.status());
  }

  @Test
  void testRewardAwarding() throws QuestSessionAlreadyExistsException, QuestRequirementsException {
    var reward = new EmptyReward();
    var quest = questFactory.createBuilder()
      .withDescription("Standardquest")
      .withName("Standardquest")
      .withReward(reward)
      .withId(UUID.randomUUID())
      .create();
    var player = QuestPlayer.withId(UUID.randomUUID());
    var session = quest.start(player);
    session.close();
    assertTrue(reward.awarded);
  }

  private static final class EmptyReward implements QuestReward {
    private boolean awarded;

    @Override
    public void award(QuestPlayer player) {
      awarded = true;
    }
  }
}