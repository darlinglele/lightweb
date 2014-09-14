
package main.java.com.pwc.simple;

import org.junit.Test;

public class RequestTest {
//    @Test
//    public void should_return_if_has_one_parameter() {
//        String url = "http://z.cn/product/id/109098";
//        Request request = Request.from(url);
//        assertThat(request.getParameters(), is(new String[]{"109098"}));
//    }
//
//    @Test
//    public void should_return_null_if_not_has_parameters() {
//        String url = "http://z.cn/product/id";
//        Request request = Request.from(url);
//        assertThat(request.getParameters(), is(new String[]{}));
//    }
//
//    @Test
//    public void should_return_all_parameters() {
//        String url = "http://z.cn/product/details?a=100&b=10&c=4343";
//        Request request = Request.from(url);
//        assertThat(request.getParameters(), is(new String[]{"100", "10", "4343"}));
//    }
//
//
//    @Test
//    public void should_return_action() {
//        String url = "http://z.cn/product/details?a=100&b=10&c=4343";
//        Request request = Request.from(url);
//        assertThat(request.getAction(), is("details"));
//    }
//
//    @Test
//    public void should_return_action_without_parameters() {
//        String url = "http://z.cn/product/details/";
//        Request request = Request.from(url);
//        assertThat(request.getAction(), is("details"));
//    }

    @Test
    public void should_return_controller() {
        String url = "http://localhost:8088/Lab/tag?id=6416&checked=true";
        String[] split = url.split("([\\?&])([^&])*=");
        for (String i : split) {
            System.out.println(i);
        }

        System.out.println(String.format("[0]", 1));
    }


}
