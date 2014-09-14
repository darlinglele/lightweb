package com.pwc.simple;

public class HomeController extends Controller {
    public IView index() {
        viewData.put("greeting", "Hello world!");
        return new View(this, "./view/home/index.html");
    }
}
