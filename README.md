

# 📝 Excel Max Finder

### 📌 **Описание**
Этот сервис на **Spring Boot** позволяет находить `N`-й максимум в **XLSX-файлах**.  
Пользователь **просто добавляет нужные `.xlsx` файлы** в папку `files/`, а затем запускает контейнер.

---

## 🚀 **Запуск проекта**

### **1️⃣ Клонирование репозитория**
```sh
git clone https://github.com/AlinaMoroz/excel-max-finder.git
```

### **2️⃣ Добавление файлов для тестирования**
Перед запуском просто скопируйте нужные файлы в папку `files/`:
```sh
mkdir -p files
cp your_file.xlsx files/
```
🔹 **Важно:** Файл должен быть в формате `.xlsx`.

### **3️⃣ Сборка проекта**
```sh
./gradlew build
```

### **4️⃣ Сборка Docker-образа**
```sh
docker build -t excel-max-finder .
```

### **5️⃣ Запуск контейнера**
```sh
docker run -p 8080:8080 excel-max-finder
```
После запуска контейнера API доступно по адресу:  
📌 **http://localhost:8080/swagger-ui/index.html**

---

## 📡 **Использование API**
После запуска контейнера, можно протестировать API через **Swagger**:  
📌 **http://localhost:8080/swagger-ui/index.html**

### **📌 Найти N-й максимум в файле**
🔹 **GET** `/find-max-n`  
**Параметры запроса:**
- `filePath` — путь к файлу в контейнере **(указывать просто имя файла, например, `test.xlsx`)**.
- `n` — какое по величине число найти.

📌 **Пример запроса в Swagger:**
```sh
GET /find-max-n?filePath=test.xlsx&n=3
```


## 📌 **Дополнительная информация**
- **Java:** 21
- **Framework:** Spring Boot
- **Swagger:** Включен (`http://localhost:8080/swagger-ui/index.html`)
- **Файлы для тестирования** добавляются в `files/` перед запуском.
