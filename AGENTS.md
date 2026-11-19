# AGENTS.md

## Что это за проект
Одномодульное Android-приложение (`com.example.my3d`): UI на Jetpack Compose плюс нативная
библиотека C++ (`my3d`), собираемая через CMake/NDK. В репозитории нет README, CI, линтера
или конфигурации форматтера.

## Сборка / проверка (Windows PowerShell)
- Используйте `.\gradlew.bat`, а не `./gradlew`. Gradle 8.7 / AGP 8.6.1 / Kotlin 1.9.0;
  compileSdk 35, minSdk 24, targetSdk 34; NDK 26.1.10909125, CMake 3.22.1.
- Отладочная сборка: `.\gradlew.bat assembleDebug`
- JVM-юнит-тесты: `.\gradlew.bat testDebugUnitTest`
- Инструментальные тесты: `.\gradlew.bat connectedAndroidTest` (нужно устройство/эмулятор).
- Единственный модуль — `:app`, поэтому префикс задачи `:app:` можно опускать. Оба набора
  тестов пока остаются шаблонами-заглушками. `local.properties` (путь к SDK) заигнорирован
  в git и зависит от конкретной машины.

## Зависимости
- Версии и плагины находятся в `gradle/libs.versions.toml`; используйте
  `alias(libs.plugins...)` / `libs.*` и добавляйте новые зависимости там, а не в виде
  координат в строку.
- `settings.gradle.kts` задаёт `FAIL_ON_PROJECT_REPOS`: репозитории объявляются только в
  settings; блок `repositories {}` в `app/build.gradle.kts` приведёт к ошибке сборки.

## Архитектура / точки входа
- `MainActivity.kt`: `onCreate` -> композабл `MainScreen()` (`ui/MainScreen.kt`); объект `WIN`
  в `math/WIN.kt` — общая конфигурация области просмотра и камеры, которую использует
  `Math3D` для проекции.
- Всё состояние экрана живёт в `ui/MainViewModel.kt` (`MainUiState` + `StateFlow`):
  `prop`, `scaleX/Y/Z`, `indexFigure`, флаги отображения, `figure`, `scale` (зум), `test`.
  `MainScreen` безсостояный, читает `collectAsState()` и вызывает методы VM (`onPropChange`,
  `onScaleXChange`, `onChangeFigure`, `onTogglePoints`, `onZoom`, `onTransformPoints`, ...).
- `ui/Canvas.kt`: `MyCanvas(state, onTransformPoints, onZoom)` — композабл (не класс),
  получает `CanvasUiState` и колбэки параметрами из `MainScreen` (state hoisting, без зависимости от VM);
  `math/Math3D.kt` проекция и сортировка полигонов (алгоритм художника).
- `structure/FigureFactory.kt` пересоздаёт Figure по параметрам (чистая функция, без Android).
- `math/PointTransformer.kt`: `JniPointTransformer` — единственный мост к `CppFuns`;
  интерфейс подменяется фейком в тестах. Натив мутирует `Point` на месте: `Edge` хранит
  прямые ссылки на те же `Point`, поэтому копирование точек сломало бы отрисовку рёбер.
- Модели в `structure/` (Point/Edge/Polygon/Figure); фигуры в `structure/figures/`
  (Cube/Cylinder/Sphere наследуют Figure).

## Контракт JNI — хрупкое место, прочитайте перед изменениями этих файлов
- `math/CppFuns.kt` в companion `init` вызывает `System.loadLibrary("my3d")`; нативный код —
  `app/src/main/cpp/my3d.cpp`, подключённый через `app/src/main/cpp/CMakeLists.txt`
  (имя библиотеки = имя CMake-проекта `my3d`).
- `my3d.cpp` в `JNI_OnLoad` кэширует `jfieldID` для полей `x`, `y`, `z` класса
  `com/example/my3d/structure/Point` (все типа float `F`). Переименование или
  перенос `Point`, а также изменение имён/типов этих полей молча ломает `transformPoints` —
  ID кэшируются один раз при загрузке, а не ищутся при каждом вызове.
- Экспортируемый символ `Java_com_example_my3d_math_CppFuns_00024Companion_transformPoints`
  кодирует Kotlin-пакет
  и путь к companion. При переименовании/переносе `CppFuns` нужно обновить имя этой C++
  функции.
- `transformPoints` изменяет каждый `Point` на месте; вызывающая сторона передаёт
  `figure.points.toTypedArray()`.
- `CppFuns.mult()` объявлен как `external`, но нативной реализации не имеет (тело C++
  закомментировано) — вызов приведёт к `UnsatisfiedLinkError`. Считайте его мёртвым кодом.

## Общение
- Всегда общаться со мной на русском языке.
