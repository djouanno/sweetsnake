package com.esir.sr.sweetsnake.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esir.sr.sweetsnake.synchro.CommunicatorChannel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/sweetsnake-server-context-test.xml" })
public class TestReceiver
{

    private static ClassPathXmlApplicationContext context;

    @Autowired
    CommunicatorChannel                           channelComponant;

    @BeforeClass
    public static void beforeClass() {
        context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
    }

    @Test
    public void test() throws Exception {


        channelComponant.send("Coucou");
    }
}
