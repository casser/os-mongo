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

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.queue.BlockingReadHandler;

/**
 * Creates a newly configured {@link ChannelPipeline} for a client-side channel.
 */
public class NettyMongoPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        
        pipeline.addLast("decoder", new NettyMongoMessageDecoder());
        pipeline.addLast("encoder", new NettyMongoMessageEncoder());
        
        pipeline.addLast("result", new BlockingReadHandler<Object>());
        
        return pipeline;
    }
}
