/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package os.mongo.nio;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.queue.BlockingReadHandler;

import os.mongo.Message;
import os.mongo.ops.OpReply;


/**
 * Sends a sequence of integers to a {@link FactorialServer} to calculate
 * the factorial of the specified integer.
 */
public class NettyMongoClient {
	
	
    private final String host;
    private final int port;
    
    private Channel channel;
    private ClientBootstrap bootstrap;
    
    public NettyMongoClient(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        init();
    }

    public void init() throws InterruptedException {
        bootstrap = new ClientBootstrap(
        	new NioClientSocketChannelFactory(
        		Executors.newCachedThreadPool(),
        		Executors.newCachedThreadPool()
        	)
        );
        bootstrap.setOption( "bufferFactory", 
        	new HeapChannelBufferFactory(ByteOrder.LITTLE_ENDIAN )
        );
        bootstrap.setPipelineFactory(new NettyMongoPipelineFactory());
        channel = bootstrap.connect(new InetSocketAddress(host, port)).awaitUninterruptibly().getChannel();
    }
    
    public Channel getChannel(){
    	return channel;
    }
    
    public void shutdown(){
    	channel.close();
    	bootstrap.releaseExternalResources();
    }
    
    public OpReply call(Message q) throws Exception {
    	return call(q,getChannel());
    }
    
    public OpReply call(Message q, Channel channel) throws Exception {
    	return send(q,channel,q.isSafe());
    }
    
    public OpReply send(Object q, Boolean safe) throws Exception {
    	return send(q,getChannel(),safe);
    }
    
    @SuppressWarnings("unchecked")
    public OpReply send(Object q, Channel channel, Boolean safe) throws Exception {
    	while(!channel.isWritable()){
    	}
    	if(safe){
    		BlockingReadHandler<OpReply> result = (BlockingReadHandler<OpReply>) channel.getPipeline().get("result");
    		channel.write(q);
    		return result.read();
    	}else{
    		channel.write(q);
    		return null;
    	}
    }
    
    
}
