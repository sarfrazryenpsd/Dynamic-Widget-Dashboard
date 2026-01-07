# Dynamic Widget Dashboard

This project implements a dynamic dashboard rendered entirely from widget metadata.  
The dashboard supports multiple widget types, each of which may appear multiple times and with distinct state, identified by an `instanceId`.

Technologies used:

- Kotlin
- Jetpack Compose
- MVVM
- StateFlow
- Hilt Dependency Injection

---

## Widget identity and instance handling

Each widget on the dashboard is defined through metadata containing:

- `type` – widget type (`BANNER`, `LIST`, `UNSUPPORTED`)
- `instanceId` – globally unique identity per widget instance

---


### How identity is handled

- `DashboardViewModel` loads metadata only
- widgets are rendered strictly in the order provided by metadata
- the dashboard does not know widget internals

Every widget instance is hosted as:


### Per-instance ViewModel

Each widget instance gets its own ViewModel using a unique key:

hiltViewModel(key = instanceId)


This guarantees:

- no shared state between widget instances
- repeated widget types behave independently
- safe reordering or duplication of widgets

---

## Banner widget and how it scales

The banner widget itself is stateless.

- no business logic
- no repository usage
- receives only pre-mapped UI data

`BannerWidgetHost` is responsible for:

- owning the `BannerViewModel`
- triggering data load using `instanceId`
- collecting state
- rendering loading / error / content

`BannerWidget` only:

- receives `List<Banner>`
- renders a horizontally scrolling list

Scaling properties:

- supports single or multiple banners visually
- supports multiple banner widget instances
- supports repeating the same widget type in metadata

Example cases handled safely:

banner(pokemon)

banner(cars)

banner(pokemon) again


Each instance remains functionally isolated.

---

## List widget scaling and state model

The list widget supports:

- loading state
- success state
- empty state
- error state
- per-instance retry behavior


### How list widget scales

- every list instance has its own ViewModel
- instance identity is passed via `instanceId`
- loading is performed once per instance
- recomposition does not refetch data

Multiple independent lists are supported:

list(movies)

list(shows)

list(movies) again


Each maintains isolated state.

---

## How list widget state works internally

Execution flow:

1. `ListWidgetHost` receives `instanceId`
2. `LaunchedEffect(instanceId)` triggers data load once
3. ViewModel calls repository function
4. repository returns static or simulated API data
5. ViewModel exposes `StateFlow<ListState>`
6. Compose collects it and renders:

- loading view
- list content
- empty placeholder
- error message with retry

Because the state is owned by the ViewModel rather than the composable, it survives recompositions and configuration changes.

---

## Extending to new widget types

To add a new widget type:

1. add a new value to `WidgetType`
2. implement repository and ViewModel for the new widget
3. create `<NewType>WidgetHost` and `<NewType>Widget`
4. handle the type in the `DashboardScreen` `when` branch

The dashboard itself remains unchanged, because layout is driven entirely by metadata.

---

## What I would improve next

If extended further, the next improvements would be:

1. Replace static data with real network APIs
    - Retrofit
    - kotlinx.serialization or Moshi
    - make repositories source-agnostic

2. Add caching and offline mode
    - Room database
    - cache per `instanceId`
    - define refresh strategies

3. UI and UX enhancements
    - animated state transitions
    - skeleton loading placeholders
    - accessibility and TalkBack support

4. Improve error modeling
    - domain-specific error types
    - telemetry or logging integration

5. Move toward full server-driven UI
    - dynamic layout configuration
    - remotely controlled widget ordering
    - conditional widget visibility

---

## Summary

This project demonstrates:

- metadata-driven dynamic dashboard
- clear per-widget identity via `instanceId`
- ViewModel state scoped per widget instance
- stateless banner widgets
- list widgets with loading, error, empty and success states
- a clean MVVM architecture with Hilt and Jetpack Compose  








