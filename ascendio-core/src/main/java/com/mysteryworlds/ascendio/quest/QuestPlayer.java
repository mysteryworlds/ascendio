package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import java.util.UUID;

public final class QuestPlayer {
  private final UUID id;

  private QuestPlayer(UUID id) {
    this.id = id;
  }

  public static QuestPlayer withId(UUID id) {
    Preconditions.checkNotNull(id);
    return new QuestPlayer(id);
  }
}
