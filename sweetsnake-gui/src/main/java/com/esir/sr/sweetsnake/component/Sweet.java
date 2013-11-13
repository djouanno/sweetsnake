package com.esir.sr.sweetsnake.component;

import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Sweet extends AbstractComponent
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 2902520296967734181L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public Sweet(final String _id, final int _x, final int _y) {
        super(_id, _x, _y, ComponentType.SWEET, GuiConstants.SWEET_ICON_PATH);
    }

}