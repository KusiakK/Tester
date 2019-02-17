# Tester
Program odpalający metody oznaczone adnotacją @Run

## Kroki

- Stworzenie własnej adnotacji Run.
- Program z dwiema metodami oznaczonymi tą adnotacją.
- Program, który odpala tylko oznaczone metody modułu program.
- Moduły mavenowe: adnotacje (rzeczone @Run), tester (sedno zadania) i program (jako zależność używa obu pozostałych modułów).
- Wywołanie całości via mvn:exec (czy główną klasę testera) powinno odpalić każdą z metod, nawet jeśli jedna ma błąd kompilacji czy nieoczekiwany wynik.

## Wymagania

- linux
- bash lub inny shell
- maven 3.6.0

## Użycie

- włącz linuxa
- odpal terminal
- git clone https://git.epam.com/Konrad_Kusiak/Tester.git
- cd Tester
- mvn install
- mvn exec:jar -pl Program