package com.pwc.simple;

public class ResumeController extends Controller {
    public IView index() {
        return View.of(this, "./view/resume/index.html");
    }
}
