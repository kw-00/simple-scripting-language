#!/bin/bash


source env.sh

rm -rf ./src/java
rm -rf ./src/out

# Compile .flex
echo "==== Compiling Lexer ===="
jflex "${src_path}/Lexer.flex" -d "${java_path}"
if [[ $? != 0 ]]; then
    exit 1
fi

# Compile .cup
echo "==== Compiling Parser ===="
cd "${java_path}"
java -cp "${classpath}" java_cup.Main < "${base_path}/${src_path}/Parser.cup"
if [[ $? != 0 ]]; then
    exit 2
fi
cd "${base_path}"

# Copy Main to the java folder, where it will be compiled 
# together with the rest of the source files
cp "${src_path}/Main.java" "${java_path}/Main.java"


# Compile Main
echo "==== Compiling Main ===="
javac -d "${out_path}" -cp "${classpath}" -sourcepath "${java_path}" "${java_path}/Main.java" -Xlint:deprecation
if [[ $? != 0 ]]; then
    exit 3
fi


