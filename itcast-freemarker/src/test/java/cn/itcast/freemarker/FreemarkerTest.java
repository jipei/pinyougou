package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreemarkerTest {

    @Test
    public void test() throws Exception {
        //1. 创建一个配置对象（设置模版路径，编码）
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(FreemarkerTest.class, "/ftl");
        configuration.setDefaultEncoding("utf-8");
        //2. 获取模版
        Template template = configuration.getTemplate("test.ftl");
        //3. 创建数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "itcast");
        dataModel.put("msg", "圣诞快乐！");

        List<Map<String, Object>> goodsList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", "苹果");
        map1.put("price", 5.0);
        goodsList.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "香蕉");
        map2.put("price", 3.0);
        goodsList.add(map2);

        dataModel.put("goodsList", goodsList);

        //4. 输出文件到指定路径
        FileWriter fileWriter = new FileWriter("D:\\itcast\\test\\test.html");

        template.process(dataModel, fileWriter);

        fileWriter.close();
    }
}
