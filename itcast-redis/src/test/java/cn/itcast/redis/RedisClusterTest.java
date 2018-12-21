package cn.itcast.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis-cluster.xml")
public class RedisClusterTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试字符串
     */
    @Test
    public void testString(){
        redisTemplate.boundValueOps("str_abc").set("i love jbl very much.传智播客！");
        Object obj = redisTemplate.boundValueOps("str_abc").get();
        System.out.println(obj);
    }


    /**
     * 测试hash；如果有更新则以最新的排在最前面
     */
    @Test
    public void testHash(){
        //键名 ；  域名，域值
        redisTemplate.boundHashOps("h_key").put("f1", "a");
        redisTemplate.boundHashOps("h_key").put("f2", "b");
        redisTemplate.boundHashOps("h_key").put("f3", "c");
        List obj = redisTemplate.boundHashOps("h_key").values();
        System.out.println(obj);
    }

    /**
     * 测试列表；按照元素的顺序排
     */
    @Test
    public void testList(){
        redisTemplate.boundListOps("l_key").leftPush("b");
        redisTemplate.boundListOps("l_key").leftPush("a");
        redisTemplate.boundListOps("l_key").rightPush("c");
        //获取列表的所有元素，-1表示最后一个元素
        Object obj = redisTemplate.boundListOps("l_key").range(0, -1);
        System.out.println(obj);
    }

    /**
     * 测试集合；无序
     */
    @Test
    public void testSet(){
        redisTemplate.boundSetOps("set_key").add("a", "b", "c");
        Object obj = redisTemplate.boundSetOps("set_key").members();
        System.out.println(obj);
    }

    /**
     * 测试有序集合
     * 按照分值升序排序
     */
    @Test
    public void testSortedSet(){
        redisTemplate.boundZSetOps("z_key").add("b", 15);
        redisTemplate.boundZSetOps("z_key").add("a", 5);
        redisTemplate.boundZSetOps("z_key").add("c", 25);
        Object obj = redisTemplate.boundZSetOps("z_key").range(0, -1);
        System.out.println(obj);
    }

}
