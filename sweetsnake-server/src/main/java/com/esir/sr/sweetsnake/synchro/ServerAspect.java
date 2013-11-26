package com.esir.sr.sweetsnake.synchro;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.registry.GameRequestsRegistry;
import com.esir.sr.sweetsnake.registry.GameSessionsRegistry;
import com.esir.sr.sweetsnake.registry.PlayersRegistry;
import com.esir.sr.sweetsnake.session.Player;

@Aspect
@Component
public class ServerAspect
{
    /** The players registry */
    @Autowired
    private PlayersRegistry               players;

    /** The requests registry */
    @Autowired
    private GameRequestsRegistry          gameRequests;

    /** The sessions registry */
    @Autowired
    private GameSessionsRegistry          gameSessions;

    @Autowired
    CommunicatorChannel                   channel;

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);

    // @After("execution(* com.esir.sr.sweetsnake.api.IServer.*(..))")
    // public void afterConnect(final JoinPoint joinPoint) {
    // log.debug("ASPECT --> Afer Connect");
    // try {
    // channel.send(players);
    // channel.send(gameRequests);
    // channel.send(gameSessions);
    // } catch (final Exception e) {
    // e.printStackTrace();
    // }
    // }



    @After("execution(* com.esir.sr.sweetsnake.server.Server.connect(..))")
    public void afterConnect(final JoinPoint joinPoint) {
        log.debug("ASPECT --> Afer Connect");
        try {
            channel.send(players);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    @After("execution(* com.esir.sr.sweetsnake.server.disconnect(..))")
    public void afterDisconnect() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.requestGame(..))")
    public void afterRequestGame() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.cancelGameRequest(..))")
    public void afterCancelGameRequest() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.acceptGame(..))")
    public void afterAcceptGame() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.refuseGame(..))")
    public void afterRefuseGame() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.leaveGame(..))")
    public void afterLeaveGame() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.getPlayersList(..))")
    public void afterGetPlayersList() {

    }

    @After("execution(* com.esir.sr.sweetsnake.server.requestMove(..))")
    public void afterRequestMove() {

    }
}
