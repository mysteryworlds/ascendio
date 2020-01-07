package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.UUID;

public final class Quest {
  private final UUID id;
  private final String name;
  private final String description;
  private final List<QuestObjective> objectives;
  private final QuestSessionFactory sessionFactory;

  Quest(
    UUID id,
    String name,
    String description,
    List<QuestObjective> objectives,
    QuestSessionFactory sessionFactory
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.objectives = objectives;
    this.sessionFactory = sessionFactory;
  }

  public QuestSession start(QuestPlayer player) {
    Preconditions.checkNotNull(player);
    return sessionFactory.createSession(this, player);
  }

  public UUID id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public List<QuestObjective> objectives() {
    return List.copyOf(objectives);
  }
}
