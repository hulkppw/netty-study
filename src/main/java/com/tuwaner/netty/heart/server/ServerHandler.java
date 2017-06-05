package com.tuwaner.netty.heart.server;


import com.tuwaner.netty.heart.model.HeartInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerHandler extends ChannelInboundHandlerAdapter{

    private Map<String, HeartInfo> heartInfoMap = new HashMap<String, HeartInfo>();
    private static final List<String> authList = new ArrayList<String>();

    static {
        authList.add("10.144.24.191:9876");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String ){
            if (authList.contains(msg)){
                ctx.writeAndFlush("OK");
            }else {
                ctx.writeAndFlush("不在认证列表中...");
            }
        }else if (msg instanceof HeartInfo){
            System.out.println(msg.toString());
            ctx.writeAndFlush("心跳接收成功！");
            HeartInfo heartInfo = (HeartInfo) msg;
            heartInfoMap.put(heartInfo.getIp() + ":" + heartInfo.getPort(), heartInfo);
        }

    }

}
