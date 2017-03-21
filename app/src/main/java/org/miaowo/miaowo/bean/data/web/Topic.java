package org.miaowo.miaowo.bean.data.web;

public class Topic {
    /**
     * value : 猜猜我是谁
     * score : 2
     * color :
     * bgColor :
     */

    private String value;
    private int score;
    private String color;
    private String bgColor;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
