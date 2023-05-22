package com.atguigu.srb.core.controller.api;


import com.alibaba.fastjson.JSON;
import com.atguigu.common.result.R;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.hfb.RequestHelper;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.atguigu.srb.core.service.UserBindService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author ChenXW
 * @since 2023-05-15
 */
@Api(tags = "会员账号绑定接口")
@RestController
@Slf4j
@RequestMapping("/api/core/userBind")
public class ApiUserBindController {


    @Resource
    private UserBindService userBindService;

    @ApiOperation("账户绑定")
    @PostMapping("/auth/bind")
    public R bind(
            @RequestBody UserBindVO userBindVO,
            HttpServletRequest request) {

        // 从header中获取数据，并对token进行校验，确保用户已经登录，并从token中提取userId
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);


        // 根据userId做用户绑定,生成一个动态表单的字符串
        String formStr = userBindService.commitBindUser(userBindVO, userId);

        return R.ok().data("formStr", formStr);
    }


    @ApiOperation("账户绑定异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {

        // 汇付宝向尚融宝发送回调请求，请求携带的参数
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("账户绑定异步回调接受的参数如下：" + JSON.toJSONString(paramMap));

        // 校验
        if (!RequestHelper.isSignEquals(paramMap)) {
            log.error("账户绑定异步回调签名错误:" + JSON.toJSONString(paramMap));
            return "fail";
        }

        log.info("校验成功，开始绑定");

        userBindService.notify(paramMap);


        return "success";
    }

}

