package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.constants.SweetSnakePropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;
import com.esir.sr.sweetsnake.factory.SweetSnakeFactory;
import com.esir.sr.sweetsnake.game.SweetSnakeSnake;
import com.esir.sr.sweetsnake.game.SweetSnakeSweet;
import com.esir.sr.sweetsnake.utils.Pair;

public class SweetSnakeGameSession implements ISweetSnakeGameSession
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger                     log = LoggerFactory.getLogger(SweetSnakeGameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String                                      id;
    private final ISweetSnakePlayer                           player1, player2;
    // TODO do not use Pair, find another way
    private final Pair<ISweetSnakePlayer, ISweetSnakeElement> player1Snake;
    private final Pair<ISweetSnakePlayer, ISweetSnakeElement> player2Snake;
    private ISweetSnakeElement[][]                            gameMap;
    private boolean                                           gameStarted;
    private int                                               remainingSweets;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _player1
     * @param _player2
     */
    public SweetSnakeGameSession(final ISweetSnakePlayer _player1, final ISweetSnakePlayer _player2) {
        log.info("Initializing a new game session between {} and {}", _player1.getName(), _player2.getName());
        id = RandomStringUtils.randomAlphanumeric(SweetSnakePropertiesConstants.GENERATED_ID_LENGTH);
        player1 = _player1;
        player2 = _player2;
        player1Snake = new Pair<ISweetSnakePlayer, ISweetSnakeElement>(player1, new SweetSnakeSnake());
        player2Snake = new Pair<ISweetSnakePlayer, ISweetSnakeElement>(player2, new SweetSnakeSnake());
        gameStarted = false;
        remainingSweets = SweetSnakeGameConstants.NUMBER_OF_SWEETS;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param player
     * @return
     */
    private ISweetSnakeElement retrievePlayerSnake(final ISweetSnakePlayer player) {
        if (player == player1Snake.getFirst()) {
            return player1Snake.getSecond();
        }
        if (player == player2Snake.getFirst()) {
            return player2Snake.getSecond();
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#startGame()
     */
    @Override
    public void startGame() {
        log.info("Initializing a new gameboard between {} and {}", player1.getName(), player2.getName());

        gameMap = new ISweetSnakeElement[SweetSnakeGameConstants.GRID_SIZE][SweetSnakeGameConstants.GRID_SIZE];
        gameMap[0][0] = player1Snake.getSecond();
        gameMap[SweetSnakeGameConstants.GRID_SIZE - 1][SweetSnakeGameConstants.GRID_SIZE - 1] = player2Snake.getSecond();

        final Random random = new Random();
        for (int i = 0; i < SweetSnakeGameConstants.NUMBER_OF_SWEETS; i++) {
            int j, k;
            do {
                j = random.nextInt(SweetSnakeGameConstants.GRID_SIZE);
                k = random.nextInt(SweetSnakeGameConstants.GRID_SIZE);
            } while (gameMap[j][k] != null);

            gameMap[j][k] = new SweetSnakeSweet();
            gameMap[j][k].setXY(j, k);
        }

        try {
            player1.getClientCallback().startGame(SweetSnakeFactory.convertGameSession(this));
            player2.getClientCallback().startGame(SweetSnakeFactory.convertGameSession(this));

            // TODO maybe reconfirm from client side to synchronize game start
            gameStarted = true;

            player1.setStatus(SweetSnakePlayerStatus.PLAYING);
            player2.setStatus(SweetSnakePlayerStatus.PLAYING);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#stopGame()
     */
    @Override
    public void stopGame() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#movePlayer(com.esir.sr.sweetsnake.api. ISweetSnakePlayer,
     * com.esir.sr.sweetsnake.enumeration.Direction)
     */
    @Override
    public void movePlayer(final ISweetSnakePlayer player, final SweetSnakeDirection direction) {
        final ISweetSnakeElement playerSnake = retrievePlayerSnake(player);
        if (playerSnake == null) {
            log.error("Can't find player's snake"); // TODO throw exception
        }

        playerSnake.move(direction);

        final ISweetSnakeElement element = gameMap[playerSnake.getX()][playerSnake.getY()];
        if (element != null) {
            gameMap[playerSnake.getX()][playerSnake.getY()] = null;
            remainingSweets--;
            log.info("{} has eaten a sweet ; {} remaining on the map", player.getName(), remainingSweets);
            if (remainingSweets == 0) {
                stopGame();
            }
        }
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getPlayer1()
     */
    @Override
    public ISweetSnakePlayer getPlayer1() {
        return player1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getPlayer2()
     */
    @Override
    public ISweetSnakePlayer getPlayer2() {
        return player2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#isGameStarted()
     */
    @Override
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}