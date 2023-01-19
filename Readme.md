Repozytorium:
backend: https://github.com/matnow138/PriceComparisonBackend/
Frontend: https://github.com/matnow138/PriceFrontend/
EurekaCloud: https://github.com/matnow138/EurekaCloud/
EurekaServer: https://github.com/matnow138/EurekaServer/
Gateway: https://github.com/matnow138/Gateway

Uruchamianie:

1. EurekaCloud
2. EurekaServer
3. Gateway
4. Backend
5. Frontend

Uwagi:

Backend ma schema.sql i data.sql ustawione na uruchamianie za każdym uruchomieniem programu - może to wyrzucać błędy jeśli program będzie odpalany ponownie.
Należy wtedy usunąć tabele w bazie. ewentualnie usunąć tylko tabelę currency.

Opis programu:

Program służy do obserowania wybranych ofert na stronie www.amazon.com, powiadamiania użytkownika o zmianie ceny i osiągnięciu ceny maksymalnej, którą ustawia użytkownik.
W trakcie wykrycia obniżki ceny produktu, zostaje wywołana metoda weryfikująca, czy cena po przeliczeniu z USD na PLN jest poniżej ceny maksymalnej.
Weryfikacja obniżki ceny produktu następuje raz na godzinę.
Kurs jest pobierany w trakcie sprawdzania ceny produktu - na bieżąco ze strony NBP.
Jeśli cena będzie poniżej maksymalnej - użytkownik otrzyma powiadomienie na maila podanego przy tworzeniu użytkownika.

Produkt ze strony www.amazon.com rejestruje się poprzez podanie ASIN. np: https://www.amazon.com/dp/B005YQZ1KE/ ASIN: B005YQZ1KE
