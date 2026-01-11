#!/bin/bash


source env.sh

# Compile .flex
jflex "${src_path}/Lexer.flex" -d "${java_path}"

# Compile .cup
cd "${java_path}"
java -cp "${classpath}" java_cup.Main < "${base_path}/${src_path}/Parser.cup"

cd "${base_path}"

cp "${src_path}/Main.java" "${java_path}/Main.java"
# Compile Main
javac -d "${out_path}" -cp "${classpath}" -sourcepath "${java_path}" "${java_path}/Main.java" -Xlint:deprecation


