package com.baice100.bigdata.geo;

public class ReverseService {
    public static boolean init = false;
    private ReverseGps reverseGps;
    private ReverseIp reverseIp;
    private ReverseBs reverseBs;

    public static ReverseService instance = null;

    public static ReverseService getInstance() {
        if (instance == null) {
            synchronized (ReverseService.class) {
                if (instance == null) {
                    instance = new ReverseService();
                }
            }
        }
        return instance;
    }

    ReverseService() {
        String ipPath = "ip_standard.csv";
        String bsPath = "base2id.csv";
        String bsNamePath = "id2name.csv";
        String gpsPath = "geo_final.csv";
        loadData(gpsPath, bsPath, bsNamePath, ipPath);
    }

    public void loadData(String gpsPath, String bsPath, String bsNamePath, String ipPath) {
        reverseGps = ReverseGps.getInstance(gpsPath);
        reverseIp = ReverseIp.getInstance(ipPath);
        reverseBs = ReverseBs.getInstance(bsPath, bsNamePath);
        init = true;
    }

    public String find(Double lat, Double lon, String bt, String ip) {
        if (lat != null && lon != null && lat > 0 && lon > 0) {
            try {
                ReverseGps.Area area = reverseGps.searchByGps(lat, lon);
                if (area != null) {
                    return area.location;
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        if (bt != null && bt.length() > 0) {
            try {
                String[] l = bt.split(",");
                bt = l[2] + "_" + l[3];
                String result = reverseBs.find(bt);
                if (result != null) {
                    return result;
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        // 都没找到的时候，回过头找经纬度的市级范围
        if (lat != null && lon != null && lat > 0 && lon > 0) {
            try {
                ReverseGps.Area area = reverseGps.searchCityByGps(lat, lon);
                if (area != null) {
                    return area.location;
                }
            } catch (Exception e) {
//				e.printStackTrace();
            }
        }
        if (ip != null && ip.length() > 0) {
            try {
                String result = reverseIp.find(ip);
                if (result != null) {
                    return result;
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        return "未知";
    }
}
