package com.example.controller;

import com.example.model.Result;
import com.example.service.FlowService;
import com.example.service.UserService;
import com.example.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.spring.security.UserDto;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.MapUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lilei
 * @description
 * @time 2019/9/25 15:01
 */
@Slf4j
@RestController
@RequestMapping("flow")
public class FlowController {

    @Autowired
    private FlowService flowService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    /**
     * @author 李磊
     * @time 2019/10/30 15:39
     * @description 创建流程
     */
    @GetMapping("create")
    public Map<String, Object> createFlow() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        String flowPath = "D:\\test\\测试BPMN模型.bpmn20.xml";

        Map<String, Object> createRes = flowService.createFlow(flowPath);

        if (null == createRes) {
            map.put("msg", "创建流程失败");
            map.put("res", "0");
            map.put("data", data);
            return map;
        }
        List<Process> processes = (List<Process>) createRes.get("processes");

        ArrayList<String> ids = new ArrayList<>();
        for (Process process : processes) {
            ids.add(process.getId());
        }
        data.put("processKeys", ids);
        data.put("deployId", ((Deployment) createRes.get("deployment")).getId());
        map.put("data", data);
        map.put("msg", "创建流程成功");
        map.put("res", "1");
        return map;
    }

    /**
     * @param paras
     * @author 李磊
     * @time 2019/10/30 15:39
     * @description 启动流程
     */
    @GetMapping("start")
    public Map<String, Object> startFlow(@RequestBody @RequestParam(required = false) Map<String, String> paras) {
        Map<String, Object> res = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        if (MapUtils.isEmpty(paras)) {
            res.put("msg", "启动流程失败");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        String processKey = paras.get("processKey");
        if (StringUtil.isEmpty(processKey)) {
            res.put("msg", "启动流程失败");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        Map<String, Object> flowParas = new HashMap<>();
        flowParas.putAll(paras);
        ProcessInstance processInstance = flowService.strartFlow(processKey, flowParas);
        if (null == processInstance) {
            res.put("msg", "启动流程失败");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }
        data.put("processId", processInstance.getId());
        res.put("msg", "启动流程成功");
        res.put("res", "1");
        res.put("data", data);
        return res;
    }

    /**
     * @param processId 流程id
     * @author 李磊
     * @time 2019/10/30 15:40
     * @description 生成流程图
     */
    @GetMapping("processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) {

        flowService.genProcessDiagram(httpServletResponse, processId);
    }

    /**
     * @param params
     * @author 李磊
     * @time 2019/10/30 15:41
     * @description 结束流程
     */
    @GetMapping("complete")
    public Map<String, Object> completeTask(@RequestBody @RequestParam(required = false) Map<String, String> params) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        if (MapUtils.isEmpty(params)) {
            map.put("msg", "请输入任务参数");
            map.put("res", "0");
            map.put("data", data);
            return map;
        }

        String taskId = params.get("taskId");
        if (StringUtil.isEmpty(taskId)) {
            map.put("msg", "请输入任务ID");
            map.put("res", "0");
            map.put("data", data);
            return map;
        }

        Map<String, Object> flowParams = new HashMap<>();
        flowParams.putAll(params);
        boolean bok = flowService.completeTask(taskId, flowParams);

        data.put("taskId", taskId);
        if (bok) {
            map.put("msg", "流程完结成功");
            map.put("res", "1");
        } else {
            map.put("msg", "流程完结失败");
            map.put("res", "0");
        }

        map.put("data", data);
        return map;
    }

    /**
     * @author
     * @time 2019/10/30 15:48
     * @description 同意任务
     */
    @GetMapping("accept")
    public Map<String, Object> acceptTask(@RequestBody @RequestParam(required = false) Map<String, String> flowParams) {
        Map<String, Object> res = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        if (MapUtils.isEmpty(flowParams)) {
            res.put("msg", "请输入任务参数");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        String taskId = flowParams.get("taskId");
        if (StringUtil.isEmpty(taskId)) {
            res.put("msg", "请输入任务ID");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        Map<String, Object> flowParas = new HashMap<>();
        flowParas.putAll(flowParams);
        flowParas.put("outcome", "通过");
        boolean bok = flowService.completeTask(taskId, flowParas);

        if (bok) {
            data.put("taskId", taskId);
            res.put("msg", "通过任务成功");
            res.put("res", "1");
        } else {
            data.put("taskId", taskId);
            res.put("msg", "通过任务失败");
            res.put("res", "0");
        }

        res.put("data", data);
        return res;
    }

    /**
     * @author
     * @time 2019/10/30 15:48
     * @description 拒绝任务
     */
    @GetMapping("reject")
    public Map<String, Object> rejectTask(@RequestBody @RequestParam(required = false) Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        if (MapUtils.isEmpty(params)) {
            res.put("msg", "请输入任务参数");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        String taskId = params.get("taskId");
        if (StringUtil.isEmpty(taskId)) {
            res.put("msg", "请输入任务ID");
            res.put("res", "0");
            res.put("data", data);
            return res;
        }

        Map<String, Object> flowParas = new HashMap<>();
        flowParas.putAll(params);
        flowParas.put("outcome", "拒绝");
        boolean bok = flowService.completeTask(taskId, flowParas);

        if (bok) {
            data.put("taskId", taskId);
            res.put("msg", "拒绝任务成功");
            res.put("res", "1");
        } else {
            data.put("taskId", taskId);
            res.put("msg", "拒绝任务失败");
            res.put("res", "0");
        }

        res.put("data", data);
        return res;
    }
////////////////////

    @GetMapping("rest/account")
    public UserRepresentation selectAccount(Integer id) {
        return new UserRepresentation() {{
            setId("1234");
            setFirstName("li");
            setFullName("lilei");
            setPrivileges(new ArrayList<String>() {{
                add("access-idm");
                add("access-task");
                add("access-modeler");
                add("access-admin");
            }});
        }};
    }

    @GetMapping("rest/editor-users")
    public Map<String, Object> selectUser(@RequestParam(required = false) UserDto userDto) {
        if (userDto == null) return null;

        List<UserDto> userList = userService.selectFlowUserByUserName(userDto);
        Map<String, Object> map = new HashMap<>();
        map.put("size", userList.size());
        map.put("total", userList.size());
        map.put("start", 0);
        map.put("data", userList);
        return map;
    }

////////////////////
    /**
     * 系统流程部署列表
     */
    @GetMapping("list")
    public Result flowableList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        return Result.success(new HashMap<String, Object>() {{
            put("count", list.size());
            put("data", list);
        }});
    }

    /**
     * 删除系统部署的流程
     */
    @GetMapping("remove")
    public String remove(String id) {
        repositoryService.deleteDeployment(id);
        return "删除成功";
    }

    /**
     * 获取流程实例
     */
    @GetMapping("instance/list")
    public Result instanceList(String userId) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        if (!StringUtil.isEmpty(userId)) {
            query.startedBy(userId);
        }

        List<ProcessInstance> list = query.list();
        return Result.success(new HashMap<String, Object>() {{
            put("count", list.size());
            put("data", list);
        }});
    }

    /**
     * 删除流程实例
     */
    @GetMapping("instance/remove")
    public String removeInstance(String id) {
        runtimeService.deleteProcessInstance(id, "删除测试");
        return "删除成功";
    }

    /**
     * 获取任务列表
     */
    @GetMapping("task/list")
    public Result taskList(String userId, String group) {
        TaskQuery query = taskService.createTaskQuery();
        if (!StringUtil.isEmpty(group)) {
            query.taskCandidateGroup(group);
        } else if (!StringUtil.isEmpty(userId)) {
            query.taskCandidateOrAssigned(userId);
        }

        List<Task> tasks = query.orderByTaskCreateTime().desc().list();
        List<Map> list = new ArrayList<>();
        for (Task task : tasks) {
            Map map = new HashMap() {{
                put("task", task);
            }};
            System.out.println(map.toString());
            list.add(map);
        }
        return Result.success(new HashMap<String, Object>() {{
            put("count", list.size());
            put("data", list);
        }});
    }

    /**
     * 删除任务
     */
    @GetMapping("task/remove")
    public String removeTask(String id) {
        taskService.deleteTask(id);
        return "删除成功";
    }

    /**
     * 获取历史任务列表
     */
    @GetMapping("historic/list")
    public Result historicTaskList(String userId) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        if (!StringUtil.isEmpty(userId)) {
            query.involvedUser(userId);
        }
        query.finished();
        List<HistoricProcessInstance> list = query.orderByProcessInstanceEndTime().desc().list();

        return Result.success(new HashMap<String, Object>() {{
            put("count", list.size());
            put("data", list);
        }});
    }

    /**
     * 获取历史任务列表
     */
    @GetMapping("historic/my/list")
    public Result historicMyTaskList(String userId) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        if (!StringUtil.isEmpty(userId)) {
            query.involvedUser(userId);
        }
        query.finished();
        List<HistoricProcessInstance> list = query.orderByProcessInstanceEndTime().desc().list();

        return Result.success(new HashMap<String, Object>() {{
            put("count", list.size());
            put("data", list);
        }});
    }
}