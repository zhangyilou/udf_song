package com.baice100.bigdata.impala.udf;


import com.baice100.bigdata.geo.ReverseService;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Udf that returns true if two double arguments  are approximately equal.
 * Usage: > create fuzzy_equals(double, double) returns boolean
 * location '/user/cloudera/hive-udf-samples-1.0.jar'
 * SYMBOL='com.cloudera.impala.FuzzyEqualsUdf';
 * > select fuzzy_equals(1, 1.000001);
 * <p>
 * create function to_city(double, double, string, string) returns string
 * location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 * symbol='ToCity';
 * <p>
 * create function geo_to_city(double, double) returns string
 * location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 * symbol='ToCity';
 * <p>
 * create function ip_to_city(string) returns string
 * location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 * symbol='ToCity';
 * <p>
 * create function bt_to_city(string) returns string
 * location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 * symbol='BtToCity';
 */
public class ToCity extends UDF {
    public ToCity() {
    }

    public String evaluate(Double lat, Double lon, String bt, String ip) {
        return ReverseService.getInstance().find(lat, lon, bt, ip);
    }

    public String evaluate(String lats, String lons, String bt, String ip) {
        Double lat = null;
        Double lon = null;
        if (lats != null && lons != null) {
            try {
                lat = Double.valueOf(lats);
                lon = Double.valueOf(lons);
            } catch (Exception e) {

            }
        }
        return evaluate(lat, lon, bt, ip);
    }

    public String evaluate(Double lat, Double lon) {
        return evaluate(lat, lon, "", "");
    }

    public String evaluate(String lat, String lon) {
        return evaluate(lat, lon, "", "");
    }

    public String evaluate(String ip) {
        return evaluate((Double) null, (Double) null, "", ip);
    }
}
