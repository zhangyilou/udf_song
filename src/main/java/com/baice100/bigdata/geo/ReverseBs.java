package com.baice100.bigdata.geo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ReverseBs {
    private Map<Integer, String> id2name = new HashMap<>();
    private Map<String, Integer> btMap = new TreeMap<>();

    private static ReverseBs instance;

    public static ReverseBs getInstance(String b2lidPath, String bsNamePath) {
        if (instance == null) {
            synchronized (ReverseBs.class) {
                if (instance == null) {
                    instance = new ReverseBs();
                    instance.loadDatas(b2lidPath, bsNamePath);
                }
            }
        }
        return instance;
    }

    public void loadDatas(String b2lidPath, String bsNamePath) {
        try {
            long cur = System.currentTimeMillis();
            String line;
            InputStream inputStreamReader = ReverseService.class.getClassLoader().getResourceAsStream(b2lidPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamReader, Charset.forName("utf-8")));
            while ((line = reader.readLine()) != null) {
                if (line.length() <= 0) {
                    continue;
                }
                String[] ds = line.split(",");
                btMap.put(ds[0].trim(), Integer.parseInt(ds[1].trim()));
            }
            inputStreamReader.close();
            reader.close();

            inputStreamReader = ReverseService.class.getClassLoader().getResourceAsStream(bsNamePath);
            reader = new BufferedReader(new InputStreamReader(inputStreamReader, Charset.forName("utf-8")));
            while ((line = reader.readLine()) != null) {
                if (line.length() <= 0) {
                    continue;
                }
                String[] ds = line.split(",");
                id2name.put(Integer.parseInt(ds[0].trim()), ds[1].trim());
            }
            inputStreamReader.close();
            reader.close();
            System.out.println("load bs data cost:" + (System.currentTimeMillis() - cur));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String find(String mtn) {
        return id2name.get(btMap.get(mtn));
    }

}
