package com.example.mvplibrary.model;

import java.util.HashMap;

public class ModelFactory {
    //存储model类的集合
    private static HashMap<String, BaseModel> mMap = new HashMap<>();


     //获取model
     //查询Map中是否存在model实例,不存在时动态创建
    public static <M extends BaseModel> M createModel(Class<M> mClass) {
        if (mMap.get(mClass) != null) {
            return (M) mMap.get(mClass);
        }

        M mModel = null;
        try {
            //通过反射创建对象，你的子类必须要有无参构造方法
            mModel = mClass.newInstance();
            if (mModel != null) {
                mMap.put(mClass.getName(), mModel);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return mModel;
    }
}
