package com.esir.sr.sweetsnake.session;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Player implements Serializable
{


    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /**
     * 
     */
    private static final long             serialVersionUID = -2720339605523394835L;

    /** The logger */
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    private final IClientCallback         client;

    /** The player name */
    private String                        name;

    /** The player status */
    private PlayerStatus                  status;

    /** The sent request (only one at a time) */
    private String                        sentRequestId;

    /** The received request id (only one at a time) */
    private String                        receivedRequestId;

    /** The current game session id (only one at a time) */
    private String                        gameSessionId;

    /** The current game player's number */
    private int                           number;

    /** The current game player's snake id */
    private String                        snakeId;

    /** The current game player's score */
    private int                           score;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _client
     */
    public Player(final IClientCallback _client) {
        client = _client;
        try {
            name = client.getUsername();
        } catch (final RemoteException e) {
            log.error("unable to retrieve client name : {}", e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name + "[status=" + status + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public IClientCallback getClientCallback() {
        return client;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * 
     * @return
     */
    public String getSentRequestId() {
        return sentRequestId;
    }

    /**
     * 
     * @return
     */
    public String getReceivedRequestId() {
        return receivedRequestId;
    }

    /**
     * 
     * @return
     */
    public String getGameSessionId() {
        return gameSessionId;
    }

    /**
     * 
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * 
     * @return
     */
    public String getSnakeId() {
        return snakeId;
    }

    /**
     * 
     * @return
     */
    public int getScore() {
        return score;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * 
     * @param _status
     */
    public void setStatus(final PlayerStatus _status) {
        status = _status;
    }

    /**
     * 
     * @param _sentRequestId
     */
    public void setSentRequestId(final String _sentRequestId) {
        sentRequestId = _sentRequestId;
    }

    /**
     * 
     * @param _receivedRequestId
     */
    public void setReceivedRequestId(final String _receivedRequestId) {
        receivedRequestId = _receivedRequestId;
    }

    /**
     * 
     * @param _gameSession
     */
    public void setGameSessionId(final String _gameSessionId) {
        gameSessionId = _gameSessionId;
    }

    /**
     * 
     * @param _number
     */
    public void setNumber(final int _number) {
        number = _number;
    }

    /**
     * 
     * @param _snakeId
     * @return
     */
    public void setSnakeId(final String _snakeId) {
        snakeId = _snakeId;
    }

    /**
     * 
     * @param _score
     */
    public void setScore(final int _score) {
        score = _score;
    }

}
