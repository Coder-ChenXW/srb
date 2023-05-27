package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.bo.TransFlowBO;
import com.atguigu.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author ChenXW
 * @since 2023-05-15
 */
public interface TransFlowService extends IService<TransFlow> {

    public void saveTransFlow(TransFlowBO transFlowBO);

    boolean isSaveTransFlow(String agentBillNo);
}
