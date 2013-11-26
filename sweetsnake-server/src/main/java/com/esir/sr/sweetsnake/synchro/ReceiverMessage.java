package com.esir.sr.sweetsnake.synchro;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.registry.GameRequestsRegistry;
import com.esir.sr.sweetsnake.registry.GameSessionsRegistry;
import com.esir.sr.sweetsnake.registry.PlayersRegistry;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.Player;

@Component
public class ReceiverMessage extends ReceiverAdapter
{

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);

    /** The players registry */
    @Autowired
    private PlayersRegistry               players;

    /** The requests registry */
    @Autowired
    private GameRequestsRegistry          gameRequests;

    /** The sessions registry */
    @Autowired
    private GameSessionsRegistry          gameSessions;

    @Override
    public void receive(final Message msg) {
        final Object obj = msg.getObject();

        if (obj.getClass() == PlayersRegistry.class) {
            log.debug("Avant" + players.getPlayersName().toString());
            players = (PlayersRegistry) obj;
            log.debug("Apres" + players.getPlayersName().toString());
        }
        if (obj.getClass() == GameSessionsRegistry.class) {
            gameSessions = (GameSessionsRegistry) obj;
        }
        if (obj.getClass() == GameRequest.class) {
            gameRequests = (GameRequestsRegistry) obj;
        }
        super.receive(msg);
    }
}
