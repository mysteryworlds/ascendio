package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;

public final class QuestSessionFactory {
  private final QuestSessionRegistry sessionRegistry;

  @Inject
  private QuestSessionFactory(QuestSessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  QuestSession createSession(
    Quest quest,
    QuestPlayer player
  ) throws QuestSessionAlreadyExistsException {
    Preconditions.checkNotNull(quest);
    Preconditions.checkNotNull(player);
    ensureSessionAbsence(quest, player);
    var session = createSession(quest, player, Lists.newArrayList());
    sessionRegistry.register(session);
    return session;
  }

  private QuestSession createSession(
    Quest quest,
    QuestPlayer player,
    List<QuestObjective> reachedObjectives
  ) {
    return new QuestSession(
      quest,
      player,
      QuestSessionStatus.RUNNING,
      reachedObjectives,
      sessionRegistry
    );
  }

  private void ensureSessionAbsence(Quest quest, QuestPlayer player)
    throws QuestSessionAlreadyExistsException {
    var sessions = sessionRegistry.findByQuest(quest);
    var sessionExists = sessions.stream()
      .anyMatch(session -> session.player() == player);
    if (sessionExists) {
      throw QuestSessionAlreadyExistsException
        .withMessage("Session already exists");
    }
  }
}
