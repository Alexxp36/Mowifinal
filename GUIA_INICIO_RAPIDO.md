# 🚀 Guía de Inicio Rápido - MowiMarket

Esta guía te ayudará a iniciar todos los servidores correctamente, tomando en cuenta los errores que tuviste anteriormente.

## 📋 Pre-requisitos

- ✅ XAMPP instalado
- ✅ Python 3.x instalado
- ✅ Java 17 instalado
- ✅ Base de datos `mowi_store` importada en MySQL

---

## 🔴 PASO 1: Iniciar MySQL en XAMPP

**⚠️ IMPORTANTE: Hazlo primero, si no Django no conectará**

1. Abre el Panel de Control de XAMPP
2. Inicia el servicio MySQL (botón "Start")
3. Verifica que aparezca en verde

```bash
# Para verificar que MySQL está corriendo en Windows:
# Abre XAMPP Control Panel y verifica que MySQL esté en verde
```

---

## 🐍 PASO 2: Iniciar Django API (Puerto 8000)

### Navegar al directorio de Django:

```bash
cd server/django_api
```

### Activar el entorno virtual (si lo tienes):

```bash
# Windows
venv\Scripts\activate

# Si no tienes venv, sáltate este paso
```

### Verificar versión de Django (debe ser 4.2):

```bash
python -c "import django; print(django.get_version())"
```

**Si sale 5.x o da error:**

```bash
pip install "Django>=4.2,<5.0"
```

### Iniciar el servidor Django:

```bash
python manage.py runserver
```

✅ **Debe mostrar:**
```
Starting development server at http://127.0.0.1:8000/
```

**Si da error de conexión a MySQL:**
- Verifica que MySQL esté corriendo en XAMPP (PASO 1)
- Verifica que la base de datos `mowi_store` exista

**Deja esta terminal abierta** y abre una nueva para el siguiente paso.

---

## ☕ PASO 3: Iniciar Spring Boot API (Puerto 8080)

### Abrir nueva terminal y navegar a Spring Boot:

```bash
cd server/sboot_api
```

### Iniciar el servidor con Maven (Windows):

```bash
mvnw.cmd spring-boot:run
```

### O con Maven (Linux/Mac):

```bash
./mvnw spring-boot:run
```

✅ **Debe mostrar:**
```
Started Application in X seconds
Tomcat started on port(s): 8080
```

**Si da error:**
- Verifica que MySQL esté corriendo en XAMPP
- Verifica que Java 17 esté instalado: `java -version`

**Deja esta terminal abierta**.

---

## ✅ PASO 4: Verificar que todo funciona

### Django API (Admin):
Abre en el navegador: http://localhost:8000/api/usuarios/

### Spring Boot API (Clientes):
Abre en el navegador: http://localhost:8080/api/productos

**Deberías ver JSON con datos de la base de datos.**

---

## 📱 PASO 5: Ejecutar la App Android

1. Abre Android Studio
2. Abre el proyecto: `Desktop/Mowimarket/1/aplicacion_android`
3. Espera a que sincronice Gradle
4. Ejecuta en el emulador

**La app debe conectar automáticamente a:**
- Django API: `http://10.0.2.2:8000` (10.0.2.2 = localhost en emulador)
- Spring Boot API: `http://10.0.2.2:8080`

---

## 🛑 Para detener los servidores:

- **Django**: `Ctrl + C` en la terminal donde corre
- **Spring Boot**: `Ctrl + C` en la terminal donde corre
- **MySQL**: Botón "Stop" en XAMPP Control Panel

---

## 🔧 Solución a Errores Comunes

### Error: "Can't connect to MySQL server"
**Causa:** MySQL no está corriendo
**Solución:** Inicia MySQL en XAMPP Control Panel

### Error: "MariaDB 10.5 or later is required"
**Causa:** Versión de Django incorrecta
**Solución:**
```bash
pip install "Django>=4.2,<5.0"
```

### Error: "Port 8000 is already in use"
**Causa:** Django ya está corriendo
**Solución:**
1. Cierra la terminal anterior con Ctrl+C
2. O usa otro puerto: `python manage.py runserver 8001`

### Error: "Port 8080 is already in use"
**Causa:** Spring Boot ya está corriendo
**Solución:**
1. Cierra la terminal anterior con Ctrl+C
2. Encuentra y mata el proceso:
   - Windows: `netstat -ano | findstr :8080` → `taskkill /PID <número> /F`

---

## 📝 Credenciales de Prueba

### Usuario Admin:
- Email: `admin@mowi.com`
- Password: `admin123`

### Usuario Cliente:
- Email: `cliente@mowi.com`
- Password: `cliente123`

---

## 🎯 Resumen del Orden de Inicio

1. ✅ MySQL (XAMPP)
2. ✅ Django API (`python manage.py runserver`)
3. ✅ Spring Boot API (`mvnw.cmd spring-boot:run`)
4. ✅ App Android (desde Android Studio)

**¡Todo listo!** 🎉
