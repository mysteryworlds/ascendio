package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.UUID;

public final class Quest {
  private final UUID id;
  private final String name;
  private final String description;
  private final List<QuestObjective> objectives;
  private final List<QuestReward> rewards;
  private final QuestSessionFactory sessionFactory;
  private final QuestGuard guard;

  Quest(
    UUID id,
    String name,
    String description,
    List<QuestObjective> objectives,
    List<QuestReward> rewards,
    QuestSessionFactory sessionFactory,
    QuestGuard guard
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.objectives = objectives;
    this.rewards = rewards;
    this.sessionFactory = sessionFactory;
    this.guard = guard;
  }

  public QuestSession start(
    QuestPlayer player
  ) throws QuestRequirementsException, QuestSessionAlreadyExistsException {
    Preconditions.checkNotNull(player);
    ensureQuestRequirements(player);
    return sessionFactory.createSession(this, player);
  }

  private void ensureQuestRequirements(QuestPlayer player)
    throws QuestRequirementsException {
    if (!guard.test(player)) {
      throw QuestRequirementsException.withMessage("Requirements not met");
    }
  }

  public List<QuestObjective> objectives() {
    return List.copyOf(objectives);
  }

  public List<QuestReward> rewards() {
    return List.copyOf(rewards);
  }
}
