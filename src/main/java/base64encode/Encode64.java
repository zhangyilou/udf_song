package base64encode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encode64 {
    private String str;

    public Encode64(){
    }

    private String encode64(String value){
            try{
                return Base64.getEncoder()
                        .encodeToString(value.getBytes(StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException ex){
                throw new  RuntimeException(ex);
            }
    }
    public static void main(String[] args) {
        System.out.println(new Encode64().encode64("哈哈"));

    }
}




