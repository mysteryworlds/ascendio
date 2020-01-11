package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;

public final class QuestFactory {
  private final QuestSessionFactory sessionFactory;

  @Inject
  private QuestFactory(QuestSessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Builder createBuilder() {
    return new Builder(
      sessionFactory,
      UUID.randomUUID(),
      Lists.newArrayList(),
      Lists.newArrayList(),
      EmptyQuestGuard.create()
    );
  }

  public static final class Builder {
    private final QuestSessionFactory sessionFactory;
    private UUID id;
    private String name;
    private String description;
    private Collection<QuestObjective> objectives;
    private Collection<QuestReward> rewards;
    private QuestGuard guard;

    private Builder(
      QuestSessionFactory sessionFactory,
      UUID id,
      Collection<QuestObjective> objectives,
      Collection<QuestReward> rewards,
      QuestGuard guard
    ) {
      this.sessionFactory = sessionFactory;
      this.id = id;
      this.objectives = objectives;
      this.rewards = rewards;
      this.guard = guard;
    }

    public Builder withId(UUID id) {
      Preconditions.checkNotNull(id);
      this.id = id;
      return this;
    }

    public Builder withDescription(String description) {
      Preconditions.checkNotNull(description);
      this.description = description;
      return this;
    }

    public Builder withObjective(QuestObjective objective) {
      Preconditions.checkNotNull(objective);
      objectives.add(objective);
      return this;
    }

    public Builder setObjectives(Collection<QuestObjective> objectives) {
      Preconditions.checkNotNull(objectives);
      this.objectives = Lists.newArrayList(objectives);
      return this;
    }

    public Builder withReward(QuestReward reward) {
      Preconditions.checkNotNull(reward);
      rewards.add(reward);
      return this;
    }

    public Builder setRewards(Collection<QuestReward> rewards) {
      Preconditions.checkNotNull(rewards);
      this.rewards = Lists.newArrayList(rewards);
      return this;
    }

    public Builder withName(String name) {
      Preconditions.checkNotNull(name);
      this.name = name;
      return this;
    }

    public Builder withGuard(QuestGuard guard) {
      Preconditions.checkNotNull(guard);
      this.guard = guard;
      return this;
    }

    public Quest create() {
      Preconditions.checkNotNull(id);
      Preconditions.checkNotNull(name);
      Preconditions.checkNotNull(description);
      Preconditions.checkNotNull(guard);
      return new Quest(
        id,
        name,
        description,
        List.copyOf(objectives),
        List.copyOf(rewards),
        sessionFactory,
        guard
      );
    }
  }
}
