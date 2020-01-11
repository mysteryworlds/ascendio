package com.mysteryworlds.ascendio.quest;

import com.google.common.base.Preconditions;

public final class QuestRequirementsException extends QuestException {
  private QuestRequirementsException(String message) {
    super(message);
  }

  public static QuestRequirementsException withMessage(String message) {
    Preconditions.checkNotNull(message);
    return new QuestRequirementsException(message);
  }
}
