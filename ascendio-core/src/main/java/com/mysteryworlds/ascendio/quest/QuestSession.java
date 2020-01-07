package com.mysteryworlds.ascendio.quest;

import java.io.Closeable;
import java.util.List;
import java.util.stream.Collectors;

public final class QuestSession implements Closeable {
  private final Quest quest;
  private final QuestPlayer player;
  private QuestSessionStatus status;
  private final List<QuestObjective> reachedObjectives;
  private final QuestSessionRegistry sessionRegistry;

  QuestSession(
    Quest quest,
    QuestPlayer player,
    QuestSessionStatus status,
    List<QuestObjective> reachedObjectives,
    QuestSessionRegistry sessionRegistry
  ) {
    this.quest = quest;
    this.player = player;
    this.status = status;
    this.reachedObjectives = reachedObjectives;
    this.sessionRegistry = sessionRegistry;
  }

  public QuestPlayer player() {
    return player;
  }

  public Quest quest() {
    return quest;
  }

  public QuestSessionStatus status() {
    return status;
  }

  public QuestProgress progress() {
    return null;
  }

  public List<QuestObjective> remainingObjectives() {
    return quest.objectives().stream()
      .filter(reachedObjectives::contains)
      .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public void close() {
    status = QuestSessionStatus.FINISHED;
  }
}
