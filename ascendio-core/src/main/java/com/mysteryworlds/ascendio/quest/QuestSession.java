package com.mysteryworlds.ascendio.quest;

import java.io.Closeable;
import java.util.List;
import java.util.stream.Collectors;

public final class QuestSession implements Closeable {
  private final Quest quest;
  private final QuestPlayer player;
  private final List<QuestObjective> reachedObjectives;
  private final QuestSessionRegistry sessionRegistry;
  private QuestSessionStatus status;

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

  public void cancel() {
    status = QuestSessionStatus.CANCELLED;
  }

  @Override
  public void close() {
    awardRewards();
    status = QuestSessionStatus.FINISHED;
    sessionRegistry.remove(this);
  }

  private void awardRewards() {
    var rewards = quest.rewards();
    for (QuestReward reward : rewards) {
      reward.award(player);
    }
  }
}
