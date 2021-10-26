package com.emily.emilyservice.utils.es;

import java.util.function.Function;

public class MapperEsBridge {

    public static <T> void execute(T t ,Function<T ,Object> function){
        System.out.println("========execute start============");
        System.out.println("类名："+function.getClass().getName());
        System.out.println("方法名："+function.getClass().getMethods().toString());
        function.apply(t);
        System.out.println("========execute end============");
    }
}
