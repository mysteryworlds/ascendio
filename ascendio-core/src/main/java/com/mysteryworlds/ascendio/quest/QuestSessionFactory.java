package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class QuestSessionFactory {
  private final QuestSessionRegistry sessionRegistry;

  private QuestSessionFactory(QuestSessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public QuestSession createSession(Quest quest, QuestPlayer player) {
    Preconditions.checkNotNull(quest);
    Preconditions.checkNotNull(player);
    return new QuestSession(
      quest,
      player,
      status, Lists.newArrayList(),
      sessionRegistry
    );
  }
}
