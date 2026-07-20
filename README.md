# RIM (Android+Compose)

## Краткое описание

Нативное Android-приложение (Kotlin + Jetpack Compose) для просмотра персонажей, эпизодов и локаций вселенной Rick and Morty. Clean Architecture, многомодульная структура (Hilt, ViewModel, Paging3).

Для увеличения фото персонажа используется доработанный fork [Compose-Zoom](https://github.com/SmartToolFactory/Compose-Zoom): в исходной версии жест pinch часто проигрывал скроллу списка/экрана; в форке приоритет pinch-жеста в скроллируемом контейнере исправлен.

---

## Стек технологий

| Слой | Технология | Назначение |
|---|---|---|
| Язык | Kotlin 2.0 | Основной язык |
| UI | Jetpack Compose (Material 3) | Декларативный UI |
| DI | Hilt | Внедрение зависимостей |
| Состояние / async | ViewModel + Kotlin Coroutines + Flow | UI-state, Paging |
| Пагинация | Paging3 | Бесконечная прокрутка каталогов |
| Сеть | Retrofit + OkHttp | HTTP-клиент |
| Изображения | Coil | Загрузка аватарок |
| Хранение | EncryptedSharedPreferences | Токен fake-login |
| Навигация | Navigation Compose | Type-safe routes |
| Zoom | локальный fork Compose-Zoom | Pinch-to-zoom на detail |

---

## Особенности

- **Многомодульная архитектура:** `domain` → `data` → `presentation` (`core-test` — общие тестовые зависимости).
- **Fake-login** — экран входа, токен (UUID) в EncryptedSharedPreferences; при наличии токена старт с Home.
- **Characters: адаптивный grid** — `LazyVerticalGrid`, 1–6 колонок по ширине; Episodes и Locations — списки с той же пагинацией.
- **Пагинация (infinite scroll)** — Paging3.
- **Pinch-to-zoom** — изображения Character через локальный fork Compose-Zoom (исправление приоритета pinch в скролле).
- **Боковое меню** — `ModalNavigationDrawer`: Characters, Episodes, Locations, Logout.
- **Тема** — light / dark / system (persist).
- **Локализация** — English-only (`values/strings.xml`; `resourceConfigurations = en`).

---

## Запуск

Требуется **JDK 18** и Android SDK (compileSdk 35, minSdk 26, targetSdk 34).

```bash
./gradlew assembleDebug        # Сборка debug APK
./gradlew detekt               # Статический анализ
./gradlew test                 # Unit-тесты
./gradlew koverHtmlReport      # Отчёт покрытия
./presentation/run.sh          # install + launch debug (device/emulator)
./presentation/run.sh --release # install + launch release
```

---

## Ссылки

- Хаб: https://github.com/menovazin/rim-main
- Бэкенд: https://github.com/menovazin/rim-backend
