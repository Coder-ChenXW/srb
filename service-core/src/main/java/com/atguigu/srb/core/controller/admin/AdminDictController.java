package com.atguigu.srb.core.controller.admin;


import com.alibaba.excel.EasyExcel;
import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author ChenXW
 * @since 2023-05-15
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
@CrossOrigin
public class AdminDictController {

    @Resource
    private DictService dictService;


    /**
     * @description: Excel批量导入数据字典
     * @author: ChenXW
     * @date: 2023/5/17 23:37
     */
    @ApiOperation("Excel批量导入数据字典")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel数据字典文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);

            return R.ok().message("数据字典批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }


    /**
     * @description: Excel数据的导出
     * @author: ChenXW
     * @date: 2023/5/18 8:34
     */
    @ApiOperation("Excel数据的导出")
    @GetMapping("/export")
    public void download(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("mydict", "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());

    }



    /**
     * @description: 根据上级节点id获取子节点数据列表
     * @author: ChenXW
     * @date: 2023/5/18 9:05
     */
    @ApiOperation("根据上级节点id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(
            @ApiParam(value = "上级节点id", required = true)
            @PathVariable Long parentId) {

        List<Dict> dictList = dictService.listByParentId(parentId);

        return R.ok().data("list", dictList);
    }

}

