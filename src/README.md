## Спецификация API

Базовый URL: `/api/tasks`

### Сводная таблица эндпоинтов

| Метод    | Эндпоинт                   | Описание                                           | Успешный ответ   |
|:---------|:---------------------------|:---------------------------------------------------|:-----------------|
| `POST`   | `/api/tasks`               | Создать новую задачу                               | `201 Created`    |
| `GET`    | `/api/tasks`               | Получить список задач (с опциональной фильтрацией) | `200 OK`         |
| `GET`    | `/api/tasks/statistics`    | Получить статистику по задачам                     | `200 OK`         |
| `GET`    | `/api/tasks/{id}`          | Получить задачу по ID                              | `200 OK`         |
| `POST`   | `/api/tasks/{id}/tags`     | Добавить тег к задаче                              | `200 OK`         |
| `PATCH`  | `/api/tasks/{id}/status`   | Обновить статус задачи                             | `200 OK`         |
| `PATCH`  | `/api/tasks/{id}/priority` | Обновить приоритет задачи                          | `200 OK`         |
| `DELETE` | `/api/tasks/{id}`          | Удалить задачу по ID                               | `204 No Content` |

---
## Архитектура и поток данных (Data Flow)

Документация вызовов по слоям: `Endpoint` -> `Controller` -> `Service` -> `Repository`.

---

### 1. Получение всех задач
`GET /api/tasks` -> `TaskController.getAllTasks()` -> `TaskService.findAll()` -> `TaskRepository.findAll()`

### 2. Получение задачи по ID
`GET /api/tasks/{id}` -> `TaskController.getById(id)` -> `TaskService.findById(id)` -> `TaskRepository.findById(id)`

### 3. Создание новой задачи
`POST /api/tasks` -> `TaskController.create(request)` -> `TaskService.create(request)` -> `TaskRepository.save(...)`

### 4. Обновление статуса
`PATCH /api/tasks/{id}/status` -> `TaskController.updateStatus(id, request)` -> `TaskService.updateStatus(id, request)` -> `TaskRepository.findById(id)`

### 5. Обновление приоритета
`PATCH /api/tasks/{id}/priority` -> `TaskController.updatePriority(id, request)` -> `TaskService.updatePriority(id, request)` -> `TaskRepository.findById(id)`

### 6. Удаление задачи
`DELETE /api/tasks/{id}` -> `TaskController.deleteById(id)` -> `TaskService.deleteById(id)` -> `TaskRepository.deleteById(id)`
> ⚠️ **Ограничение хранения данных:**
> Приложение использует **In-Memory** репозиторий (`ConcurrentHashMap` / `ArrayList`). Все данные хранятся в оперативной памяти и сбрасываются при перезапуске сервера.