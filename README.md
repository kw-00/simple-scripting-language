### A simple scripting language with JFlex and CUP

#### Requires:
- Linux OS (tested with Kali 2024.1)
- Java 17 (tested with Amaxon Corretto 17.0.17.0.1-linux-x64)
- JFlex (tested with 1.9.1)

#### Make sure that:
1. "java" command for Java 17 works
1. "javac" command works
1. "jflex" command works

For example, run the following:
`
java --version;
javac --version;
jflex --version;
`

#### Instructions
1. Make sure you're in project root
1. Run `source setup.sh`. In case of missing permissions, run `chmod +x setup.sh`. This will set up your PATH variables in your shell. It won't alter your system globally in any way, which means you need to run `source setup.sh` every time you open a terminal and want to use the scripting language.
1. After that, you gain access to the following commands:
    - build.sh -- builds the interpreter
    - run.sh -- runs the interpreter
    - buildrun.sh -- self-explanatory
    - test.sh -- like run, but accepts input from ./test/test.txt (. being the project root)

