package top.yunp.ss;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CountServer {

    private static Logger log = Logger.getLogger(CountServer.class.getName());

    static {
        log.setLevel(Level.ALL);
    }

    public static void main(String[] args) {
        NioEventLoopGroup rootLoop = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoop = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(rootLoop, workerLoop)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyChannelHandler());
                    }
                });
        try {
            ChannelFuture future = server.bind(8000).sync();
            log.info("Server started at port 8000");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rootLoop.shutdownGracefully();
            workerLoop.shutdownGracefully();
        }
    }
}
