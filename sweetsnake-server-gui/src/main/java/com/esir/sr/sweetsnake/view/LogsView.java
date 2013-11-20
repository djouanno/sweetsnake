package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.JTextAreaOS;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class LogsView extends AbstractView
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 6428987586481231760L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(LogsView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The title label */
    private JLabel              titleLB;

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The log level combo box */
    private JComboBox<String>   levelsCB;

    /** The clear button */
    private JButton             clearBTN;

    /** The logs text area */
    private JTextArea           logTAR;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public LogsView() {
        super();
        log.info("Initializing the Players View");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#buildImpl()
     */
    @Override
    protected void buildImpl() {
        setLayout(new BorderLayout());

        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initTitleLB();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        topPL.add(titleLB, gbc);

        initLevelsCB();
        gbc.gridx = 1;
        gbc.weightx = 1000;
        topPL.add(levelsCB, gbc);

        initClearBTN();
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 10, 0, 10);
        topPL.add(clearBTN, gbc);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initLogTAR();
        centerPL.add(new JScrollPane(logTAR, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        final JTextAreaOS out = new JTextAreaOS(logTAR);
        System.setOut(new PrintStream(out));
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTopPL() {
        topPL = new JPanel(new GridBagLayout());
    }

    /**
     * 
     */
    private void initTitleLB() {
        titleLB = new JLabel("Output logs");
        titleLB.setFont(new Font("sans-serif", Font.BOLD, 14));
    }

    /**
     * 
     */
    private void initLevelsCB() {
        final String[] levels = { "ALL", "INFO", "DEBUG" };
        levelsCB = new JComboBox<String>(levels);
        levelsCB.addActionListener(new LevelListener());
    }

    /**
     * 
     */
    private void initClearBTN() {
        clearBTN = new JButton("clear logs");
        clearBTN.addActionListener(new ClearListener());
    }


    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel(new BorderLayout());
        centerPL.setBorder(new EmptyBorder(10, 0, 10, 10));
    }

    /**
     * 
     */
    private void initLogTAR() {
        logTAR = new JTextArea();
        logTAR.setEditable(false);
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class LevelListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String level = (String) levelsCB.getSelectedItem();

            if (level.equals("ALL")) {
            } else {
                final BufferedReader br = new BufferedReader(new StringReader(logTAR.getText()));
                final StringBuilder sb = new StringBuilder();
                String line = null;

                try {
                    while ((line = br.readLine()) != null) {
                        if (line.contains("[" + level + "]")) {
                            sb.append(line);
                        }
                    }
                    logTAR.setText(sb.toString());
                } catch (final IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }
    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class ClearListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            logTAR.setText("");
        }

    }

}
