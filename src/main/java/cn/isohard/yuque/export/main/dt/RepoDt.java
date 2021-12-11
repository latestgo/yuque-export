package cn.isohard.yuque.export.main.dt;

import cn.isohard.yuque.export.main.entity.Repo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDt extends BaseListDt<Repo>  {
}
