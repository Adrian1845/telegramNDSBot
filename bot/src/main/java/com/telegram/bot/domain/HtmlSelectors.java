package com.telegram.bot.domain;

public final class HtmlSelectors {

    private HtmlSelectors() {} // prevent instantiation


    // Table parsing
    public static final String TABLE = "table#";
    public static final String TABLE_ID = "list";
    public static final String ROWS = "tbody tr";
    public static final String LINK_CELL = "td.link a[href]";
    public static final String SIZE_CELL = "td.size";
    public static final String TITLE_ATTR = "title";
    public static final String HREF_ATTR = "href";

    // Telegram HTML formatting
    public static final String BULLET = "• ";
    public static final String LINK_OPEN = "<a href=\"";
    public static final String LINK_CLOSE = "\">";
    public static final String LINK_END = "</a>";
    public static final String SIZE_PREFIX = "   Size: ";
    public static final String NEWLINE = "\n";
}

