package com.tuwaner.netty.heart;


import com.tuwaner.netty.heart.model.HeartInfo;
import io.netty.channel.ChannelHandlerContext;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;

public class HeartTask implements  Runnable{

    private ChannelHandlerContext ctx;

    private HeartInfo heartInfo = new HeartInfo();

    public HeartTask(ChannelHandlerContext ctx, String ip, int port){
        this.ctx = ctx;
        heartInfo.setIp(ip);
        heartInfo.setPort(port);
    }

    public void run() {
        //Sigar sigar = new Sigar();
        try {
            /*Mem mem = sigar.getMem();
            heartInfo.getMemInfo().put("total", String.valueOf(mem.getTotal()));
            heartInfo.getMemInfo().put("used", String.valueOf(mem.getUsed()));
            heartInfo.getMemInfo().put("free", String.valueOf(mem.getFree()));

            CpuPerc cpuPerc = sigar.getCpuPerc();
            heartInfo.getCpuInfo().put("user", String.valueOf(cpuPerc.getUser()));
            heartInfo.getCpuInfo().put("sys", String.valueOf(cpuPerc.getSys()));
            heartInfo.getCpuInfo().put("wait", String.valueOf(cpuPerc.getWait()));
            heartInfo.getCpuInfo().put("idle", String.valueOf(cpuPerc.getIdle()));*/
            heartInfo.setLasttime(new Date());
            ctx.writeAndFlush(heartInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
