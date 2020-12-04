package com.sluggard.feign.client;

import com.sluggard.feign.vo.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author：lizheng@homedone.net
 * @description
 * @since: JDK1.8
 * @version: 1.0
 * @date: 2020/9/17 16:15
 * 最后更新日期：
 * 修改人：
 * 复审人：
 * @Copyright © 2019-2021 杭州亿房达科技有限公司
 */
@FeignClient("user")
public interface UserClient {

    @GetMapping("/user/loadUserByUsername/{username}")
    Customer loadUserByUsername(@PathVariable String username);
}
