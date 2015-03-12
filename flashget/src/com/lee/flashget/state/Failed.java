package com.lee.flashget.state;

import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.lee.flashget.ContextHolder;
import com.lee.flashget.object.Resource;
import com.lee.flashget.util.ImageUtil;

public class Failed extends AbstractState {

    @Override
    public ImageIcon getIcon() {
        return ImageUtil.FAILED_IMAGE;
    }
    
    public String getState() {
        return "failed";
    }

    @Override
    public void init(Resource resource) {
        System.out.println(resource.getSaveFile().getAbsolutePath());
        System.out.println("被停止了");
        //当任务被设置为不可下载时, 停止时间计算器
        ContextHolder.dh.stopTimer(resource);       
    }

}

