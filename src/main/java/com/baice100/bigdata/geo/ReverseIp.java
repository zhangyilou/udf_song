package com.baice100.bigdata.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReverseIp {

    private String[] names;
    private Long[] indexes;
    private int maxIndex;

    public static ReverseIp instance = null;

    public static ReverseIp getInstance(String filePath) {
        if (instance == null) {
            synchronized (ReverseIp.class) {
                if (instance == null) {
                    instance = new ReverseIp();
                    instance.loadDatas(filePath);
                }
            }
        }
        return instance;
    }


    public void loadDatas(String filePath) {
        try {
            ArrayList<String> tmpNames = new ArrayList<>();
            ArrayList<Long> tmpIndex = new ArrayList<>();

            InputStream inputStreamReader = ReverseService.class.getClassLoader().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamReader, Charset.forName("utf-8")));

            String line;
            long cur = System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
                if (line.length() <= 0) {
                    continue;
                }
                String[] ds = line.split(",");
                tmpNames.add(ds[1]);
                tmpIndex.add(Long.parseLong(ds[0]));
            }
            names = new String[tmpNames.size()];
            indexes = new Long[tmpIndex.size()];
            tmpIndex.toArray(indexes);
            tmpNames.toArray(names);
            maxIndex = indexes.length - 1;
            reader.close();
            inputStreamReader.close();
            System.out.println("load ip data cost:" + (System.currentTimeMillis() - cur));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String find(String ip) {
        long ipInt = ipToLong(ip);
        int idx = search(indexes, ipInt, 0, maxIndex);
//        System.out.println(ip + " " + ipInt + " " + indexes[idx] + " " + names[idx]);
        return names[idx];
    }

    public int search(Long[] seq, long v, int low, int high) {
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (v == seq[mid]) {
                return mid;
            } else if (v > seq[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return mid;
    }

    public long ipToLong(String strIp) {
        try {
            long[] ip = new long[4];
            // 先找到IP地址字符串中.的位置
            int position1 = strIp.indexOf(".");
            int position2 = strIp.indexOf(".", position1 + 1);
            int position3 = strIp.indexOf(".", position2 + 1);
            // 将每个.之间的字符串转换成整型
            ip[0] = Long.parseLong(strIp.substring(0, position1));
            ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
            ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
            ip[3] = Long.parseLong(strIp.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (Exception e) {
            return -1L;
        }
    }

    public static void main(String[] args) throws IOException {

        ReverseIp reverseIp = ReverseIp.getInstance("ip_standard.csv");

        List<String> ips = new ArrayList<>();
        ips.add("122.246.219.132");
        ips.add("197.199.253.1");
        ips.add("197.199.254.8");
        ips.add("218.253.0.168");
        ips.add("218.189.25.136");
        ips.add("218.253.0.78");
        ips.add("149.126.86.21");
        ips.add("111.92.162.18");
        ips.add("62.201.216.222");
        ips.add("218.176.242.12");
        ips.add("41.84.159.20");
        ips.add("1.26.105.255");
//        Integer.MAX_VALUE
        for (String ip : ips) {
            System.out.println(reverseIp.find(ip));
        }
    }
}

