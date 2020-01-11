package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;

public final class QuestSessionAlreadyExistsException extends QuestException {
  private QuestSessionAlreadyExistsException(String message) {
    super(message);
  }

  public static QuestSessionAlreadyExistsException withMessage(String message) {
    Preconditions.checkNotNull(message);
    return new QuestSessionAlreadyExistsException(message);
  }
}
