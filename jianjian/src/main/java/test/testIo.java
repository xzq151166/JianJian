package test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testIo {
    public static void main(String[] args) throws IOException {
        /*File file = new File("D:/practice/Helloworld.txt");
        //创建文件，返回一个boolean值
        boolean isCreated = file.createNewFile();
        if(isCreated){
            System.out.println("创建文件成功");
        }else{
            System.out.println("创建失败.");
        }*/

        //判断文件是否是目录

       /* if(file.isFile()){
            System.out.println("是文件");
        }else{
            System.out.println("是目录.");
        }*/

        //将文件移动到文件夹里面去

        /*File file1 = new File("D:/practice/IOTest");
        file1.mkdirs();
        if(file.renameTo(file1)){
            System.out.println("移动成功");

        }else {
            System.out.println("移动失败.");
        }

        String[] list = file1.list();
        for (String  d:list) {
            System.out.println(d);

        }*/
        //从磁盘读出一个文件到内存中，再打印到控制台
       /* File file1 = new File("D:/practice/IOTest/Helloworld.txt");
        FileInputStream fileInputStream = new FileInputStream(file1);
        int len = 0;
        byte[] bytes = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        //把数据添加到stringBuffer
        while((len=fileInputStream.read(bytes))!=-1){
            stringBuffer.append(new String(bytes,0,len));
        }

        System.out.println(stringBuffer.toString());*/

        /*从程序中写一个HelloWorld你好世界到一个指定文件里面*/
       /* File file1 = new File("D:/practice/IOTest/Helloworld.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        fileOutputStream.write("helloworld2222".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();*/

        /*拷贝一个图片，从一个文件目录拷贝到另一个文件目录*/
        //源文件地址
       /* File file1 = new File("D:/practice/img/88.jpg");
        //目标文件地址
        File file2 = new File("D:/practice/IOTest/img/88.jpg");

        if(!file1.createNewFile()){
            System.out.println("创建文件失败");
        }
        FileInputStream f1 = new FileInputStream(file1);

        FileOutputStream o1 = new FileOutputStream(file2);

        //写入

        int len = 0;
        byte[] bytes = new byte[1024];

        while ((len=f1.read(bytes)) !=1){
            o1.write(bytes,0,len);
        }
        o1.flush();
        f1.close();
        o1.close();
        System.out.println("文件复制成功。");*/

        /*统计一个文件calcCharNum.txt（见附件）中字母'A'和'a'出现的总次数*/
        /*File file = new File("D:/practice/calcAndNum.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        int len=0;
        byte[] bytes = new byte[1024];
        StringBuffer sb = new StringBuffer();
        while ((len=fileInputStream.read(bytes))!=-1){
            sb.append(new String(bytes,0,len));
        }
        fileInputStream.close();

        //解析sb
        String str = sb.toString();
        char[] chars = str.toCharArray();
        int count = 0;
        for (char c:chars) {
            if(c=='a' || c=='A'){
                count++;
            }
        }

        System.out.println("总共有"+count+"个");*/

        /*统计包含中文字符的文件中的，每个字符出现的次数*/

        /*FileReader fr = new FileReader("D:/practice/calcAndChinaStr.txt");
        StringBuffer stringBuffer = new StringBuffer();
        while (fr.read()!=-1){
            stringBuffer.append(fr.read());
        }

        fr.close();
        System.out.println(stringBuffer.toString());*/

        //第二种方法,乱码专用，先将文件中的内容转化为fileInputSteam，再将inputStream传给InputSteamReader，根据指定编码转化为字符流，最后传入
        //传入BufferReader,通过readLine()方法逐行高效读出来，强烈推荐此方式
       /* InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("D:/practice/calcAndChinaStr.txt")),"GBK");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> strings = new ArrayList<>();
        String r ;
        while ((r=bufferedReader.readLine())!=null){
            Collections.addAll(strings,r);
        }
        System.out.println(strings.size());*/
        //

        PrintWriter pw = new PrintWriter("D:/practice/Helloworld.txt");

        pw.println("飞雪连天射白鹿");
        pw.println("金庸小说我都爱看");
        System.out.println("写出完毕！");
        pw.close();

    }
}
