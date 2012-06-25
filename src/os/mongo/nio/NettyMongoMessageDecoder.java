package os.mongo.nio;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import os.mongo.Messages;
import os.mongo.ops.OpReply;



public class NettyMongoMessageDecoder extends FrameDecoder {
	
	
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
    	if (buffer.readableBytes() < 4) {
            // The length field was not received yet - return null.
            // This method will be invoked again when more packets are
            // received and appended to the buffer.
            return null;
         }

    	
    	buffer.markReaderIndex();
    	int mLength 		= buffer.readInt();
    	if(mLength>buffer.readableBytes()+4){
    		buffer.resetReaderIndex();
    		return null;
    	}
    	
		byte[] mBytes 	= new byte[mLength];
		buffer.resetReaderIndex();
		try{
			buffer.readBytes(mBytes);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	    return (OpReply)Messages.valueOf(mBytes);
    }
    
}
