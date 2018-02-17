package com.generics;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by Alex Grischenko on 9/8/2017.
 */
public class RunningCodeFromExternalFiles {

    //Executing functions from external JS file
    public static String md2html() throws ScriptException, FileNotFoundException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        File functionscript = new File("public/lib/marked.js");
        Reader reader = new FileReader (functionscript);
        engine.eval(reader);

        Invocable invocableEngine = (Invocable) engine;
        Object marked = engine.get("marked");
        Object lexer = invocableEngine.invokeMethod(marked, "lexer", "**hello**");
        Object result = invocableEngine.invokeMethod(marked, "parser", lexer);
        //invocableEngine.invokeFunction ("yourFunction", "param");
        return result.toString();

    }

}
