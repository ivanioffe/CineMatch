# CineMatch

**CineMatch** — это современное Android-приложение для поиска фильмов, построенное с использованием передовых практик разработки и актуального стека технологий.

[![check](https://github.com/ivanioffe/CineMatch/actions/workflows/check.yaml/badge.svg)](https://github.com/ivanioffe/CineMatch/actions/workflows/check.yaml)

## Основные функции

В данный момент реализованы следующие функциональные модули:

* **Onboarding**: Приветственный экран для новых пользователей.
* **Auth**: Система авторизации и регистрации.
* **Search Movies**: Удобный поиск фильмов с пагинацией.
* **Movie Details**: Подробная информация о фильме.

## Технологии

Проект написан полностью на **Kotlin** с использованием **Jetpack Compose** и придерживается принципов **Clean Architecture** и **Multi-module** организации.

### Architecture
* **MVVM / MVI** pattern
* **Multi-module** (feature-based separation)

### DI
* **Hilt**

### Data
* **Retrofit** & **OkHttp** — Сетевые запросы
* **Kotlinx Serialization** — JSON парсинг
* **Room** — Локальная база данных
* **DataStore Preferences** — Хранение настроек

### Asynchrony
* **Coroutines** & **Flow**

### UI
* **Jetpack Compose**
* **Material 3** — Дизайн-система
* **Coil 3** — Асинхронная загрузка изображений
* **Lottie** — Анимации
* **Paging 3 (Compose)** — Пагинация списков

### Testing
* **JUnit 5** & **JUnit 4**
* **Mockk** — Мокирование
* **Turbine** — Тестирование Flow
* **Truth** — Утверждения (Assertions)
* **Robolectric** — Unit-тесты с Android-окружением
* **Roborazzi** — Скриншот тестирование

### Build & Tools
* **Gradle Version Catalogs (TOML)** — Управление зависимостями
* **Convention Plugins** (в модуле `:build-logic`) — Переиспользование конфигурации сборки.
* **KSP** — Kotlin Symbol Processing
* **Ktlint** — Линтер Kotlin кода

## Структура проекта

```text
:app                # Основной модуль приложения
:feature
 ├── :auth          # Авторизации
 ├── :movie-details # Детали фильма
 ├── :onboarding    # Экран приветствия
 └── :search-movies # Поиск фильмов
:core               # Базовые модули и утилиты
:build-logic        # Кастомные Convention Plugins
```

## Установка и запуск

1.  **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/ivanioffe/CineMatch.git
    ```

2.  **Откройте проект в Android Studio.**

3.  **Синхронизируйте Gradle:**
    Дождитесь загрузки всех зависимостей.

4.  **Запустите:**
    Выберите конфигурацию `app` и эмулятор/устройство.

> Требования:
> - Android SDK 26+
> - Kotlin 2.2.0+

---

Автор: [IvanIoffe](https://github.com/IvanIoffe)

> *Если вам понравилось приложение — ставьте ⭐ и делитесь с друзьями!*
