# Dominio y Arquitectura de Software

## 1. Patrón Arquitectónico: Arquitectura Hexagonal (Ports & Adapters)

El proyecto sigue una estructura limpia desacoplando la lógica de negocio del framework y los detalles de infraestructura:

* `domain`: Contiene las reglas puras del negocio, entidades de dominio, enums y puertos de salida (interfaces).
* `application`: Casos de uso, servicios e interfaces de transporte (DTOs).
* `infrastructure`: Controladores REST, JPA Entities, Repositorios Spring Data, MapStruct/Mapeadores y Global Exception Handler.

---

## 2. Reglas del Modelo de Datos (Inglés)

1. **Category (`categories`)**: Catálogo maestro. Mantiene el `prefixCode` (3 caracteres) obligatorio para la generación de folios.
2. **Asset (`assets`)**: Entidad principal de inventario.
   * `folio`: Código autogenerado bajo la regla `PREFIX-YYYY-XXXX` (ejemplo: `LAP-2026-0001`).
   * `serialNumber`: Único a nivel base de datos.
3. **AssetHistory (`asset_history`)**: Bitácora e historial de auditoría de transiciones de estado (`previousStatus` -> `newStatus`). Registra la fecha, el usuario que realizó la acción (`changedBy`) y la justificación.
4. **AssetStatus**: Enum con valores `AVAILABLE`, `ASSIGNED`, `MAINTENANCE`, `RETIRED`.

---

## 3. Manejo de Errores e Idiomas

* **Código de Fuente:** Totalmente en **Inglés**.
* **Respuestas de API REST:** Formato uniforme JSON con mensajes claros en **Español**.