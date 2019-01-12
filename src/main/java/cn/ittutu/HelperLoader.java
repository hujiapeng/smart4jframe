package cn.ittutu;

import cn.ittutu.helper.BeanHelper;
import cn.ittutu.helper.ClassHelper;
import cn.ittutu.helper.ControllerHelper;
import cn.ittutu.helper.IocHelper;
import cn.ittutu.util.ClassUtil;

public final class HelperLoader {

    public static void init(){
        Class<?>[] classList={ClassHelper.class,BeanHelper.class,IocHelper.class,ControllerHelper.class};
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }

}
