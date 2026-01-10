#!/bin/bash

base_path="$(pwd)"
lib_path="lib"
src_path="src"
java_path="${src_path}/java"
out_path="${src_path}/out"
classpath="${base_path}/${lib_path}/*:${out_path}"

# Compile .flex
jflex "${src_path}/Lexer.flex" -d "${java_path}"

# Compile .cup
cd "${java_path}"
java -cp "${classpath}" java_cup.Main < "${base_path}/${src_path}/Parser.cup"
cd "${base_path}"

# Compile Main
javac -d "${out_path}" -cp "${classpath}" "${src_path}/Main.java"

java -cp "${classpath}" "${src_path}/Main"