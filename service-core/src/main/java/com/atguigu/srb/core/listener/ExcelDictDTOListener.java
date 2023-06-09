package com.atguigu.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {



    private DictMapper dictMapper;


    // 数据列表
    private List<ExcelDictDTO> list = new ArrayList<ExcelDictDTO>();

    // 每隔5条存储数据库，实际使用中可以3000条，然后清理list，方便内存回收
    private static final int BATCH_COUNT = 5;


    public ExcelDictDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(ExcelDictDTO data, AnalysisContext context) {

        log.info("解析到一条记录: {}", data);
        list.add(data);

        if (list.size() >= BATCH_COUNT) {
            // 调用mapper层的save方法
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 当最后剩余的数据记录数不足BATCH_COUNT时，最后的数据也要存储到数据库中
        saveData();
        log.info("所有数据解析完成！");
    }


    private void saveData() {
        log.info("{}条数据，开始存储数据库.....", list.size());
        // 调用mapper层的save方法
        dictMapper.insertBatch(list);

        log.info("{} 条数据存储数据库成功！", list.size());
    }

}
