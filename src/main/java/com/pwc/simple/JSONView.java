package main.java.com.pwc.simple;

import com.google.gson.Gson;

public class JSONView implements com.pwc.simple.IView {

    private final Object data;

    public JSONView(Object data) {
        this.data = data;
    }

    @Override
    public byte[] render() {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return json.getBytes();
    }
}
