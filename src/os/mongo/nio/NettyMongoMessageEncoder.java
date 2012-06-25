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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import os.mongo.ops.BaseMsg;
import os.utils.ByteArray;


/**
 * Encodes a {@link Number} into the binary representation prepended with
 * a magic number ('F' or 0x46) and a 32-bit length prefix.  For example, 42
 * will be encoded to { 'F', 0, 0, 0, 1, 42 }.
 */
public class NettyMongoMessageEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
    	ChannelBuffer buf = ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN,4);
    	if(BaseMsg.class.isAssignableFrom(msg.getClass())){
	    	BaseMsg q 	 = (BaseMsg)msg;
	        byte[] bytes = q.toBinaryMsg();
	        buf.writeBytes(bytes);
    	}else if(ByteBuffer.class.isAssignableFrom(msg.getClass())){
    		ByteBuffer q = (ByteBuffer)msg;
    		buf.writeBytes(q);
    	}else if(ByteArray.class.isAssignableFrom(msg.getClass())){
    		ByteArray q = (ByteArray)msg;
    		buf.writeBytes(q.array());
    	}
        return buf;
    }
}
