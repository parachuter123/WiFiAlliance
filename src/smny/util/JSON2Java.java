package smny.util;
import java.io.FileReader;
import java.io.File;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;


import java.util.concurrent.atomic.AtomicReference;
public class JSON2Java {
   private static final ScriptEngine jsonParser;
   static
   {
        FileReader FR = null;
      try
      {
        		File FileName = new File(JSON2Java.class.getResource("JSON2Java.js").toURI());
        		FR = new FileReader(FileName);
         ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
         engine.eval(FR);
         jsonParser = engine;
      }
      catch (Exception e)
      {
         // Unexpected
         throw new AssertionError(e);
      }finally{
        		if(FR != null)try{
        				FR.close();
        		}catch(Exception e){
        			
        		}
        }
   }
   public static Object parseJSON(String json)
   {
      try
      {
         SimpleBindings bindings = new SimpleBindings();
         String eval = "var tmp = (" + json + ");var o = new java.util.concurrent.atomic.AtomicReference(tmp.toJava());";
         jsonParser.eval(eval, bindings);
         AtomicReference ret = (AtomicReference)bindings.get("o");
         return ret.get();
      }
      catch (ScriptException e)
      {
         throw new RuntimeException("Invalid json", e);
      }
   }
    public static void main(String args[]){
    	/*
    	*/
        
       
        
    }
}
