package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validator {

    // Kiểm tra String không rỗng
    public static boolean isNotBlank(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Kiểm tra int
    public static boolean isInteger(String input) {
        if (!isNotBlank(input))
            return false;
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra double
    public static boolean isDouble(String input) {
        if (!isNotBlank(input)) return false;
        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra boolean (true/false hoặc 0/1)
    public static boolean isBoolean(String input) {
        if (!isNotBlank(input)) return false;
        String val = input.trim().toLowerCase();
        return val.equals("true") || val.equals("false") || val.equals("1") || val.equals("0");
    }

    // Kiểm tra ngày theo format yyyy-MM-dd
    public static boolean isDate(String input) {
        if (!isNotBlank(input)) return false;
        try {
            LocalDate.parse(input.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Chuyển string -> boolean
    public static boolean parseBoolean(String input) {
        return input.trim().equalsIgnoreCase("true") || input.trim().equals("1");
    }
}
