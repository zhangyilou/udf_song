package com.baice100.bigdata.impala.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 create function sha2(string, int) returns string
 location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 symbol='Sha2';

 create function sha256(string) returns string
 location 'hdfs://bigdatadm004:8020/data/lib/baice-bigdata-hive-udf-1.0-SNAPSHOT.jar'
 symbol='Sha2';
 */
public class Sha2 extends UDF {
    private transient MessageDigest digest;

    public Sha2() {
    }

    public String evaluate(String value) {
        return evaluate(value, new Integer(256));
    }

    public String evaluate(String value, Integer method) {
        try {
            digest = MessageDigest.getInstance("SHA-" + method);
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }
        digest.update(value.getBytes(), 0, value.length());
        byte[] resBin = digest.digest();
        return Hex.encodeHexString(resBin);
    }
}