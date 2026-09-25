import java.util.*;

class Solution {
    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;
        return new ArrayList<>(parseExpression());
    }

    private Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (index < s.length() && s.charAt(index) == ',') {
            index++;
            result.addAll(parseTerm());
        }

        return result;
    }

    private Set<String> parseTerm() {
        Set<String> result = new TreeSet<>();
        result.add("");

        while (index < s.length() && s.charAt(index) != '}' && s.charAt(index) != ',') {
            Set<String> current = parseFactor();
            Set<String> next = new TreeSet<>();

            for (String a : result) {
                for (String b : current) {
                    next.add(a + b);
                }
            }

            result = next;
        }

        return result;
    }

    private Set<String> parseFactor() {
        if (s.charAt(index) == '{') {
            index++;
            Set<String> result = parseExpression();
            index++;
            return result;
        }

        Set<String> result = new TreeSet<>();
        result.add(String.valueOf(s.charAt(index)));
        index++;

        return result;
    }
}