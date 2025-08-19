package com.majestic.util;

/* * @author fordfrog **/
public class StringUtil {
    private StringUtil() {
    }

    public static String concat(String... strings) {
        final StringBuilder sbString = new StringBuilder();
        for (String string : strings) {
            sbString.append(string);
        }
        return sbString.toString();
    }

    public static StringBuilder startAppend(int sizeHint, String... strings) {
        final int length = getLength(strings);
        final StringBuilder sbString = new StringBuilder(sizeHint > length ? sizeHint : length);
        for (String string : strings) {
            sbString.append(string);
        }
        return sbString;
    }

    public static void append(StringBuilder sbString, String... strings) {
        sbString.ensureCapacity(sbString.length() + getLength(strings));
        for (String string : strings) {
            sbString.append(string);
        }
    }

    public static int getLength(Iterable<String> strings) {
        int length = 0;
        for (String string : strings) {
            length += (string == null) ? 4 : string.length();
        }
        return length;
    }

    public static int getLength(String[] strings) {
        int length = 0;
        for (String string : strings) {
            length += (string == null) ? 4 : string.length();
        }
        return length;
    }

    public static String getTraceString(StackTraceElement[] trace) {
        final StringBuilder sbString = new StringBuilder();
        for (StackTraceElement element : trace) {
            sbString.append(element.toString()).append(System.lineSeparator());
        }
        return sbString.toString();
    }
}