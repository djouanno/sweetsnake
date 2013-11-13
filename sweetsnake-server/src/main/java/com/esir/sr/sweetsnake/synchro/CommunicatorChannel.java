package com.esir.sr.sweetsnake.synchro;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommunicatorChannel {

	private JChannel channel;
	
	@Autowired
	private ReceiverMessage receiverMessage;
	
	@PostConstruct
	public void init() throws Exception {
		channel = new JChannel();
		channel.setReceiver(receiverMessage);
		channel.connect("channel");
	}
	
	public void send(Object obj) throws Exception {
		channel.send(new Message(null, null, obj));
	}
	
	@PreDestroy
	public void close() {
		channel.close();
	}
}
