## Приложение для просмотра новостей
### Что может:
 - Показывать важные новости
 - Отображать фрагмент любой новости, с возможностью открыть ее целиком в браузере.
 - Выполнять поиск и фильрацию новостей. То же самое по источникам.
 - Добавлять новости в избранное
 - Кешировать все отображенные  новости.(*После первичной подгрузки новостей, приложение может работать без интернета)*

### Стек:
 - Dagger 2
 - Retrofit
 - Room
 - MVP(Moxy) - на экранах *Headlines* *Sources*
 - MVVM - экран *Saved*
 - MVVM+(на LiveData) - экран фильтров новостей
 - MVI(Orbit) - экран фильтров источников
 - RxJava 3 - работа с сетью
 - Coroutines - работа с кешем
 - Flow - экран Saved
 - Навигация на собственном решении(через FragmentManager)
 - Пагинация на собственном решении.
 - Каждый экран поддерживает SwipeToRefresh
 - Lottie и SplashScreen
