package org.vician.shapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShellApplication {

    protected final String headerMethodName = "method name";
    protected final String headerMethodUnderscores = "-----------";
    protected final String headerDesc = "description";
    protected final String headerDescsUnderscores = "-----------";
    
    protected int longestMethodName;
    
    protected List<Method> methods;
    
    protected List<String> descriptions;
    
    public void runConsole() throws Exception{
        initConsole();
        
        System.out.println(getClass().getSimpleName() + " started.");
        System.out.println("Type 'help' to print all methods.");
        System.out.println();
        
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        String command;
        while (true) {
            command = in.readLine();
            
            if (command.length() == 0) {
                continue;
            }
            
            boolean commandExecuted = false;
            for (int i = 0; i < methods.size(); i++) {
                Method m = methods.get(i);
                if (command.equals(m.getName())) {
                    
                    ShellMethod cm = m.getAnnotation(ShellMethod.class);
                    
                    String[] values = new String[cm.params().length];
                    for (int p = 0; p < cm.params().length; p++) {
                        String param = cm.params()[p];
                        if (param.length() == 0) {
                            continue;
                        } else {
                            System.out.print(param + ": ");
                            
                            String value = in.readLine();
                            while (value.charAt(value.length() - 1) == '\\') {
                                value = value.substring(0, value.length() - 1); // remove '\' char
                                value += '\n' + in.readLine();
                            }
                            values[p] = value;
                        }
                    }
                    
                    try {
                        Object retValue = m.invoke(this, values);
                        if (retValue != null) {
                            System.out.println(retValue);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    System.out.println();
                    commandExecuted = true;
                }
            }
            if (!commandExecuted) {
                System.out.println("Invalid method name. Type 'help' to print all methods.");
                System.out.println();
            }
        } 
    }
    
    protected void initConsole() {
        methods = new ArrayList<Method>();
        descriptions = new ArrayList<String>();
        longestMethodName = headerMethodName.length();
        Set<String> methodNames = new HashSet<String>();
        for (Method m : getClass().getMethods()) {
            ShellMethod cm = m.getAnnotation(ShellMethod.class);
            if (cm != null) {
                if (methodNames.contains(m.getName())) {
                    throw new RuntimeException("Console method names have to be unique. " +
                    		"Found multiple methods with the same name: " + m.getName());
                }
                for (Class<?> paramType : m.getParameterTypes()) {
                    if (!String.class.isAssignableFrom(paramType)) {
                        throw new RuntimeException("All parameters of console method must be " +
                        		"of type String. Invalid method name: " + m.getName());
                    }
                }
                if (cm.params().length != m.getParameterTypes().length) {
                    throw new RuntimeException("Invalid length of [params] field of [" + ShellMethod.class.getSimpleName() + "] " +
                            "annotation. Length of 'params' field have to be equal to the number of parameters of the [" + m + "] method.");
                }
                methods.add(m);
                descriptions.add(cm.description());
                if (m.getName().length() > longestMethodName) {
                    longestMethodName = m.getName().length();
                }
            }
        }
    }
    
    @ShellMethod(description = "prints this help")
    public void help() {
        System.out.printf("%-" + longestMethodName + "s - ", headerMethodName);
        System.out.print(headerDesc);
        System.out.println();
        System.out.printf("%-" + longestMethodName + "s - ", headerMethodUnderscores);
        System.out.print(headerDescsUnderscores);
        System.out.println();
        for (int i = 0; i < methods.size(); i++) {
            System.out.printf("%-" + longestMethodName + "s - ", methods.get(i).getName());
            System.out.print(descriptions.get(i));
            System.out.println();
        }
    }
    
    @ShellMethod(
            params = {"method name"},
            description = "prints attributes of any available method")
    public void params(String methodName) {
        boolean methodExists = false;
        for (Method m : methods) {
            if (methodName.equals(m.getName())) {
                ShellMethod cm = m.getAnnotation(ShellMethod.class);
                if (cm.params().length == 0) {                    
                    System.out.println("\t(this method has empty parameters)");
                } else {
                    for (String p : cm.params()) {
                        System.out.println("\t" + p);
                    }
                }
                methodExists = true;
            }
        }
        if (!methodExists) {
            System.out.println("Invalid method name.");
        }
    }
    
    @ShellMethod(description = "exits the application")
    public void exit() {
        System.out.println("Closing application.");
        System.exit(0);
    }
    
}
