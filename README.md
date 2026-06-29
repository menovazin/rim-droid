# RIM — Rick and Morty Explorer (Android)

Нативный Android-порт приложения RIM с Flutter на Kotlin + Jetpack Compose, построенный по принципам Clean Architecture.

## Стек

| Компонент | Технология |
|---|---|
| Язык | Kotlin 2.0 |
| UI | Jetpack Compose (Material 3) |
| Архитектура | Clean Architecture (многомодульная) |
| DI | Hilt |
| Навигация | Navigation Compose (type-safe routes) |
| Сеть | Retrofit + OkHttp |
| Пагинация | Paging3 |
| Изображения | Coil |
| Безопасное хранилище | EncryptedSharedPreferences |
| Линт | Detekt |
| Покрытие | Kover |
| Тесты | JUnit + Mockito + Truth + Turbine |

## Структура модулей

```
rim_droid/
├── domain/        ← Чистый Kotlin: сущности, интерфейсы репозиториев, use-case'ы
├── data/          ← Retrofit API, DTO, мапперы, PagingSource, реализации репозиториев
├── presentation/  ← Compose UI, ViewModel'и, Hilt DI, навигация
└── core-test/     ← Общие тестовые зависимости и хелперы
```

**Правила зависимостей:** `domain` → ничего; `data` → `domain`; `presentation` → `domain` + `data`.

## Запуск

```bash
# Сборка debug APK
./gradlew assembleDebug

# Статический анализ
./gradlew detekt

# Unit-тесты
./gradlew test

# Отчёт покрытия
./gradlew koverHtmlReport
```

Требуется JDK 17+ и Android SDK (compileSdk 34).

## Фичи

- **Fake-login** — экран входа с генерацией токена в EncryptedSharedPreferences
- **Splash-гейт** — автоматический редирект по наличию токена
- **Боковое меню** — ModalNavigationDrawer с разделами: Персонажи, Эпизоды, Локации, Выйти
- **Адаптивная сетка** — LazyVerticalGrid (1–6 колонок по ширине экрана)
- **Paging3** — бесконечная прокрутка для всех каталогов
- **Detail-экраны** — с pinch-to-zoom, иконками пола/типа, аватарками
- **Визуальное обогащение** — CDN аватарки, парсинг S01E01, иконки типа локации
- **Локализация** — EN/ES (values, values-es)

## API

- Rick and Morty REST API: https://rickandmortyapi.com/api
- CDN аватарок: `https://semester.syazy.com/rickandmorty/{id}.jpeg`

## Референсы

- `arch-refs/rim-flutter` — исходная Flutter-реализация (только чтение)
- `arch-refs/android-template` — эталонный Android-шаблон AliAsadi (только чтение)
- `openspec/changes/port-rim-to-android-native/` — OpenSpec-артефакты портирования