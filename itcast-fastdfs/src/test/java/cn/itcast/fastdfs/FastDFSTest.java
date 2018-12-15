package cn.itcast.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {

    @Test
    public void test() throws Exception{
        //1. 获取追踪服务器配置文件路径并设置配置；
        String conf_filename = ClassLoader.getSystemResource("fastdfs/tracker.conf").getPath();
        ClientGlobal.init(conf_filename);
        //2. 创建TrackerClient，直接实例化；
        TrackerClient trackerClient = new TrackerClient();
        //3. 利用TrackerClient获取到TrackerServer；
        TrackerServer trackerServer = trackerClient.getConnection();
        //4. 创建一个存储服务器StorageServer，可以为空；
        StorageServer storageServer = null;
        //5. 利用TrackerServer和StorageServer创建一个StorageClient对象并上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //上传图片
        /**
         * 参数1：文件路径
         * 参数2：文件扩展名（后缀）
         * 参数3：文件信息
         * 返回的结果如下：
         * group1
         * M00/00/00/wKgMqFwU1R6AK5AsAABw0se6LsY186.jpg
         */
        String[] upload_file = storageClient.upload_file("d:\\itcast\\pics\\575968fcN2faf4aa4.jpg", "jpg", null);
        if (upload_file != null && upload_file.length > 0) {
            for (String str : upload_file) {
                System.out.println(str);
            }

            //组名
            String groupName = upload_file[0];
            //文件路径
            String filename = upload_file[1];

            //获取存储服务器的地址
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);
            for (ServerInfo serverInfo : serverInfos) {
                System.out.println("ip = " + serverInfo.getIpAddr() + "；port=" + serverInfo.getPort());
            }

            String url = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + filename;
            System.out.println("图片的可访问地址为：" + url);
        }

    }
}
