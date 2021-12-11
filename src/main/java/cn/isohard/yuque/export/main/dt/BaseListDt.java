package cn.isohard.yuque.export.main.dt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseListDt<T> implements BaseDt<List<T>> {
    private List<T> data;

    @Override
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
