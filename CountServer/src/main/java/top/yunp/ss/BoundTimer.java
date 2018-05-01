package top.yunp.ss;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

public class BoundTimer {

    private final ChannelHandlerContext ctx;
    private Timer timer = null;

    public BoundTimer(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void activate() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int c = 0;

            @Override
            public void run() {
                c++;
                ctx.writeAndFlush(Unpooled.copiedBuffer(String.format("Count %d\n", c).getBytes(Charset.forName("utf-8"))));
            }
        }, 1000, 1000);
    }

    public void deactivate() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
