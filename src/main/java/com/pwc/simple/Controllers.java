package com.pwc.simple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Controllers {
    public static final String HOME = "Home";
    public static final String INDEX = "index";
    private static HashMap<String, Controller> bufferedControllers = new HashMap<>();

    public static Controller of(Request request) {
        String controllerName = request.getController();
        String className = "com.pwc.simple." + capitaliseFirstLetter(controllerName) + "Controller";
        return loadControllerByName(className);
    }

    public static String capitaliseFirstLetter(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static Controller loadControllerByName(String name) {
        if (bufferedControllers.containsKey(name)) {
            return bufferedControllers.get(name);
        } else {
            Class<Controller> clazz;
            try {
                clazz = (Class<Controller>) Class.<Controller>forName(name);
            } catch (ClassNotFoundException e) {
                return new ExceptionController("Error occurred while loading controller!", e);
            }
            Constructor<Controller> co;
            try {
                co = clazz.getConstructor(null);
            } catch (NoSuchMethodException e) {
                return new ExceptionController("Error occurred while loading controller!", e);
            }
            Controller controller;
            try {
                controller = co.newInstance(null);
            } catch (InstantiationException e) {
                return new ExceptionController("Error occurred while loading controller!", e);
            } catch (IllegalAccessException e) {
                return new ExceptionController("Error occurred while loading controller!", e);
            } catch (InvocationTargetException e) {
                return new ExceptionController("Error occurred while loading controller!", e);
            }
            bufferedControllers.put(name, controller);
            return controller;
        }
    }
}
