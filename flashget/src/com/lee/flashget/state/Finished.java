package com.lee.flashget.state;

import javax.swing.ImageIcon;

import com.lee.flashget.ContextHolder;
import com.lee.flashget.object.Resource;
import com.lee.flashget.util.FileUtil;
import com.lee.flashget.util.ImageUtil;

public class Finished extends AbstractState {

    @Override
    public ImageIcon getIcon() {
        return ImageUtil.FINISHED_IMAGE;
    }

    public String getState() {
        return "finished";
    }

    public void init(Resource resource) {
        //删除临时文件
        FileUtil.deletePartFiles(resource);
        //资源下载完成后取消任务
        ContextHolder.dh.stopTimer(resource);
    }
    
    
}
