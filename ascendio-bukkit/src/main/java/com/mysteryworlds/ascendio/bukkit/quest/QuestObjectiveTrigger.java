package com.mysteryworlds.ascendio.bukkit.quest;

import com.mysteryworlds.ascendio.quest.QuestPlayer;
import com.mysteryworlds.ascendio.quest.QuestPlayerRegistry;
import com.mysteryworlds.ascendio.quest.QuestSession;
import com.mysteryworlds.ascendio.quest.QuestSessionRegistry;
import java.util.Optional;
import javax.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public final class QuestObjectiveTrigger implements Listener {
  private final QuestSessionRegistry sessionRegistry;
  private final QuestPlayerRegistry playerRegistry;

  @Inject
  private QuestObjectiveTrigger(
    QuestSessionRegistry sessionRegistry,
    QuestPlayerRegistry playerRegistry
  ) {
    this.sessionRegistry = sessionRegistry;
    this.playerRegistry = playerRegistry;
  }

  @EventHandler
  public void blockBreak(BlockBreakEvent blockBreak) {
    Optional<QuestPlayer> player = findPlayer(blockBreak.getPlayer());
    player.ifPresent(questPlayer -> blockBreak(questPlayer, blockBreak));
  }

  private void blockBreak(
    QuestPlayer player,
    BlockBreakEvent blockBreak
  ) {
    var sessions = sessionRegistry.findByPlayer(player);
    for (var session : sessions) {
      triggerBlockBreak(session, blockBreak);
    }
  }

  private void triggerBlockBreak(
    QuestSession session,
    BlockBreakEvent blockBreak
  ) {

  }

  private Optional<QuestPlayer> findPlayer(Player player) {
    return playerRegistry.find(player.getUniqueId());
  }
}
