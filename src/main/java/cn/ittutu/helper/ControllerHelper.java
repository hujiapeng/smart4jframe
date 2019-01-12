package cn.ittutu.helper;

import cn.ittutu.annotation.Action;
import cn.ittutu.bean.Handler;
import cn.ittutu.bean.Request;
import cn.ittutu.util.CollectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    //存放请求与处理器的映射关系
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action=method.getAnnotation(Action.class);
                            String mapping=action.value();
                            //验证URL映射规则
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array=mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array)&&array.length==2){
                                    //获取请求方法与请求路径，如Get:/index
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(controllerClass,method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod,String requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
