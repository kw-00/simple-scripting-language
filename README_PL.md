# Parser bashopodobny

Zrealizowałem następujące punkty:
- Interpreter -- działa zarówno w sposób tradycyjny (polecenie->ENTER-> wynik->następne polecenie) jak i przy pomocy bloku (BEGIN ...polecenia... END -> wynik)
- Funkcjonalność bazowa -- podobnie jak w poleceniu, z małymi różnicami:
    - nazwy plików mogą zawierać znaki spoza zestawu określonego w poleceniu.
    - można przekierować wynik komendy do wielu plików równocześnie (echo costam > plik1 plik2 ...)
- R1. Napisy w cudzysłowach -- tak jak w poleceniu. Jednakże, ze względu na naturę pisanego przeze mnie interpretera, znak nowej linii kończy polecenie, chyba, że użyje się konstrukcji BEGIN wyrażenie END. Dlatego do przetestowania znaków nowej linii w napisie trzeba użyć tej konstrukcji
- R2. Obsługa błędów -- jak w poleceniu. Dodatkowo, każdy błąd