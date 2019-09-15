package com.cxp.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-09-15 18:01
 */
public class NettyServer implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final int port;
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup ;
    private ChannelFuture channelFuture;

    public NettyServer(int port){
        this.port = port;
    }

    public void init(){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                //配置TCP参数，握手字符串长度设置
                .option(ChannelOption.SO_BACKLOG,1024)
                //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                .option(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new NettyServerInitializer());
        try {
            channelFuture = bootstrap.bind(this.port).sync();
            logger.info("netty服务启动: [port:" + this.port + "]");
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            logger.error("NettyServer init exception : "+e.getMessage(),e);
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void run() {
        init();
    }
}
