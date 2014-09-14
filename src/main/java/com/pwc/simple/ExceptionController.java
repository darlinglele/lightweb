package com.pwc.simple;

public class ExceptionController extends Controller implements IController {
    public ExceptionController(String message, Throwable e) {
        this.viewData.put("title", message);
        this.viewData.put("content", HelperClass.getStackTrace(e));
    }
    @Override
    public byte[] execute(String action, Object[] parameters) {
        return (this.viewData.get("title")+"............." + viewData.get("content")).getBytes();
    }

    public byte[] execute() {
        return execute(null, null);
    }
}
