package com.example.service;

import org.flowable.engine.runtime.ProcessInstance;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/30 10:30
 * @description 流程服务类
 */
public interface FlowService {

    /**
     * 部署工作流
     */
    Map<String, Object> createFlow(String filePath);

    /**
     * 启动工作流
     */
    ProcessInstance strartFlow(String processKey, Map<String, Object> paras);

    boolean isFinished(String processInstanceId);

    void genProcessDiagram(HttpServletResponse httpServletResponse, String processId);

    default void out(String out) {
        System.out.println(out);
    }

    boolean completeTask(String taskId, Map<String, Object> flowParams);
}