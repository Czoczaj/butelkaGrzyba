package Task;

import java.util.HashMap;
import java.util.Scanner;

public class RomanNumbers {

    private HashMap<String, Integer> romanMap = new HashMap<>();

    //variables
    private String[] checkTab = new String[3];
    private HashMap<String, Integer> checkMap = new HashMap<>();
    private Integer lastValue;
    private Integer sum;
    private String lastChar;
    private Boolean sumIsOk;
    private Integer index;
    //

    //Reset functions
    void fillRomanMap(){
        romanMap.put("CM", 900);
        romanMap.put("M", 1000);
        romanMap.put("CD", 400);
        romanMap.put("D", 500);
        romanMap.put("XC", 90);
        romanMap.put("C", 100);
        romanMap.put("XL", 40);
        romanMap.put("L", 50);
        romanMap.put("IV", 4);
        romanMap.put("IX", 9);
        romanMap.put("X", 10);
        romanMap.put("V", 5);
        romanMap.put("I", 1);
    }
    void resetCheckMap(){
        checkMap.clear();
        checkMap.put("M", 1);
        checkMap.put("C", 1);
        checkMap.put("X", 1);
        checkMap.put("I", 1);
    }
    void resetRomanTab(){
      checkTab[0] = "D";
      checkTab[1] = "L";
      checkTab[2] = "V";
    }

    void resetAll(){
        index = 0;
        resetCheckMap();
        resetRomanTab();
        lastValue = 1001;
        sum = 0;
        lastChar = "0";
        sumIsOk = true;
    }
    void initialize(){
        resetAll();
        fillRomanMap();
    }
    //

    //Check Functions
    Boolean checkForChar(String character){
        return romanMap.containsKey(character);
    }
    Boolean checkForRepetitivity(String character){
        if(character.equals(lastChar)){
            System.out.println("rep");
            if (checkMap.containsKey(character)){
                System.out.println("0.1");
                updateCheckMap(character);
                if (checkMap.containsValue(3)){
                    System.out.println("1");
                    return false;
                }
            }
            if (searchTab(checkTab, character) || character.length()==2){
                System.out.println("2");
                return false;
            }
        }
        return true;
    }
    Boolean checkForValue(String character){
        if (romanMap.get(character) > lastValue){
            return false;
        }
        lastValue = romanMap.get(character);
        return true;
    }
    Boolean checkForValue2(String character){
        if (lastChar.length() == 2){
            return !romanMap.containsValue(romanMap.get(lastChar) + romanMap.get(character));
        }
        return true;

    }

    Boolean checkAll(String character){
        boolean b =
                checkForChar(character) &&
                checkForRepetitivity(character) &&
                checkForValue(character) &&
                checkForValue2(character);
        return b;
    }
    //

    //Other functions
    void updateCheckMap(String character){
        Integer savedValue;
        savedValue = checkMap.get(character);
        checkMap.remove(character);
        checkMap.put(character, savedValue++);
    }
    Boolean searchTab(String[] tab, String character){
        for (String x: tab) {
            if (x.equals(character)){
                return true;
            }
        }
        return false;
    }
    //

    //core functions
    Integer charToNumber(String character){
        if(!sumIsOk){
            return 0;
        }
        index += character.length();
        return romanMap.get(character);
    }

    String extractChar(String romanNumber){
        StringBuilder chars = new StringBuilder();
        chars.append(romanNumber.charAt(index));
        if (romanNumber.length() -1 > index) {
            chars.append(romanNumber.charAt(index + 1));
        }
        if (checkAll(chars.toString())){
            lastChar = chars.toString();
            return chars.toString();
        }
        chars.delete(1, 2);
        if (checkAll(chars.toString())){
            lastChar = chars.toString();
            return chars.toString();
        }
        sumIsOk = false;
        return "";

    }

    Integer romanNumberToArabic(String romanNumber){
        while (romanNumber.length() > index && sumIsOk){
            if (sumIsOk){
                sum += charToNumber(extractChar(romanNumber));
            }else{
                sum = 0;
            }
        }
        return sum;
    }
    //

    //LOOP
    void appLoop(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        Boolean onOff = true;
        String romanNum = "";
        initialize();
        while (onOff){
            resetAll();
            System.out.println("----WYBIERZ OPCJĘ----");
            System.out.println("[1] - Zamień liczbę rzymską na arabską.");
            System.out.println("[2] - Wyjdź");
            System.out.println("---------------------");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Podaj liczbę rzymską");
                    romanNum = scanner.nextLine();
                    do {
                        if (romanNumberToArabic(romanNum) == 0) {
                            System.out.println("Podałeś niepoprawnie zapisaną cyfrę! spróbuj jeszcze raz.");
                        }else{
                            System.out.println("Liczba arabska: " + romanNumberToArabic(romanNum));
                        }
                    }
                    while (romanNumberToArabic(romanNum) == 0);
                    break;
                case 2:
                    onOff = false;
                    break;
            }
        }
    }
    //
}
//CM M CD D XC C XL L IV V I
