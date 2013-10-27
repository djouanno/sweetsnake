package com.esir.sr.sweetsnake.commons.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.commons.enumerations.Status;

public class PlayerDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = -7478382230116293470L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      name;
    private final Status      status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerDTO(final String _name, final Status _status) {
        name = _name;
        status = _status;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

}
