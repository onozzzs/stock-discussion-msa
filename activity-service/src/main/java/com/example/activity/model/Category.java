package com.example.activity.model;

public enum Category{
    POST {
        public String makeContent(String username, String targetName, String targetContent) {
            return username + " uploads new post";
        }
    },
    COMMENT {
        public String makeContent(String username, String targetName, String targetContent) {
            return username + " comments on " + targetName + " " + targetContent;
        }
    },
    LIKE {
        public String makeContent(String username, String targetName, String targetContent) {
            return username + " likes " + targetName;
        }
    },
    FOLLOW {
        public String makeContent(String username, String targetName, String targetContent) {
            return username + " follows " + targetName;
        }
    };

    public abstract String makeContent(String username, String targetName, String targetContent);
}
