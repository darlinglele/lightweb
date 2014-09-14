package com.pwc.simple;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class View implements IView {
    private final String viewPath;
    private final Controller controller;

    public View(Controller controller, String viewPath) {
        this.controller = controller;
        this.viewPath = viewPath;
    }

    public static View of(Controller controller, String viewPath) {
        return new View(controller, viewPath);
    }

    public byte[] render() {
        Document contentDocument = getDocument(this.viewPath);

        Document templateDocument = null;
        if (hasTemplate(contentDocument)) {
            String templatePath = contentDocument.getElementsByTag("Simple:Master").first().attr("path");
            templateDocument = getDocument(templatePath);
            Elements elements = templateDocument.getElementsByTag("Simple:ContentPlaceHolder");
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element tempElement = iterator.next();
                String elementId = tempElement.attr("id");
                Elements contentElement = contentDocument.getElementsByAttributeValue("ContentPlaceHolderId", elementId);
                if (contentElement.size() == 0) {

                } else {
                    tempElement.append(contentElement.first().html());
                    List<Node> nodes = tempElement.childNodes();
                    Integer integer = tempElement.siblingIndex();

                    tempElement.parent().insertChildren(integer, nodes);
                    tempElement.remove();
                }
            }
        }

        contentDocument = templateDocument != null ? templateDocument : contentDocument;

        Elements body = contentDocument.getElementsByTag("head");
        if (controller.viewData.size() > 0) {
            Gson gson = new Gson();
            String json = gson.toJson(controller.viewData);
            body.first().append("<script>var ViewData= " + json + "</script>");
        }
        return contentDocument.outerHtml().getBytes(StandardCharsets.UTF_8);
    }

    private boolean hasTemplate(Document contentDocument) {
        return contentDocument.getElementsByTag("Simple:Master").size() > 0;
    }

    private Document getDocument(String viewPath1) {
        Document document = null;
        try {
            document = Jsoup.parse(new File(viewPath1), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
