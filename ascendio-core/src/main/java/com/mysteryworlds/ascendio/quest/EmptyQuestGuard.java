package com.mysteryworlds.ascendio.quest;

public final class EmptyQuestGuard implements QuestGuard {
  private EmptyQuestGuard() {
  }

  public static EmptyQuestGuard create() {
    return new EmptyQuestGuard();
  }

  @Override
  public boolean test(QuestPlayer player) {
    return true;
  }
}
