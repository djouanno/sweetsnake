package com.esir.sr.sweetsnake.registry;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class PlayersRegistry implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /**
     * 
     */
    private static final long             serialVersionUID = -1611509528254160026L;

    /** The logger */
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(PlayersRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The players map */
    private Map<String, Player>           players;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected PlayersRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing players registry");
        players = new LinkedHashMap<String, Player>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param name
     * @return
     */
    public boolean contains(final String name) {
        return players.containsKey(name);
    }

    /**
     * 
     * @param player
     */
    public void add(final Player player) {
        players.put(player.getName(), player);
    }

    /**
     * 
     * @param name
     * @return
     * @throws PlayerNotFoundException
     */
    public Player get(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            throw new PlayerNotFoundException("player not found");
        }
        return players.get(name);
    }

    /**
     * 
     * @param name
     * @throws PlayerNotFoundException
     */
    public void remove(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            throw new PlayerNotFoundException("player not found");
        }
        players.remove(name);
    }

    /**
     * 
     * @return
     */
    public Set<String> getPlayersName() {
        return Collections.unmodifiableSet(players.keySet());
    }

}
