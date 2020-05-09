package com.example.enjoy_english.aop;

import com.example.enjoy_english.model.Feedback;
import com.example.enjoy_english.model.Menu;
import com.example.enjoy_english.model.QA;
import com.example.enjoy_english.model.User;
import com.example.enjoy_english.repository.FeedbackRepository;
import com.example.enjoy_english.repository.MenuRepository;
import com.example.enjoy_english.repository.QARepository;
import com.example.enjoy_english.repository.UserRepository;
import com.example.enjoy_english.tools.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Aspect
@Component
public class OperationLog {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QARepository qaRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    private Object resultObj = null;
    private Operation operation = new Operation();

    /**
     * 记录系统插入操作的日志
     */
    @Pointcut("execution(public * com.example.enjoy_english.service.*.add*(..))" +
            "&& !execution(public * com.example.enjoy_english.service.LogService.*(..))")
    public void insertOperation(){}

    @Around("insertOperation()")
    public Object aroundInsertOperation(ProceedingJoinPoint point){
        init(point, OperationType.INSERT);

        try {
            resultObj = point.proceed();

            Result result = (Result) resultObj;
            if (result.getStatus() > 0){
                operation.setNewvalue( result.getData().toString() );
                operationRepository.save(operation);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return resultObj;
    }

    /**
     * 记录系统更新操作的日志
     */
    @Pointcut("execution(public * com.example.enjoy_english.service.*.update*(..))" +
            "&& !execution(public * com.example.enjoy_english.service.LogService.*(..))")
    public void updateOperation(){}

    @Around("updateOperation()")
    public Object arroundUpdateOperation(ProceedingJoinPoint point){
        init(point, OperationType.UPDATE);

        try {

            Object[] args = point.getArgs();
            for (Object arg : args){
                if ("User".equals( operation.getObject() )){
                    if (arg instanceof User){
                        User oldValue = userRepository.findByAccno( ((User) arg).getAccno() );
                        operation.setOldvalue( oldValue.toString() );
                    }
                }else if ("QA".equals( operation.getObject() )){
                    if (arg instanceof QA){
                        QA oldValue = qaRepository.findByItemno( ((QA) arg).getItemno() );
                        operation.setOldvalue( oldValue.toString() );
                    }
                }else if ("Menu".equals( operation.getObject() )){
                    if (arg instanceof Menu){
                        Menu oldValue = menuRepository.findByGroupno( ((Menu) arg).getGroupno() );
                        operation.setOldvalue(oldValue.toString());
                    }
                }else if ("Feedback".equals( operation.getObject() )){
                    if (arg instanceof Feedback){
                        Feedback oldValue = feedbackRepository.findById( ((Feedback) arg).getFeedbackUnionKey() ).orElse(null);
                        operation.setOldvalue(oldValue.toString());
                    }
                }
            }

            resultObj = point.proceed();

            Result result = (Result) resultObj;
            if (result.getStatus() > 0){
                operation.setNewvalue( result.getData().toString() );
                operationRepository.save(operation);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return resultObj;
    }

    /**
     * 记录系统删除操作的日志
     */
    @Pointcut("execution(public * com.example.enjoy_english.service.*.delete*(..))" +
            "&& !execution(public * com.example.enjoy_english.service.LogService.*(..))")
    public void deleteOperation(){}

    @Around(value = "deleteOperation()")
    public Object aroundDeleteOperation(ProceedingJoinPoint point){
        init(point, OperationType.DELETE);

        try {

            List<Object> objectList = new ArrayList<>();
            String objName = point.getTarget().getClass().getSimpleName().split("ServiceImpl")[0];
            Object[] args = point.getArgs();
            for (Object arg : args){
                List<String> ids = (List<String>) arg;
                for (String id : ids){
                    if ("User".equals(objName)){
                        objectList.add( userRepository.findByAccno(id) );
                    }else if ("QA".equals(objName)){
                        objectList.add(qaRepository.findByItemno(id));
                    }else if ("Menu".equals(objName)){
                        objectList.add(menuRepository.findByGroupno(id));
                    }
                }
            }
            operation.setOldvalue( objectList.toString() );

            resultObj = point.proceed();

            operationRepository.save(operation);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return resultObj;
    }

    private void init(ProceedingJoinPoint point, OperationType type){
        resultObj = null;
        operation = new Operation();
        operation.setType(type);
        operation.setDatetime(new Timestamp(new Date().getTime()));
        String operator = SecurityContextHolder.getContext().getAuthentication().getName();
        operation.setOperator(operator);
        String objName = point.getTarget().getClass().getSimpleName().split("ServiceImpl")[0];
        operation.setObject(objName);
    }

//    private List getBeforeObjectList(ProceedingJoinPoint point){
//        List<Object> objectList = new ArrayList<>();
//        String objName = point.getTarget().getClass().getSimpleName().split("ServiceImpl")[0];
//        Object[] args = point.getArgs();
//        for (Object arg : args){
//            List<String> ids = (List<String>) arg;
//            for (String id : ids){
//                if ("User".equals(objName)){
//                    objectList.add( userRepository.findByAccno(id) );
//                }else if ("QA".equals(objName)){
//                    objectList.add(qaRepository.findByItemno(id));
//                }else if ("Menu".equals(objName)){
//                    objectList.add(menuRepository.findByGroupno(id));
//                }
//            }
//        }
//        return objectList;
//    }

}
