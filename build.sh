#!/bin/bash

build=false
if [[ " $@ " =~ " --build " ]] then
    build=true
fi

run=false
if [[ " $@ " =~ " --run " ]] then
    run=true
fi

if [[ "${build}" = false && "${run}" = false ]] then
    build=true
    run=true
fi

echo $@
echo ${build} ${run}

base_path="$(pwd)"
lib_path="lib"
src_path="src"
java_path="${src_path}/java"
out_path="${src_path}/out"
classpath="${base_path}/${lib_path}/*:${out_path}"

if [[ "${build}" = true ]] then
    # Compile .flex
    jflex "${src_path}/Lexer.flex" -d "${java_path}"

    # Compile .cup
    cd "${java_path}"
    java -cp "${classpath}" java_cup.Main < "${base_path}/${src_path}/Parser.cup"
    cd "${base_path}"

    # Compile Main
    javac -d "${out_path}" -cp "${classpath}" "${src_path}/Main.java"
fi

if [[ "${run}" = true ]] then
    java -cp "${classpath}" "${src_path}/Main"
fi