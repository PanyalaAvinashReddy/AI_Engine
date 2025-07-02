package com.example.aichatbot.model;

public class AnswerBlock {
    private String type;
    private String value;
    private String link;
    private String image;

    public AnswerBlock() {}

    public AnswerBlock(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public AnswerBlock(String type, String value, String link, String image) {
        this.type = type;
        this.value = value;
        this.link = link;
        this.image = image;
    }

    public String getType() { return type; }
    public String getValue() { return value; }
    public String getLink() { return link; }
    public String getImage() { return image; }

    public void setType(String type) { this.type = type; }
    public void setValue(String value) { this.value = value; }
    public void setLink(String link) { this.link = link; }
    public void setImage(String image) { this.image = image; }
}
