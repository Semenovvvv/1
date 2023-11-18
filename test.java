import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;

class MathOperation
{
   private String[] _arabNums = new String[]{"1","2","3","4","5","6","7","8","9","10"};
   private String[] _romanNums = new String[]{"I","II","III","IV","V","VI","VII","VIII","IX","X"};
   public boolean isDigit(String str)
   {
      try 
      { 
         int _intValue = Integer.parseInt(str); 
         return true; 
      } 
      catch (NumberFormatException ex) { 
         return false;
      } 
   }  
  
   public int romanToArab(String str)
   {   
      return Arrays.asList(_romanNums).indexOf(str) + 1;
   }

   public String arabToRoman(int num)
   {
      if (num > 10 || num < 0)
         throw new IllegalArgumentException("Римские числа больше 10 не подерживаются");
      return _romanNums[num - 1];
   }
}

public class Main
{
   // Ничего интересного, кроме стандартного линейного поиск не придумал
   private static int countOccurrences(String inputString, char[] searchArray) {
      int count = 0;

      for (int i = 0; i < inputString.length(); i++)
      {
         for (int j = 0; j < searchArray.length; j++)
         {
            if (inputString.charAt(i) == searchArray[j]) 
               count++;
         }
      }
      return count;
   }
  
   public static void main(String[] args) 
   {
      // Мапа ошибок
      Map<String, String> errors = new HashMap<String, String>();
      errors.put("notMathOperaton", "Cтрока не является математической операцией");
      errors.put("invalidNotation", "Используются одновременно разные системы счисления");
      errors.put("negativeRomanNum", "В римской системе нет отрицательных чисел");
      errors.put("moreOneOperation", "Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

      // Объекты матеметических операций и ввода
      MathOperation MO = new MathOperation();
      Scanner in = new Scanner(System.in);

      // Переменные 
      String  operationString;
      String  arg1;
      String  arg2;
      boolean isRomanNums = false;
      char    operation;
      int     num1;
      int     num2;
      int     result = 0;

      // Ввод с консоли строки 
      operationString = in.nextLine();

      // Подсчет количества символов математических операций
      int countOccs = countOccurrences(operationString, new char[]{'+','-','*','/'});
      if (countOccs < 1)  
         throw new IllegalArgumentException(errors.get("notMathOperaton"));
      else if (countOccs > 1)
         throw new IllegalArgumentException(errors.get("moreOneOperation"));

      // Сплит искомой строки, 0 - первое число, 1 - операция, 2 - второе число 
      String[] splitOpString = operationString.split(" ");

      // Если при сплите получислось больше 3 переменных, то тут что то точно не чисто
      if (splitOpString.length > 3)
      {
         throw new IllegalArgumentException(errors.get("invalidFormat"));
      }
      
      arg1 = splitOpString[0];
      arg2 = splitOpString[2];
      operation = splitOpString[1].charAt(0);;

      // Если оба числа(аргументы) не числа(тип данных), то есть римские, то переводим в арабские
      // Свободу палестине! (Сори если полит высказывания нельзя)
      if (!MO.isDigit(arg1) && !MO.isDigit(arg2))
      {
         isRomanNums = true;
         num1 = MO.romanToArab(arg1);
         num2 = MO.romanToArab(arg2);
      }
      // Если оба числа(аргументы) int, то ок
      else if (MO.isDigit(arg1) && MO.isDigit(arg2))
      {
         num1 = Integer.parseInt(arg1);
         num2 = Integer.parseInt(arg2);
      }
      // Иначе числа не одинакового формата
      else 
      {
         throw new IllegalArgumentException(errors.get("invalidNotation"));
      }

      // Почему так хейтят ифы, то же свитч по моему громоздкий
      // Сделали бы без брейков, то цены бы им не было
      switch(operation)
      {
         case '+':
         result = num1 + num2;
         break;
         case '-':
         result = num1 - num2;
         break;
         case '*':
         result = num1 * num2;
         break;
         case '/':
         result = num1 / num2;
         break;
      }

      // В риме нет отрицательных чисел
      if (isRomanNums && result < 0)
         throw new ArithmeticException(errors.get("negativeRomanNum"));

      // Вывод
      if (isRomanNums)
      {
         System.out.println(MO.arabToRoman(result));
      }
      else 
      {
         System.out.println(result);
      }
   }
}