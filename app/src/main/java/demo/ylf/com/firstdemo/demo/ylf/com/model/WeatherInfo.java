package demo.ylf.com.firstdemo.demo.ylf.com.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/8.
 */
public class WeatherInfo implements Serializable {

    private  int result;
    private  String content;

    public int getResult() {
        return result;
    }

    public String getContent() {
        return content;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
