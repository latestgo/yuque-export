package cn.isohard.yuque.export.main.dt;

import cn.isohard.yuque.export.main.entity.Doc;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocDt extends BaseListDt<Doc>{
}
