package by.interoct.generator.util;

import com.google.common.base.CaseFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LabelFormatter {
    SIMPLE_TEXT {
        @Override
        public String toCamelCaseVariable(String in) {
            if (in.isEmpty()) return in;
            String name = toCamelCaseName(in);
            String substring = name.substring(0, 1);
            return name.replaceFirst(substring, substring.toLowerCase());
        }

        @Override
        public String toCamelCaseName(String in) {
            if (in.isEmpty()) return in;
            String result = EMPTY_STRING;
            Matcher matcher = TEXT.matcher(in);
            while (matcher.find()) {
                result += matcher.group();
            }
            result = result.toUpperCase().replaceAll(REGEX, REPLACEMENT);
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, result);
        }
    }, CAMEL_CASE_VARIABLE {
        @Override
        public String toCamelCaseVariable(String in) {
            return in;
        }

        @Override
        public String toCamelCaseName(String in) {
            String substring = in.substring(0, 1);
            return in.replaceFirst(substring, substring.toUpperCase());
        }
    }, CAMEL_CASE_NAME {
        @Override
        public String toCamelCaseVariable(String in) {
            String substring = in.substring(0, 1);
            return in.replaceFirst(substring, substring.toLowerCase());
        }

        @Override
        public String toCamelCaseName(String in) {
            return in;
        }
    };
    private static final Pattern TEXT = Pattern.compile("\\s*\\w+\\s*");
    private static final String EMPTY_STRING = "";
    private static final String REGEX = " ";
    private static final String REPLACEMENT = "_";

    public abstract String toCamelCaseVariable(String in);

    public abstract String toCamelCaseName(String in);

}
