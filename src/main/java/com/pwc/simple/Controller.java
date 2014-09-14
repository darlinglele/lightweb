package com.pwc.simple;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Controller implements IController, Cloneable {
    public Map<String, Object> viewData = new HashMap<>();

    public byte[] execute(String action, Object[] parameters) {
        Method method;
        Class[] paramsType = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            paramsType[i] = parameters[0].getClass();
        }

        try {
            method = this.getClass().getMethod(action, paramsType);
        } catch (NoSuchMethodException e) {
            String message = String.format("Can't found the action named %s with %s parameters", action, parameters.length);
            return new ExceptionController(message, e).execute();
        }
        IView view;

        try {
            view = (IView) method.invoke(this, parameters);
        } catch (IllegalAccessException e) {
            return new ExceptionController("Illegal access exception", e).execute();
        } catch (InvocationTargetException e) {
            return new ExceptionController("Invocation target exception", e).execute();
        }
        return view.render();
    }
}
