# Zagadnienia do zaliczenia ZASD (styczeń, 2024)
## Teoria złożoności algorytmów
![Klasy problemów](img/problems_comparision.png)
### 1. Klasa NP i P.
- **dst: Definicja klasy NP i definicja klasy P.<br />**
P - Klasa problemów rozwiązalnych w czasie wielomianowym. Oznacza to, że istnieje algorytm, który rozwiązuje problem w czasie, który można ograniczyć wielomianem długości danych wejściowych.<br />
NP - Klasa problemów, dla których rozwiązanie może być sprawdzone w czasie wielomianowym. Innymi słowy, jeśli podamy rozwiązanie, możemy szybko (w czasie wielomianowym) zweryfikować jego poprawność, chociaż znalezienie samego rozwiązania może być trudne.
- **db: Jaka jest różnica pomiędzy problemami z P i NP, czy P=NP?<br />**
P są rozwiązywalne w czasie wielomianowym a NP weryfikowalne w czasie wielomianowym
Pytanie, czy P = NP, jest jednym z najważniejszych nierozstrzygniętych problemów w informatyce i teorii złożoności obliczeniowej.
- **bdb: Problemy której klasy są trudniejsze z P czy z NP? Odpowiedź uzasadnić.<br />**
NP ponieważ nie znamy rozwiązania w czasie wielomianowym
### 2. Klasa NP-zupełnych i NP trudnych.
- **dst: Definicja klasy NP-zupełnych i klasy NP-trudnych?<br />**
NP-zupełne - Są to problemy, które mają najwyższy poziom trudności wśród problemów w klasie NP. Dla każdego problemu NP-zupełnego istnieje możliwość sprowadzenia każdego innego problemu z klasy NP do niego w czasie wielomianowym. **Są decyzyjne.** <br />
NP-trudne - Niekoniecznie należą do klasy NP, ale ich trudność jest porównywalna lub większa niż trudność problemów w klasie NP. Dla problemów NP-trudnych nie jest wymagane, aby ich rozwiązania można było w czasie wielomianowym zweryfikować.
- **db: Jaka jest różnica pomiędzy problemami z klas NP-zupełnych i NP-trudnych, czy klasa NP-zupełnych zawiera się w NP-trudnych, a klasa NP-trudnych zawiera się w NP-zupełnych?<br />**
NP-trudne w przeciwieństwie do NP-zupełnych nie są weryfikowalne w czasie wielomianowym. Klasa NP-zupełnych zawiera się w NP-trudnych, lecz nie odwrotnie.
- **bdb: Problemy której klasy są trudniejsze z NP-zupełnych i czy z NP-trudnych?<br />**
NP-trudne ponieważ nie są weryfikowalne w czasie wielomianowych.
### 3. Dowodzenie NP-zupełności.
- **dst: Co to jest transformacja wielomianowa problemów, co wystarczy zrobić dla udowodnienia NP-zupełności problemu?<br />**
Transformacja wielomianowa problemów to proces przekształcania jednego problemu obliczeniowego w inny problem za pomocą algorytmu, który działa w czasie wielomianowym. Polega to na sposobie reprezentacji jednego problemu za pomocą innego w taki sposób, że rozwiązanie jednego problemu może być wykorzystane do rozwiązania drugiego.<br />
Aby udowodnić NP-zupełność należy pokazać, że problem należy do klasy NP, a potem należy przeprowadzić redukcję wielomianową z istniejącego problemu NP-zupełnego. Na przykład redukcja podziału zbioru do problemu plecakowego.
- **db: Uzasadnienie, że w ten sposób rzeczywiście można udowodnić NP-zupełność (uzasadnienie ma uwzględnić problem SAT).<br />**
Problem spełnialności formuły logicznej, znany jako SAT (z ang. "Boolean Satisfiability Problem"), polega na sprawdzeniu, czy dla danej formuły logicznej zbudowanej z zmiennych logicznych (które mogą być prawdziwe lub fałszywe) i połączonych za pomocą operatorów logicznych, istnieje przyporządkowanie wartości do tych zmiennych, które spełni warunki tej formuły.
- **bdb: Znaczenie praktyczne tego, że jakiś problem należy do klasy NP-zupełnych.<br />**
Oznacza to, że jeżeli jakikolwiek problem z nich zostanie sprowadzony do klasy P to wszystkie inne też będą do tej klasy sprowadzone.
### 4. Dowodzenie NP-trudności.
- **dst: Co to jest transformacja wielomianowa problemów, co wystarczy zrobić dla udowodnienia NP-trudności problemu?<br />**
Transformacja wielomianowa problemów to proces przekształcania jednego problemu obliczeniowego w inny problem za pomocą algorytmu, który działa w czasie wielomianowym. Polega to na sposobie reprezentacji jednego problemu za pomocą innego w taki sposób, że rozwiązanie jednego problemu może być wykorzystane do rozwiązania drugiego.<br />
Każdy problem optymalizacyjny, którego wersja decyzyjna jest
NP-zupełna jest problemem NP-trudnym.<br />
Dla wykazania trudności rozważanego problemu
optymalizacyjnego wystarcza wykazanie NP-zupełności
odpowiadającego mu problemu decyzyjnego. 
- **db: Wyjaśnić i uzasadnić stwierdzenie: problem NP-trudny to taki problem obliczeniowy, którego rozwiązanie jest co najmniej tak trudne jak rozwiązanie każdego problemu z klasy NP.<br />**
Oznacza to, że dla problemów NP-trudnych, podobnie jak dla problemów NP nie znaleziono rozwiązania w czasie wielomianowym, lecz w przeciwieństwie do nich ich weryfikowalność nie jest w czasie wielomianowym.
- **bdb: Znaczenie praktyczne tego, że jakiś problem należy do klasy NP-trudnych.<br />**
Nie da się zweryfikować rozwiązania w czasie wielomianowym. Nawet jeżeli ktoś udowodni że P=NP.
### 5. Twierdzenie Savitcha i jego konsekwencje.
- **dst: Podać treść twierdzenia Savitcha wraz z wyjaśnieniem czego ono dotyczy.<br />**
`P-SPACE=NP-SPACE`<br />
Jeśli dla danego problemu da się zweryfikować poprawność
rozwiązania dla konkretnych danych w pamięci ograniczonej
przez pewien wielomian p(n) zależny od rozmiaru problemu
n, to da się również rozwiązać ten problem w pamięci
wielomianowej.
- **db: Czego wymaga dowód twierdzenia Savitcha (co trzeba udowodnić)?<br />**
???
- **bdb: Jakie jest znaczenie twierdzenia Savitcha?<br />**
???
### 6. Twierdzenie Cooka i jego konsekwencje.
- **dst: Podać treść twierdzenia Cooka oraz podać sformułowanie problemu, którego ono dotyczy.<br />**
Problem decyzyjny jest NP-zupełny, jeśli jest w NP i jeśli każdy problem NP może być do niego zredukowany. Stwierdza, że problem spełnialności formuły logicznej, znany jako SAT jest NP-zupełny.
- **db: Czego wymaga dowód twierdzenia Cooka (co trzeba udowodnić)?<br />**
Trzeba udowodnić NP-zupełność, czyli należy pokazać, że problem należy do klasy NP, a potem należy przeprowadzić redukcję wielomianową z istniejącego problemu NP-zupełnego.
- **bdb: Jakie jest znaczenie twierdzenia Cooka?<br />**
Oznacza to, że jeżeli jakikolwiek problem NP-zupełny (np. SAT) zostanie sprowadzony do klasy P to wszystkie inne też będą do tej klasy sprowadzone.
### 7. Klasa NC (klasa Nicka).
- **dst: Definicja klasy NC.<br />**
Klasa złożoności obliczeniowej Nicka, określana jako NC, to klasa problemów decyzyjnych, które mogą być rozwiązane równolegle w deterministycznym czasie wielomianowym.
- **db: Czy P=NC?<br />**
Pytanie, czy P=NC, jest nadal otwarte i jest jednym z fundamentalnych problemów w teorii złożoności obliczeniowej.
- **bdb: Co daje praktycznie wiedza, że dany problem należy do NC?<br />**
Problemy z klasy NC mają potencjał do efektywnego rozwiązywania na architekturach równoległych. To oznacza, że przy odpowiedniej architekturze sprzętowej można je przetwarzać równocześnie na wielu procesorach, co może znacznie przyspieszyć ich rozwiązanie.
