package com.esir.sr.sweetsnake.factory;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;

public class SweetSnakeFactory
{

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    public static SweetSnakeGameSessionRequestDTO convertGameSessionRequest(final ISweetSnakeGameSessionRequest request) {
        final SweetSnakeGameSessionRequestDTO requestDTO = new SweetSnakeGameSessionRequestDTO(request.getRequestingPlayer().getName(), request.getRequestedPlayer().getName());
        return requestDTO;
    }

    public static SweetSnakeGameSessionDTO convertGameSession(final ISweetSnakeGameSession session) {
        final SweetSnakeGameSessionDTO sessionDTO = new SweetSnakeGameSessionDTO(session.getPlayer1().getName(), session.getPlayer2().getName());
        return sessionDTO;
    }

    public static SweetSnakePlayerDTO convertPlayer(final ISweetSnakePlayer player) {
        final SweetSnakePlayerDTO playerDTO = new SweetSnakePlayerDTO(player.getName(), player.getStatus());
        return playerDTO;
    }

}