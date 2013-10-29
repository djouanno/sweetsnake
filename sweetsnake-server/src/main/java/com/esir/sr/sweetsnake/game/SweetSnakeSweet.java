package com.esir.sr.sweetsnake.game;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.enumeration.Direction;

public class SweetSnakeSweet extends SweetSnakeAbstractElement
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeSweet.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/


    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeSweet() {
        super(Type.SWEET);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/


    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void move(final Direction direction) {
        log.info("Moving sweet to the {}", direction);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/


    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/


}
