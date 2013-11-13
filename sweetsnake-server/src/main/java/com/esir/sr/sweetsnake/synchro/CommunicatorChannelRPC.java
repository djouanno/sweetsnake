package com.esir.sr.sweetsnake.synchro;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jgroups.JChannel;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.server.Server;

@Component
public class CommunicatorChannelRPC {

	private static final String CHANNEL_NAME = "channel";
	private JChannel channel;
	private RpcDispatcher dispatcher;
	private RequestOptions options;

	@Autowired
	private Server server;

	@PostConstruct
	public void init() throws Exception {
		channel = new JChannel();
		dispatcher = new RpcDispatcher(channel, server);
		options = new RequestOptions(ResponseMode.GET_ALL, 5000);
		channel.connect(CHANNEL_NAME);
	}

	public void callMethod(String name, Object... param) throws Exception {
		Class[] classes = new Class[param.length];
		for (int i = 0; i < param.length; i++) {
			classes[i] = param[i].getClass();
		}
		RspList rsp_list = dispatcher.callRemoteMethods(null, name,
				param, classes, options);
	}
	
	@PreDestroy
	public void close() {
		channel.close();
		dispatcher.stop();
	}
}
