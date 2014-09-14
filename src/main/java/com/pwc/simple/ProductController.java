package com.pwc.simple;

public class ProductController extends Controller {
    public IView list(String id) {
        viewData.put("user", new User("cn412768","linzhixiong"));
        String viewPath = "./view/product/list.html";
        return View.of(this, viewPath);
    }

    public IView details() {
        String viewPath = "./view/product/details.html";
        return View.of(this, viewPath);
    }
}

