# NewsFeed

Приложение отображения новостной ленты

## Функциональные требования

1. Просмотр списка новостей. Выводится заголовок, картинка, описание и дата.
2. Кеширование новостей в БД.
3. В случае возникновения ошибки загрузки появляется кнопка "Повторить".
4. При клике по списку осуществлен переход на сайт статьи.

## Нефункциональные требования.
Приложение должно состоять трех экранных форм: 
1. splash screen
2. пагинированных список 
3. webview

Список новостей находятся по этому адресу:

https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publi shedAt&apiKey=2bb5f673d126444da2ed6b70a0fc5b1d8&page={page c 1 до 5}

Приложение должно получить данные по сети. Сохранить их в БД (SQLite).

Дата публикации статьи отображается в формате число.месяц.год. Пример: 22.11.1987 г.

## Используемый инструментарий и технологии
1. Clean Architecture
2. MVVM
3. LiveData
4. Retrofit
5. Room
6. Navigation
7. Coil
8. Paging3
8. Hilt
9. Coroutines
10. Kotlin
