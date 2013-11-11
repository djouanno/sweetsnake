package com.esir.sr.sweetsnake.component;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.GameConstants;

public class GameBoardPanel extends JPanel
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long    serialVersionUID = 8531962414769780455L;

    /** The logger */
    private static final Logger  log              = LoggerFactory.getLogger(GameBoardPanel.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The map width */
    private final int            width;

    /** The map height */
    private final int            height;

    /** The components */
    private final IComponent[][] components;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _width
     * @param _height
     * @param _nbSweets
     */
    public GameBoardPanel(final int _width, final int _height) {
        super();
        width = _width;
        height = _height;
        components = new IComponent[width][height];
        setLayout(null);
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param component
     */
    public void setComponent(final IComponent component) {
        log.debug("Setting element {} on the map", component);
        if (component != null) {
            components[component.getXPos()][component.getYPos()] = component;
            final JComponent comp = (JComponent) component;
            comp.setLocation(component.getXPos() * GameConstants.CELL_SIZE, component.getYPos() * GameConstants.CELL_SIZE);
            add(comp);
        }
    }

    /**
     * 
     * @param component
     */
    public void removeComponent(final IComponent component) {
        log.debug("Removing element {} from the map", component);
        if (component != null) {
            components[component.getXPos()][component.getYPos()] = null;
            remove((JComponent) component);
        }
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public IComponent getComponent(final int x, final int y) {
        return components[x][y];
    }

    /**
     * 
     * @param id
     * @return
     */
    public IComponent getComponentById(final String id) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final IComponent component = components[x][y];
                if (component != null && component.getId().equals(id)) {
                    return component;
                }
            }
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#getSize()
     */
    @Override
    public Dimension getSize() {
        return new Dimension(GameConstants.CELL_SIZE * width, GameConstants.CELL_SIZE * height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameConstants.CELL_SIZE * width, GameConstants.CELL_SIZE * height);
    }

}
