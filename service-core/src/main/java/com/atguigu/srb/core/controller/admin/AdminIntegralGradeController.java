package com.atguigu.srb.core.controller.admin;


import com.atguigu.srb.core.mapper.IntegralGradeMapper;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author ChenXW
 * @since 2023-05-15
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    public IntegralGradeService integralGradeService;

    @GetMapping("/list")
    public List<IntegralGrade> listAll(){
        return integralGradeService.list();
    }

}

