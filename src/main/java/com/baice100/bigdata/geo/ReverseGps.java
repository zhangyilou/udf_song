package com.baice100.bigdata.geo;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseGps {

    static class Area {
        float[] retangle = new float[4];
        String location;
        Polygon polygon;
    }

    private List<Area> districtAreas = new ArrayList<>();
    private List<Area> cityAreas = new ArrayList<>();
    private static ReverseGps instance;

    public static ReverseGps getInstance(String filePath) {
        if (instance == null) {
            synchronized (ReverseGps.class) {
                if (instance == null) {
                    instance = new ReverseGps();
                    instance.loadDatas(filePath);
                }
            }
        }
        return instance;
    }

    public void loadDatas(String geoPath) {
        try {
            long cur = System.currentTimeMillis();
            InputStream inputStreamReader = ReverseService.class.getClassLoader().getResourceAsStream(geoPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamReader, Charset.forName("utf-8")));
            String t;
            while ((t = reader.readLine()) != null) {
                String[] arr = t.split("\t");
                Area area = new Area();
                area.location = arr[0];
                String linepoints = arr[1];
                area.retangle = new float[]{-100000, 100000, -100000, 100000};
                Polygon polygon = new Polygon();
                Arrays.asList(linepoints.split(";")).stream().forEach(line -> {
                    String[] a = line.split(",");
                    float lat = Float.parseFloat(a[1]);
                    float lon = Float.parseFloat(a[0]);
                    polygon.addPoint((int) (lat * 100000), (int) (lon * 100000));
                    if (lat > area.retangle[0]) area.retangle[0] = lat;
                    if (lat < area.retangle[1]) area.retangle[1] = lat;
                    if (lon > area.retangle[2]) area.retangle[2] = lon;
                    if (lon < area.retangle[3]) area.retangle[3] = lon;
                });
                area.polygon = polygon;
                if (area.location.endsWith("_未知")) {
                    cityAreas.add(area);
                } else {
                    districtAreas.add(area);
                }
            }
            reader.close();
            inputStreamReader.close();
            System.out.println("load gps data cost:" + (System.currentTimeMillis() - cur));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ReverseGps.Area searchCityByGps(double lat, double lon) {
        for (ReverseGps.Area area : cityAreas) {
            if (area != null && lat < area.retangle[0] && lat > area.retangle[1] && lon < area.retangle[2] && lon > area.retangle[3]) {
                if (area != null && area.polygon != null && area.polygon.contains(100000 * lat, 100000 * lon)) {
                    return area;
                }
            }
        }
        return null;
    }

    public ReverseGps.Area searchByGps(float lat, float lon) {
        for (ReverseGps.Area area : districtAreas) {
            if (area != null && lat < area.retangle[0] && lat > area.retangle[1] && lon < area.retangle[2] && lon > area.retangle[3]) {
                if (area != null && area.polygon != null && area.polygon.contains(100000 * lat, 100000 * lon)) {
                    return area;
                }
            }
        }
        return null;
    }

    public ReverseGps.Area searchByGps(double lat, double lon) {
        for (ReverseGps.Area area : districtAreas) {
            if (area != null && lat < area.retangle[0] && lat > area.retangle[1] && lon < area.retangle[2] && lon > area.retangle[3]) {
                if (area != null && area.polygon != null && area.polygon.contains(100000 * lat, 100000 * lon)) {
                    return area;
                }
            }
        }
        return null;
    }


//    public static void main(String[] args) throws IOException {
//        ReverseGps.loadPolygons("./datas/geo_final.csv");
//        String[] ds = "120.763065,23.565569".split(",");
//        double lat = Double.parseDouble(ds[1]);
//        double lon = Double.parseDouble(ds[0]);
//        ReverseGps.Area area = ReverseGps.searchByGps(lat, lon);
//        if (area != null) {
//            System.out.println(area.location);
//        }
//        area = ReverseGps.searchCityByGps(lat, lon);
//        if (area != null) {
//            System.out.println(area.location);
//        }
//    }
}

