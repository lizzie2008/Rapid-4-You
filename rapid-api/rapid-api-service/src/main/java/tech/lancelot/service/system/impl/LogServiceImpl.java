package tech.lancelot.service.system.impl;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lancelot.domain.system.Log;
import tech.lancelot.dto.system.LogQueryCriteria;
import tech.lancelot.mapstruct.sys.LogSmallMapper;
import tech.lancelot.repository.system.LogRepository;
import tech.lancelot.service.system.LogService;
import tech.lancelot.utils.PageUtil;
import tech.lancelot.utils.QueryHelp;
import tech.lancelot.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author lancelot
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {


    private final LogRepository logRepository;
    //    private final LogErrorMapper logErrorMapper;
    private final LogSmallMapper logSmallMapper;

    @Override
    public Object queryAll(LogQueryCriteria criteria, Pageable pageable) {
//        Page<Log> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)),pageable);
//        String status = "ERROR";
//        if (status.equals(criteria.getLogType())) {
//            return PageUtil.toPage(page.map(logErrorMapper::toDto));
//        }
//        return page;
        return null;
    }

    @Override
    public List<Log> queryAll(LogQueryCriteria criteria) {
//        return logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)));
        return null;
    }

    @Override
    public Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable) {
        Page<Log> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteriaQuery, cb, criteria)), pageable);
        return PageUtil.toPage(page.map(logSmallMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        tech.lancelot.annotations.Log aopLog = method.getAnnotation(tech.lancelot.annotations.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                username = new JSONObject(argValues.get(0)).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logRepository.save(log);
    }

    @Override
    public Object findByErrDetail(Long id) {
//        Log log = logRepository.findById(id).orElseGet(Log::new);
//        ValidationUtil.isNull( log.getId(),"Log","id", id);
//        byte[] details = log.getExceptionDetail();
//        return Dict.create().set("exception",new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
        return null;
    }

    @Override
    public void download(List<Log> logs, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (Log log : logs) {
//            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("用户名", log.getUsername());
//            map.put("IP", log.getRequestIp());
//            map.put("IP来源", log.getAddress());
//            map.put("描述", log.getDescription());
//            map.put("浏览器", log.getBrowser());
//            map.put("请求耗时/毫秒", log.getTime());
//            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
//            map.put("创建日期", log.getCreateTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logRepository.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logRepository.deleteByLogType("INFO");
    }
}

