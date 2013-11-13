package com.esir.sr.sweetsnake.synchro;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.session.Player;

@Component
public class ReceiverMessage extends ReceiverAdapter {

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);

    
	@Override
	public void receive(Message msg) {
		log.debug(msg.getObject().toString());
		super.receive(msg);
	}
}
