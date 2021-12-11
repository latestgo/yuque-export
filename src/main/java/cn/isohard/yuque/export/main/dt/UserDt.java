package cn.isohard.yuque.export.main.dt;

import cn.isohard.yuque.export.main.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDt extends BaseListDt<User> {
}
