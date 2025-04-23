import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import complex.Complex;
import evaluator.ExpressionEvaluator;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
    ExpressionEvaluator exp = new ExpressionEvaluator(new HashMap<>() {{
        // fifth root of unity
        put("x", new Complex(Math.cos(2.0 * Math.PI / 5.0), Math.sin(2.0 * Math.PI / 5.0)));
    }});

    String[] equations_without_verbose = {
            "-1.3+e^(2.3-1.2)-sin(pi)",
            "3^-1+2^-2", "(1+i)^(2-i)+2^-i",
            "1.2+(4.5-2.2/0.5+(3.45-2.22))+3.5-2.1"
    }, equations_with_verbose = {
            "x^4+x^3+x^2+x+1",
            "sin(2*x)-2*sin(x)*cos(x)",
            "sin(cos(x+tan(x)))+cos(sin(x)-tan(x))"
    };

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Order(1)
    @Test
    void TestWithoutVerbose1() {
        assertArrayEquals(
            Arrays.stream(equations_without_verbose).map((String equation) -> {
                try {
                    return exp.evaluateEquation(equation, 4, false);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return equation;
        }).toArray(), new String[] { "1.7042", "0.5833", "(2.2593 + 3.4868i)", "3.93" });
    }

    @Order(2)
    @Test
    void TestWithVerbose1() throws Exception {
        assertEquals("0.00", exp.evaluateEquation(equations_with_verbose[0], 2, true));

        assertArrayEquals(new String[] {
            "Evaluating : (0.31 + 0.95i) ^ 4.0",
            "Evaluating : (0.31 + 0.95i) ^ 3.0",
            "Evaluating : (0.31 + 0.95i) ^ 2.0",
            "Evaluating : (0.31 - 0.95i) + (-0.81 - 0.59i)",
            "Evaluating : (-0.5 - 1.54i) + (-0.81 + 0.59i)",
            "Evaluating : (-1.31 - 0.95i) + (0.31 + 0.95i)",
            "Evaluating : -1.0 + 1.0"
        }, Arrays.stream(outContent.toString(StandardCharsets.UTF_8).trim().split("\n")).map(String::trim).toArray());
    }

    @Order(3)
    @Test
    void TestWithVerbose2() throws Exception {
        assertEquals("0.00", exp.evaluateEquation(equations_with_verbose[1], 2, true));

        assertArrayEquals(new String[] {
            "SubEquation: 2*x",
            "Evaluating : 2.0 * (0.31 + 0.95i)",
            "Evaluating : sin((0.62 + 1.90i))",
            "SubEquation: x",
            "Evaluating : sin((0.31 + 0.95i))",
            "SubEquation: x",
            "Evaluating : cos((0.31 + 0.95i))",
            "Evaluating : 2.0 * (0.45 + 1.05i)",
            "Evaluating : (0.9 + 2.10i) * (1.42 - 0.33i)",
            "Evaluating : (1.98 + 2.67i) - (1.98 + 2.67i)"
        }, Arrays.stream(outContent.toString(StandardCharsets.UTF_8).trim().split("\n")).map(String::trim).toArray());
    }

    @Order(4)
    @Test
    void TestWithVerbose3() throws Exception {
        assertEquals("(1.8834 + 1.1667i)", exp.evaluateEquation(equations_with_verbose[2], 4, true));

        assertArrayEquals(new String[] {
            "SubEquation: cos(x+tan(x))",
            "SubEquation: x+tan(x)",
            "SubEquation: x",
            "Evaluating : tan((0.31 + 0.95i))",
            "Evaluating : (0.31 + 0.95i) + (0.14 + 0.77i)",
            "Evaluating : cos((0.45 + 1.72i))",
            "Evaluating : sin((2.61 - 1.17i))",
            "SubEquation: sin(x)-tan(x)",
            "SubEquation: x",
            "Evaluating : sin((0.31 + 0.95i))",
            "SubEquation: x",
            "Evaluating : tan((0.31 + 0.95i))",
            "Evaluating : (0.45 + 1.05i) - (0.14 + 0.77i)",
            "Evaluating : cos((0.32 + 0.28i))",
            "Evaluating : (0.9 + 1.25i) + (0.99 - 0.09i)",
        }, Arrays.stream(outContent.toString(StandardCharsets.UTF_8).trim().split("\n")).map(String::trim).toArray());
    }

    @Order(5)
    @Test
    void TestWithoutVerbose2() throws Exception {
        double x = Double.parseDouble(
            exp.evaluateEquation("2 + (  log(5) -   log(3)  )    /  log(4)", 5, true)
        );
        exp.setVariable("x", x);
        String expression = "4^(x+1) - 4^(x-1)";
        assertEquals(100.0, Double.parseDouble(
            exp.evaluateEquation(expression, 2, false)
        ));
        try{
            exp.evaluateEquation("(2+3", 0, false);
        } catch (Exception exc){
            assertEquals("Invalid parenthesis sequence", exc.getMessage());
        }
    }
}