package com.kuretru.android.singlenet.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    private final String NULL_STRING = null;
    private final String EMPTY_STRING = "";
    private final String BLANK_STRING = "   ";
    private final String TEXT = "abcdef";

    private final List<String> LIST = new ArrayList<>();
    private final String LIST_STRING = "A,B,C";
    private final String SPLITTER = ",";

    private final String JSON = "{\"code\":100,\"data\":\"2020-10-09 09:12:35\",\"message\":\"success\"}";

    public StringUtilsTest() {
        super();
        LIST.add("A");
        LIST.add("B");
        LIST.add("C");
    }

    @Test
    public void testIsNullOrEmpty() {
        assertTrue(StringUtils.isNullOrEmpty(NULL_STRING));
        assertTrue(StringUtils.isNullOrEmpty(EMPTY_STRING));
        assertFalse(StringUtils.isNullOrEmpty(BLANK_STRING));
        assertFalse(StringUtils.isNullOrEmpty(TEXT));
    }

    @Test
    public void testIsNullOrBlank() {
        assertTrue(StringUtils.isNullOrBlank(NULL_STRING));
        assertTrue(StringUtils.isNullOrBlank(EMPTY_STRING));
        assertTrue(StringUtils.isNullOrBlank(BLANK_STRING));
        assertFalse(StringUtils.isNullOrBlank(TEXT));
    }

    @Test
    public void testStringToList() {
        assertNull(StringUtils.stringToList(null, SPLITTER));
        assertEquals(LIST, StringUtils.stringToList(LIST_STRING, SPLITTER));
    }

    @Test
    public void testJsonToMap() {
        Map<String, Object> data = StringUtils.jsonToMap(JSON);
        assertEquals(100, data.get("code"));
        assertEquals("success", data.get("message"));
        assertEquals("2020-10-09 09:12:35", data.get("data"));
    }

    @Test
    public void testMapToJson() {
        Map<String, Object> data = new HashMap<>();
        data.put("code", 100);
        data.put("message", "success");
        data.put("data", "2020-10-09 09:12:35");
        String json = StringUtils.mapToJson(data);
        assertEquals(JSON, json);
    }

}
