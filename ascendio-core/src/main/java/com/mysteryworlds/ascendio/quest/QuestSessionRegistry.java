package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

public final class QuestSessionRegistry {
  private final Set<QuestSession> sessions;

  private QuestSessionRegistry(Set<QuestSession> sessions) {
    this.sessions = sessions;
  }

  public static QuestSessionRegistry empty() {
    return new QuestSessionRegistry(Sets.newConcurrentHashSet());
  }

  public Set<QuestSession> findByPlayer(QuestPlayer player) {
    Preconditions.checkNotNull(player);
    return sessions.stream()
      .filter(questSession -> questSession.player().equals(player))
      .collect(Collectors.toUnmodifiableSet());
  }

  public Set<QuestSession> findByQuest(Quest quest) {
    Preconditions.checkNotNull(quest);
    return sessions.stream()
      .filter(questSession -> questSession.quest().equals(quest))
      .collect(Collectors.toUnmodifiableSet());
  }

  void remove(QuestSession session) {
    Preconditions.checkNotNull(session);
    sessions.remove(session);
  }

  void register(QuestSession session) {
    Preconditions.checkNotNull(session);
    sessions.add(session);
  }
}
