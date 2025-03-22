package evaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the interface for the ExpressionEvaluator class
 * @author Saptarshi Dey
 * @since March 2025
 * @version 1.0
 */

public interface Evaluator {

    // List of terminal symbols in decreasing order of precedence
    List<Character> terminalSymbols = new ArrayList<>(){{
        add('^'); add('/'); add('*'); add('-'); add('+');
    }};

    String evaluateEquation(String equation,int precision, boolean verbose) throws Exception;
}