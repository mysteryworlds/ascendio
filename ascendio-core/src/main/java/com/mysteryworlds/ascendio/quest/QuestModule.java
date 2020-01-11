package com.mysteryworlds.ascendio.quest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javax.inject.Singleton;

public final class QuestModule extends AbstractModule {
  private QuestModule() {
  }

  @Provides
  @Singleton
  QuestSessionRegistry provideSessionRegistry() {
    return QuestSessionRegistry.empty();
  }

  public static QuestModule create() {
    return new QuestModule();
  }
}
