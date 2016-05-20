package net.paoding.rose.exception.util;

import java.util.List;

/**
 * Daniel Abel
 * weili209072
 * 2015/12/24.
 */
public class BaseMail {
    private String title;
    private String content;
    private List<String> tos;

    public BaseMail() {

    }

    public BaseMail(String title, String content, List<String> tos) {
        this.title = title;
        this.content = content;

        this.tos = tos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<String> getTos() {
        return tos;
    }

    public void setTos(List<String> tos) {
        this.tos = tos;
    }
}
