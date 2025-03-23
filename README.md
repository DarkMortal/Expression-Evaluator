# Expression Evaluator
## Usage
```java
import evaluator.ExpressionEvaluator;

class Main{
    public static void main(String[] args){
        try {
          String equation = "(1+i)^(2-i)+2^-i";
          ExpressionEvaluator exp = new ExpressionEvaluator();
          System.out.println("Result: " + exp.evaluateEquation(equation, 4, true));
      	}catch(Exception e) {
          e.printStackTrace();
      	}
    }
}
```
## Output
```
SubEquation: 1+i
Evaluating : 1.00 + 1.00i
SubEquation: 2-i
Evaluating : 2.00 - 1.00i
Evaluating : (1.0 + i) ^ (2.00 - 1.00i)
Evaluating : 2.00 ^ -1.00i
Evaluating : (1.49 + 4.13i) + (0.77 - 0.64i)
Result: (2.2593 + 3.4868i)
```
## Working with variables
```java
import evaluator.ExpressionEvaluator;

class Main{
    public static void main(String[] args){
        try {
          String equation = "sin(cos(x+tan(x)))+cos(sin(x)-tan(x)) ";
          ExpressionEvaluator exp = new ExpressionEvaluator();
          exp.setVariable("x", 3.0);
          System.out.println("Result: " + exp.evaluateEquation(equation, 4, true));
      	}catch(Exception e) {
          e.printStackTrace();
      	}
    }
}
```
## Output
```
SubEquation: cos(x+tan(x))
SubEquation: x+tan(x)
SubEquation: x
Evaluating : tan(3.0)
Evaluating : 3.0 + -0.14
Evaluating : cos(2.86)
Evaluating : sin(-0.96)
SubEquation: sin(x)-tan(x)
SubEquation: x
Evaluating : sin(3.0)
SubEquation: x
Evaluating : tan(3.0)
Evaluating : 0.14 - -0.14
Evaluating : cos(0.28)
Evaluating : -0.82 + 0.96
Result: 0.1409
```
Click here to access the [documentation](https://darkmortal.github.io/Expression-Evaluator/)
***
