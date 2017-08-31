Shapp
=====

Shapp is a utility to simplify development of shell (command line interface) Java applications.

You can declare shell commands simply by annotating java methods. E.g. to add `helloWorld` command
to your shell application you create `public void helloWorld()` method and annotate it
with `@ShellMethod` annotation:

    public class MyShellApp extends ShellApplication {

        public static void main(String[] args) {
            // Scan annotated methods and start the console
            new MyShellApp().start();
        }

        @ShellMethod(
                description = "says hello to my new buddy",
                params = {"buddy name"})
        public void helloWorld(String buddyName) {
            System.out.println("Hello " + buddyName + ". Have a nice day.");
        }

    }

Now, after running your command line Java application your `helloWorld` command is ready for invocation.

    MyShellApp started.
    Type 'help' to print all methods.

***help*** lists available commands with their description.
Notice that our annotated `helloWorld` method is automatically listed among other commands.
There are also three common build-in commands on your disposal - `help`, `exit` and `params`.

    help
    method name - description
    ----------- - -----------
    helloWorld  - says hello to my new buddy
    params      - prints input params of a command
    exit        - exits the application
    help        - prints this help

***helloWorld*** does what we determined it to do. It prints hello message after we type in the buddy name:

    helloWorld
    buddy name: Rachel
    Hello Rachel. Have a nice day.

***params*** command comes handy to examine all input parameters of any available command:

    params
    method name: helloWorld
	    buddy name

***exit*** command finishes the application.

    exit
    Closing the application.
