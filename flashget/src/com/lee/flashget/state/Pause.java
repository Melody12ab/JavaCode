package com.lee.flashget.state;

import javax.swing.ImageIcon;

import com.lee.flashget.ContextHolder;
import com.lee.flashget.object.Resource;
import com.lee.flashget.util.ImageUtil;

public class Pause extends AbstractState {
    
    @Override
    public ImageIcon getIcon() {
        return ImageUtil.PAUSE_IMAGE;
    }

    @Override
    public String getState() {
        return "pause";
    }

    @Override
    public void init(Resource resource) {
        //资源暂停后取消任务
        ContextHolder.dh.stopTimer(resource);
    }

}
