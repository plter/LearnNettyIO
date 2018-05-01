package top.yunp.ss;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MyChannelHandler extends ChannelInboundHandlerAdapter {


    private static Logger log = Logger.getLogger(MyChannelHandler.class.getName());

    static {
        log.setLevel(Level.ALL);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        BoundTimer boundTimer = new BoundTimer(ctx);
        ctx.channel().attr(AttributeKey.valueOf("boundTimer")).set(boundTimer);
        boundTimer.activate();
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Attribute<BoundTimer> attr = ctx.channel().attr(AttributeKey.valueOf("boundTimer"));
        attr.get().deactivate();
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }
}
