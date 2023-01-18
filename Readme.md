Repozytorium:
backend: https://github.com/matnow138/PriceComparisonBackend/tree/9fd2a712af5ae9b9a03694d829de43bc63d53021
Frontend: https://github.com/matnow138/PriceFrontend/tree/d517245258a68eeda5893e6289cfeacfbcc9249b
EurekaCloud: https://github.com/matnow138/EurekaCloud/tree/5a0454c1a5c0d73c0d459bbba53443ec74f61b9c
EurekaServer: https://github.com/matnow138/EurekaServer/tree/20de4ef36875bb6e59bbc1483ad53a71672453dd
Gateway: https://github.com/matnow138/EurekaServer/tree/20de4ef36875bb6e59bbc1483ad53a71672453dd

Uruchamianie:

1. EurekaCloud
2. EurekaServer
3. Gateway
4. Backend
5. Frontend

Uwagi:

Backend ma schema.sql i data.sql ustawione na uruchamianie za każdym uruchomieniem programu - może to wyrzucać błędy jeśli program będzie odpalany ponownie.
Należy wtedy usunąć tabele w bazie. ewentualnie usunąć tabelę currency.
Nie wszystkie endpointy są dostępne przez frontend - wychodzę z założenia, że nie powinno to być ogólnie dostępne.

