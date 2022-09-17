package com.sarangchurch.follower.docs.utils;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    enum DocUrl {
        GENDER("gender", "성별"),
        ROLE("role", "권한"),
        ;

        private final String pageId;
        private final String text;

        DocUrl(String pageId, String text) {
            this.pageId = pageId;
            this.text = text;
        }

        public String getPageId() {
            return pageId;
        }

        public String getText() {
            return text;
        }
    }
}