package cn.isohard.yuque.export.main.dt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDataDt<T> implements BaseDt<T> {

    private T data;

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }
}
