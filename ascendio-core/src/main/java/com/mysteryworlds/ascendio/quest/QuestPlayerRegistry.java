package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class QuestPlayerRegistry {
  private final Map<UUID, QuestPlayer> players;

  private QuestPlayerRegistry(Map<UUID, QuestPlayer> players) {
    this.players = players;
  }

  public Optional<QuestPlayer> find(UUID uniqueId) {
    Preconditions.checkNotNull(uniqueId);
    return Optional.ofNullable(players.get(uniqueId));
  }

  public static QuestPlayerRegistry empty() {
    return new QuestPlayerRegistry(Maps.newHashMap());
  }
}
