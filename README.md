# Sistema de Inventario de Activos Tecnológicos

Módulo backend para la gestión, control y auditoría de activos tecnológicos institucionales.

## Stack Tecnológico

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.3.5 (Spring Data JPA, Spring Security, Validation)
* **Base de Datos:** Oracle Database 23c/26ai (vía Docker)
* **Gestor de Dependencias:** Maven

---

## Requisitos Previos

* **JDK 17** o superior instalo y configurado.
* **Docker** y **Docker Compose** en ejecución.

---

## Instrucciones para Levantar el Entorno Local

### 1. Iniciar la Base de Datos Oracle

Ejecuta el siguiente comando para iniciar la instancia de Oracle DB en segundo plano:

```bash
docker compose up -d