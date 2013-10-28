package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.enumeration.Status;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnableToMountGameSessionException;
import com.esir.sr.sweetsnake.factory.SweetSnakeFactory;
import com.esir.sr.sweetsnake.game.SweetSnakeGameSession;
import com.esir.sr.sweetsnake.game.SweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.game.SweetSnakePlayer;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger                                   log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakePlayer>                        players;
    private List<ISweetSnakeGameSession>                          gameSessions;
    private Map<ISweetSnakePlayer, ISweetSnakeGameSessionRequest> pendingRequests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    protected SweetSnakeServer() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    private SweetSnakeGameSessionDTO startGameSession(final ISweetSnakePlayer player1, final ISweetSnakePlayer player2) {
        final ISweetSnakeGameSession gameSession = new SweetSnakeGameSession(player1, player2);
        gameSessions.add(gameSession);

        final SweetSnakeGameSessionDTO gameSessionDTO = SweetSnakeFactory.convertGameSession(gameSession);

        player1.setStatus(Status.PLAYING);
        player2.setStatus(Status.PLAYING);
        gameSession.startGame();

        return gameSessionDTO;
    }

    private String retrieveClientName(final ISweetSnakeClientCallback client) {
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private ISweetSnakePlayer retrievePlayer(final String name) {
        if (players.containsKey(name)) {
            return players.get(name);
        }
        return null;
    }

    private ISweetSnakeGameSessionRequest retrieveRequest(final ISweetSnakePlayer player) {
        if (pendingRequests.containsKey(player)) {
            return pendingRequests.get(player);
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initialization of the SweetSnakeServer");
        players = new HashMap<String, ISweetSnakePlayer>();
        gameSessions = new ArrayList<ISweetSnakeGameSession>();
        pendingRequests = new HashMap<ISweetSnakePlayer, ISweetSnakeGameSessionRequest>();
    }

    @Override
    public void connect(final ISweetSnakeClientCallback client) throws UnableToConnectException {
        final String clientName = retrieveClientName(client);
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }
        if (players.containsKey(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }
        final ISweetSnakePlayer player = new SweetSnakePlayer(client);
        player.setStatus(Status.AVAILABLE);
        players.put(clientName, player);
        log.info("New client with username {} has connected", clientName);
    }

    @Override
    public void disconnect(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        if (players.containsKey(clientName)) {
            players.remove(clientName);
            log.info("Client with username {} has disconnected", clientName);
        }
    }

    @Override
    public SweetSnakeGameSessionRequestDTO requestGameSession(final ISweetSnakeClientCallback client, final SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException {
        if (!players.containsKey(otherPlayer.getName())) {
            throw new PlayerNotFoundException("unable to find the specified player");
        }

        final ISweetSnakePlayer player2 = retrievePlayer(otherPlayer.getName());
        if (player2.getStatus() != Status.AVAILABLE) {
            throw new UnableToMountGameSessionException("player is not available");
        }

        final ISweetSnakePlayer player1 = retrievePlayer(retrieveClientName(client));
        if (pendingRequests.containsKey(player1)) {
            throw new UnableToMountGameSessionException("a request is already pending");
        }

        final ISweetSnakeGameSessionRequest request = new SweetSnakeGameSessionRequest(player1, player2);
        pendingRequests.put(player1, request);
        player1.setStatus(Status.PENDING);

        final SweetSnakeGameSessionRequestDTO requestDTO = SweetSnakeFactory.convertGameSessionRequest(request);
        player2.getClientCallback().requestGame(requestDTO);
        log.info("Game session request between {} and {} is pending", player1.getName(), player2.getName());

        return requestDTO;
    }

    @Override
    public SweetSnakeGameSessionDTO acceptGameSession(final ISweetSnakeClientCallback client, final SweetSnakeGameSessionRequestDTO requestDTO) throws PlayerNotFoundException, UnableToMountGameSessionException {
        final ISweetSnakePlayer requestingPlayer = retrievePlayer(requestDTO.getRequestingPlayerName());
        if (requestingPlayer == null) {
            throw new PlayerNotFoundException("player not found");
        }

        final ISweetSnakeGameSessionRequest request = pendingRequests.get(requestingPlayer);
        if (request == null) {
            throw new UnableToMountGameSessionException("request not available");
        }
        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new UnableToMountGameSessionException("no matching request");
        }

        return startGameSession(request.getRequestingPlayer(), request.getRequestedPlayer());
    }

    @Override
    public void leaveGameSession(final ISweetSnakeClientCallback client) {
        // TODO
    }

    @Override
    public void cancelGameSessionRequest(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final ISweetSnakePlayer player = retrievePlayer(clientName);
        final ISweetSnakeGameSessionRequest request = retrieveRequest(player);
        if (request != null) {
            pendingRequests.remove(request);
            player.setStatus(Status.AVAILABLE);
        }
        log.info("Game session request canceled by player {}", player.getName());
    }

    @Override
    public void move(final Direction direction) {
        // TODO
    }

    @Override
    public List<SweetSnakePlayerDTO> getPlayersList(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<SweetSnakePlayerDTO> playersList = new ArrayList<SweetSnakePlayerDTO>();
        for (final String player : players.keySet()) {
            if (!clientName.equals(player)) {
                final SweetSnakePlayerDTO playerDTO = SweetSnakeFactory.convertPlayer(players.get(player));
                playersList.add(playerDTO);
            }
        }
        return playersList;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

}