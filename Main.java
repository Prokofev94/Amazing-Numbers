package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> allProps = List.of("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY",
            "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD");

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!\n");
        getInstructions();
        request();
    }

    public static void request() {
        Scanner scanner = new Scanner(System.in);
        String[] input;
        do {
            System.out.print("Enter a request: ");
            input = scanner.nextLine().split(" ");
            if (isNatural(input)) {
                if (input.length == 1) {
                    getProperties(Long.parseLong(input[0]));
                } else if (input.length == 2) {
                    getProperties(Long.parseLong(input[0]), Long.parseLong(input[1]));
                } else {
                    List<String> props = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(input, 2, input.length)));
                    getProperties(Long.parseLong(input[0]), Long.parseLong(input[1]), props);
                }
            }
        } while (!"0".equals(input[0]));
        System.out.println("\nGoodbye!");
    }

    public static boolean isNatural(String[] input) {
        if (isNumeric(input[0]) && Long.parseLong(input[0]) >= 0) {
            if (Long.parseLong(input[0]) == 0) {
                return false;
            }
        } else {
            System.out.println("The first parameter should be a natural number or zero.");
            return false;
        }
        if (input.length > 1) {
            if (!(isNumeric(input[1]) && Long.parseLong(input[1]) > 0)) {
                System.out.println("The second parameter should be a natural number.");
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static void getProperties(long num) {
        System.out.println("Properties of " + num);
        System.out.println("even: " + isEven(num));
        System.out.println("odd: " + isOdd(num));
        System.out.println("buzz: " + isBuzz(num));
        System.out.println("duck: " + isDuck(num));
        System.out.println("palindromic: " + isPalindromic(num));
        System.out.println("gapful: " + isGapful(num));
        System.out.println("spy: " + isSpy(num));
        System.out.println("square: " + isSquare(num));
        System.out.println("sunny: " + isSunny(num));
        System.out.println("jumping: " + isJumping(num));
        System.out.println("sad: " + isSad(num));
        System.out.println("happy: " + isHappy(num));
    }

    public static void getProperties(long num, long range) {
        for (long i = num; i < num + range; i++) {
            System.out.print(i + " is ");
            if (isBuzz(i)) {
                System.out.print("buzz, ");
            }
            if (isDuck(i)) {
                System.out.print("duck, ");
            }
            if (isPalindromic(i)) {
                System.out.print("palindromic, ");
            }
            if (isGapful(i)) {
                System.out.print("gapful, ");
            }
            if (isSpy(i)) {
                System.out.print("spy, ");
            }
            if (isSquare(i)) {
                System.out.print("square, ");
            }
            if (isSunny(i)) {
                System.out.print("sunny, ");
            }
            if (isJumping(i)) {
                System.out.print("jumping, ");
            }
            if (isSad(i)) {
                System.out.print("sad, ");
            }
            if (isHappy(i)) {
                System.out.print("happy, ");
            }
            if (isEven(i)) {
                System.out.println("even");
            } else {
                System.out.println("odd");
            }
        }
    }

    public static void getProperties(long num, long range, List<String> props) {
        if (props.size() > 1 && checkMutualExclusions(props)) {
            return;
        }
        if (isWrongProperties(props)) {
            return;
        }
        boolean hasProperties;
        for (long i = num; range > 0; i++) {
            hasProperties = true;
            for (String prop : props) {
                if (prop.charAt(0) == '-') {
                    hasProperties &= !checkProperty(i, prop.substring(1));
                } else {
                    hasProperties &= checkProperty(i, prop);
                }
            }
            if (hasProperties) {
                getProperties(i, 1);
                range--;
            }
        }
    }

    public static boolean checkProperty(long num, String property) {
        switch (property.toLowerCase()) {
            case "even":
                return isEven(num);
            case "odd":
                return isOdd(num);
            case "buzz":
                return isBuzz(num);
            case "duck":
                return isDuck(num);
            case "palindromic":
                return isPalindromic(num);
            case "gapful":
                return isGapful(num);
            case "spy":
                return isSpy(num);
            case "square":
                return isSquare(num);
            case "sunny":
                return isSunny(num);
            case "jumping":
                return isJumping(num);
            case "sad":
                return isSad(num);
            case "happy":
                return isHappy(num);
            default:
                break;
        }
        return false;
    }

    public static boolean isWrongProperties(List<String> props) {
        List<String> wrongProps = new ArrayList<>();
        for (String prop : props) {
            if (prop.charAt(0) == '-') {
                prop = prop.substring(1);
            }
            if (!(allProps.contains(prop.toUpperCase()))) {
                wrongProps.add(prop.toUpperCase());
            }
        }
        if (wrongProps.size() > 1) {
            System.out.printf("The properties %s are wrong.\n", wrongProps);
            System.out.println("Available properties: " + allProps);
            return true;
        } else if (wrongProps.size() == 1) {
            System.out.printf("The property %s is wrong.\n", wrongProps);
            System.out.println("Available properties: " + allProps);
            return true;
        }
        return false;
    }

    public static boolean checkMutualExclusions(List<String> props) {
        List<String> exclusives = new ArrayList<>();
        if (props.contains("duck") && props.contains("spy")) {
            exclusives.add("duck");
            exclusives.add("spy");
        }
        if (props.contains("even") && props.contains("odd")) {
            exclusives.add("even");
            exclusives.add("odd");
        }
        if (props.contains("sunny") && props.contains("square")) {
            exclusives.add("sunny");
            exclusives.add("square");
        }
        if (props.contains("happy") && props.contains("sad")) {
            exclusives.add("happy");
            exclusives.add("sad");
        }
        if (props.contains("-even") && props.contains("-odd")) {
            exclusives.add("-even");
            exclusives.add("-odd");
        }
        if (props.contains("-sunny") && props.contains("-square")) {
            exclusives.add("-sunny");
            exclusives.add("-square");
        }
        if (props.contains("-happy") && props.contains("-sad")) {
            exclusives.add("-happy");
            exclusives.add("-sad");
        }
        for (String prop : allProps) {
            if (props.contains(prop.toLowerCase()) && props.contains(String.format("-%s", prop.toLowerCase()))) {
                exclusives.add(prop);
                exclusives.add(String.format("-%s", prop));
            }
        }
        if (exclusives.size() > 0) {
            System.out.println("The request contains mutually exclusive properties: " + exclusives);
            System.out.println("There are no numbers with these properties.");
            return true;
        }
        return false;
    }

    public static boolean isEven(long num) {
        return num % 2 == 0;
    }

    public static boolean isOdd(long num) {
        return num % 2 != 0;
    }

    public static boolean isBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    public static boolean isDuck(long n) {
        for (long i = n; i > 0; i /= 10) {
            if (i % 10 == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPalindromic(long num) {
        String str = String.valueOf(num);
        StringBuilder reverse = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reverse.append(str.charAt(i));
        }
        return str.equals(reverse.toString());
    }

    public static boolean isGapful(long num) {
        if (num > 99) {
            String s = String.valueOf(num);
            long m = Long.parseLong(String.valueOf(s.charAt(0)) + s.charAt(s.length() - 1));
            return num % m == 0;
        }
        return false;
    }
    
    public static boolean isSpy(long num) {
        long sum = 0;
        long product = 1;
        long digit;
        String strNum = String.valueOf(num);
        for (int i = 0; i < strNum.length(); i++) {
            digit = Character.getNumericValue(strNum.charAt(i));
            sum += digit;
            product *= digit;
        }
        return sum == product;
    }

    public static boolean isSquare(long num) {
        double square = Math.sqrt(num);
        return square == (long) square;
    }

    public static boolean isSunny(long num) {
        return isSquare(num + 1);
    }

    public static boolean isJumping(long num) {
        String strNum = String.valueOf(num);
        int a;
        int b;
        for (int i = 1; i < strNum.length(); i++) {
            a = Character.getNumericValue(strNum.charAt(i - 1));
            b = Character.getNumericValue(strNum.charAt(i));
            if (Math.abs(a - b) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSad(long num) {
        return !isHappy(num);
    }

    public static boolean isHappy(long num) {
        List<Long> nums = new ArrayList<>();
        String strNum;
        long n = num;
        do {
            strNum = String.valueOf(n);
            nums.add(n);
            n = 0;
            for (int i = 0; i < strNum.length(); i++) {
                n += (long) Character.getNumericValue(strNum.charAt(i)) * Character.getNumericValue(strNum.charAt(i));
            }
        } while (n != 1 && !nums.contains(n));
        return n == 1;
    }

    public static void getInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }
}
