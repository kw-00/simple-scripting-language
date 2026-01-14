# Parser bashopodobny

## Zrealizowane punkty
- Interpreter -- działa zarówno w sposób tradycyjny (polecenie->ENTER-> wynik->następne polecenie) jak i przy pomocy bloku (BEGIN ...polecenia... END -> wynik)
- Funkcjonalność bazowa -- podobnie jak w poleceniu, z małymi różnicami:
    - nazwy plików mogą zawierać znaki spoza zestawu określonego w poleceniu.
    - można przekierować wynik komendy do wielu plików równocześnie (echo costam > plik1 plik2 ...)
- R1. Napisy w cudzysłowach -- tak jak w poleceniu. Jednakże, ze względu na naturę pisanego przeze mnie interpretera, znak nowej linii kończy polecenie, chyba, że użyje się konstrukcji BEGIN wyrażenie END. Dlatego do przetestowania znaków nowej linii w napisie trzeba użyć tej konstrukcji
- R2. Obsługa błędów -- jak w poleceniu. Funkcjonalność $? została zrealizowana za pomocą systemu zmiennych. Istnieje również zmienne ERROR, która zawiera nazwę ostatniego błędu. 
- R3. Wieloargumentowe echo -- tak jak w poleceniu. Wszystkie komendy są wieloargumentowe.
- R4. | oraz grep -- tak jak w poleceniu.
- R6. Zmienne -- jak w poleceniu
- R8. Wynik podprogramu -- tak jak poleceniu

## Obsługa
### Wymagania
- Linux
- Java 17 (działające komendy "java" oraz "javac")
- JFlex (działająca komenda "jflex")

### Obsługa
1. Będąc w folderze głównym projektu (simple-scripting-language), należy wprowadzić następującą rzecz w terminal:
`source setup.sh`. Komenda ta lokalnie przygotowuje zmienne środowiskowe, które pozwalają na użycie kolejnych komend.

1. Następnie, program budujemy przy pomocy
`build.sh` w tym samym terminalu.

1. Na koniec, interpreter uruchamiamy poprzez `run.sh`.

### Struktura projektu
Folder src/ zawiera pliki źródłowe. Są nimi Main.java (interpreter), Data.java (klasa, która zawiera stan oraz funkcje używane przez parser), Lexer.flex oraz Parser.cup.

Po użyciu komendy `build.sh` powstają dwa nowe foldery - src/java/ oraz src/out/.
Do folderu src/java/ trafiają skompilowane pliki .java. Folder src/out/ zawiera pliki .class.

### Ostatnie słowa
Z góry przepraszam za strukturę kodu. Jako nowicjusz nie wiedziałem od samego początku, jak będzie wyglądał mój program, toteż nie jest on przykładem dobrego programowania obiektowego.
