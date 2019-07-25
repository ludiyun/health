package it.heima.com;

import Utils.QiniuUtils;
import com.itheima.entity.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.Set;

/*定时任务,清理垃圾图片*/
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        //清理图片
        Set<String> set = jedisPool.getResource()
                .sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
while (iterator.hasNext()){
    String pic = iterator.next();
    /*删除图片服务器中的图片*/
    QiniuUtils.deleteFileFromQiniu(pic);
    /*删除redis中的图片*/

    jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
}

    }
}
