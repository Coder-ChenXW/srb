package com.atguigu.srb.core.controller.api;


import com.atguigu.common.result.R;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.pojo.vo.BorrowerVO;
import com.atguigu.srb.core.service.BorrowerService;
import io.jsonwebtoken.JwtBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author ChenXW
 * @since 2023-05-15
 */
@Api(tags = "借款人")
@RestController
@RequestMapping("/api/core/borrower")
@Slf4j
public class ApiBorrowerController {


    @Resource
    private BorrowerService borrowerService;

    /**
     * @description: 保存借款人信息
     * @author: ChenXW
     * @date: 2023/5/25 21:28
     */
    @ApiOperation("保存借款人信息")
    @PostMapping("/auth/save")
    public R save(
            @RequestBody BorrowerVO borrowerVO,
            HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);

        return R.ok().message("信息提交成功");

    }


    /**
     * @description: 获取借款人认证状态
     * @author: ChenXW
     * @date: 2023/5/25 22:58
     */
    @ApiOperation("获取借款人认证状态")
    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(HttpServletRequest request){

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer status = borrowerService.getStatusByUserId(userId);
        return R.ok().data("borrowerStatus", status);
    }


}

