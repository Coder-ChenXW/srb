package com.atguigu.srb.sms.controller.api;


import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.util.RandomUtils;
import com.atguigu.common.util.RegexValidateUtils;
import com.atguigu.srb.sms.service.SmsService;
import com.atguigu.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
@CrossOrigin
@Slf4j
public class ApiSmsController {


    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @description: 发送验证码
     * @author: ChenXW
     * @date: 2023/5/18 12:46
     */
    @ApiOperation("发送验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable
            String mobile){

        // 校验手机号不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // 校验手机号码的合法性
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        String code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        log.info("验证码：" + map.get("code"));
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE,map);

        // 将验证码存入redis
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile, map.get("code"), 5, TimeUnit.DAYS);


        return R.ok().message("短信发送成功");
    }

}
