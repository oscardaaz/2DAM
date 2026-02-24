# Ejercicios JPQL — Proyecto Inversa

> Objetivo: practicar JPQL sobre entidades y relaciones (Sede, Departamento, Empleado, EmpleadoDatosProf, Proyecto, ProyectoSede).
> Reglas:
> - Usa **parámetros nombrados** `:param`.
> - Usa **TypedQuery** cuando devuelvas entidades completas.
> - Usa **Query** cuando devuelvas campos sueltos (`List<Object[]>`).
> - Para UPDATE/DELETE: **transacción obligatoria** y `executeUpdate()`.

---

## Bloque A — SELECT básicos (entidades completas)

### 1) Listar todas las sedes
- **Devuelve:** `List<Sede>`
- **Pista:** consulta simple `FROM ...` con alias.

### 2) Listar departamentos de una sede por nombre
- **Entrada:** `nomSede`
- **Devuelve:** `List<Departamento>`
- **Pista:** filtra por `departamento.sede.nomSede = :nomSede`.

### 3) Buscar empleado por DNI
- **Entrada:** `dni`
- **Devuelve:** `Empleado` (un único resultado)
- **Pista:** `WHERE e.dni = :dni` y usa `getSingleResult()`.

### 4) Empleados cuyo nombre contenga un patrón
- **Entrada:** `patron` (ej. `%Ana%`)
- **Devuelve:** `List<Empleado>`
- **Pista:** `LIKE` y ordena por nombre.

---

## Bloque B — Proyecciones (campos concretos)

### 5) Nombre empleado + nombre departamento + nombre sede
- **Devuelve:** `List<Object[]>` (3 columnas)
- **Pista:** selecciona 3 atributos navegando por relaciones desde `Empleado`.

### 6) Nombre empleado + categoría + sueldo
- **Devuelve:** `List<Object[]>`
- **Pista:** parte de `EmpleadoDatosProf` y navega a `empleado.nomEmp`.
- **Extra:** ordena por sueldo descendente.

---

## Bloque C — IN / BETWEEN / ORDER BY

### 7) Empleados con categoría en una lista
- **Entrada:** `categorias` (lista)
- **Devuelve:** `List<EmpleadoDatosProf>` o `List<Object[]>` (si sacas nombre+sueldo)
- **Pista:** `IN :categorias`.

### 8) Empleados con sueldo entre dos valores
- **Entrada:** `min`, `max`
- **Devuelve:** `List<EmpleadoDatosProf>` o `List<Object[]>`
- **Pista:** `BETWEEN :min AND :max`.

---

## Bloque D — Colecciones (SIZE / IS EMPTY)

### 9) Departamentos con número de empleados
- **Devuelve:** `List<Object[]>` (nomDepto, numEmpleados)
- **Pista:** `SIZE(departamento.empleados)`.

### 10) Departamentos sin empleados
- **Devuelve:** `List<Departamento>`
- **Pista:** `departamento.empleados IS EMPTY`.

### 11) Sedes con al menos 2 departamentos
- **Devuelve:** `List<Sede>`
- **Pista:** usa `SIZE(sede.departamentos)` y compara.

---

## Bloque E — GROUP BY (agregaciones)

### 12) Sueldo medio por departamento
- **Devuelve:** `List<Object[]>` (nomDepto, avgSueldo)
- **Pista:** `AVG(...)`, `GROUP BY`, y orden por el AVG.

### 13) Número de empleados por sede
- **Devuelve:** `List<Object[]>` (nomSede, countEmpleados)
- **Pista:** `COUNT(...)` agrupando por sede navegando desde empleado.

### 14) Departamento con sueldo máximo (o top 3)
- **Devuelve:** `List<Object[]>` (nomDepto, maxSueldo) o lista ordenada
- **Pista:** usa `MAX(...)` y `GROUP BY`, o bien ordena por sueldo y limita desde Java.

---

## Bloque F — Subconsultas (nivel medio)

### 15) Empleados con sueldo por encima de la media global
- **Devuelve:** lista (elige si entidad o proyección)
- **Pista:** subconsulta con `AVG(sueldo)` y comparación `>`.

### 16) Empleados cuyo sueldo sea el máximo de su departamento
- **Devuelve:** lista (posible proyección con nombre, depto, sueldo)
- **Pista:** subconsulta correlacionada por departamento.

---

## Bloque G — Proyectos (relación con entidad intermedia)

### 17) Proyectos asociados a una sede (por nombre de sede)
- **Entrada:** `nomSede`
- **Devuelve:** `List<Proyecto>`
- **Pista:** join a la colección de relaciones intermedias y filtra por sede.

### 18) Para cada proyecto, cuántas sedes tiene
- **Devuelve:** `List<Object[]>` (nomProy, numSedes)
- **Pista:** `COUNT(...)` + `GROUP BY` sobre proyecto.

### 19) Proyectos sin sedes asignadas
- **Devuelve:** `List<Proyecto>`
- **Pista:** `IS EMPTY` sobre la colección del proyecto (la relación intermedia).

### 20) Sede con mayor total de empleados asignados a proyectos
- **Devuelve:** `List<Object[]>` o un único resultado
- **Pista:** `SUM(numEmpleadosAsignados)` agrupando por sede y ordenando desc.

---

## Bloque H — UPDATE / DELETE masivos (transacción obligatoria)

### 21) Subir sueldos un % por categoría (UPDATE)
- **Entrada:** `categoria`, `porcentaje`
- **Devuelve:** nº filas actualizadas (int)
- **Pista:** `UPDATE` con multiplicación del sueldo por `(1 + porcentaje/100)`.

### 22) Poner sueldo a NULL a empleados de una sede (UPDATE)
- **Entrada:** `nomSede`
- **Devuelve:** nº filas actualizadas
- **Pista:** update sobre datos prof filtrando por `empleado.departamento.sede.nomSede`.

### 23) Borrado correcto de empleados de un departamento (DELETE en orden)
- **Entrada:** `nomDepto`
- **Devuelve:** nº filas borradas en cada paso
- **Pista:** primero borra `EmpleadoDatosProf` y luego `Empleado` (por la FK).

---

## Bloque I — JOIN FETCH (evitar N+1)

### 24) Cargar empleados con su departamento en una sola consulta
- **Devuelve:** `List<Empleado>`
- **Pista:** `JOIN FETCH` para traer el departamento junto al empleado.

### 25) Cargar sedes con sus departamentos (y opcional: empleados)
- **Devuelve:** `List<Sede>`
- **Pista:** `JOIN FETCH` en cascada y `DISTINCT` para evitar duplicados.

---

## Extra (opcional)

### 26) “Buscador” de empleados (multi-filtro)
- **Entrada:** `nomSede` opcional, `nomDepto` opcional, `minSueldo` opcional
- **Devuelve:** lista de empleados o proyección
- **Pista:** construye JPQL dinámico desde Java (StringBuilder) y añade condiciones según no sean null.
