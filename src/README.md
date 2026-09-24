# Архитектура и поток данных (Data Flow)

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