# guia-jpql-inversa.md

Guía rápida para resolver los ejercicios JPQL del proyecto **Inversa**  
(sin soluciones completas)

---

## 1) Qué devuelve tu consulta y qué tipo de Query usar

### A) Si devuelves **entidades completas**
Ejemplos: `Sede`, `Departamento`, `Empleado`, `Proyecto`, `EmpleadoDatosProf`

✅ Usa **TypedQuery<T>**
- Devuelve: `List<T>` con `getResultList()`
- O `T` con `getSingleResult()` si esperas 1 resultado

**Cuándo:** ejercicios tipo “listar X”, “buscar por id/dni”, “filtrar y devolver entidades”.

---

### B) Si devuelves **campos sueltos** (proyección)
Ejemplos: nombre + sede, sueldo medio por depto, conteos…

✅ Usa **Query** (o TypedQuery<Object[]> si lo usáis, pero normalmente Query)
- Devuelve: `List<Object[]>`
- Cada fila es un `Object[]` con los valores **en el mismo orden** del SELECT

**Cuándo:** ejercicios tipo “sacar nombre + depto + sede”, “AVG/COUNT/SUM”, etc.

> Tip: para imprimir, recorre `for (Object[] fila : lista)` y accede `fila[0]`, `fila[1]`…

---

### C) Si devuelves **un valor agregado** (un número)
Ejemplos: `COUNT`, `AVG`, `SUM`, `MAX`, `MIN` sin agrupar

✅ Puedes usar:
- `TypedQuery<Long>` para `COUNT`
- `TypedQuery<Double>` para `AVG`
- `TypedQuery<BigDecimal>` si trabajas con `SUM`/`AVG` de `BigDecimal`

**Opción práctica:** `Query` y castear el `getSingleResult()`.

---

## 2) getResultList vs getSingleResult (y el “por qué peta”)

### getResultList()
- Devuelve lista (vacía si no hay resultados)
- Nunca lanza excepción por “no encontrado”

### getSingleResult()
- Espera exactamente 1 fila
- Lanza:
    - `NoResultException` si no hay ninguna
    - `NonUniqueResultException` si hay más de una

✅ Úsalo solo cuando sea lógico que haya 1 (ej. buscar por PK/DNI).

---

## 3) Parámetros (lo recomendado)

### Parámetros nombrados
- Se escriben como `:nombre`
- Se asignan con `setParameter("nombre", valor)`

✅ Recomendación: siempre parámetros nombrados, nada de concatenar strings.

---

## 4) Orden típico de dificultad (y qué estás practicando)

1. `FROM Entidad` (listar todo)
2. `WHERE ...` (filtros)
3. `ORDER BY ...`
4. Proyecciones → `SELECT campo1, campo2`
5. Navegar relaciones → `e.departamento.sede.nomSede`
6. Colecciones → `SIZE(...)`, `IS EMPTY`
7. Agregaciones → `COUNT/AVG/SUM` + `GROUP BY`
8. Subconsultas
9. `JOIN FETCH` para evitar N+1
10. `UPDATE/DELETE` masivos con transacción

---

## 5) Navegación por relaciones (mentalidad JPQL)

JPQL consulta **objetos**, así que en lugar de joins por columnas, navegas:

- `Departamento.sede.nomSede`
- `Empleado.departamento.nomDepto`
- `EmpleadoDatosProf.empleado.nomEmp`

Si tienes una relación (por ejemplo) `Sede -> departamentos`, entonces:
- `SIZE(s.departamentos)`
- `s.departamentos IS EMPTY`

---

## 6) Colecciones: SIZE / IS EMPTY

### SIZE()
- Devuelve un entero con el nº de elementos en una colección
- Ideal para “departamentos con número de empleados”

### IS EMPTY / IS NOT EMPTY
- Filtra por colección vacía/no vacía
- Ideal para “proyectos sin sedes”, “departamentos sin empleados”

---

## 7) GROUP BY: cómo pensar los resultados

Cuando haces un `GROUP BY`, normalmente devuelves proyecciones:
- `SELECT claveAgrupacion, AGG(...)`

Ejemplos típicos:
- Por departamento: `nomDepto, AVG(sueldo)`
- Por sede: `nomSede, COUNT(empleados)`

✅ Resultado habitual: `List<Object[]>`

---

## 8) Subconsultas: patrón mental

- “Mayor que la media”: comparar con `(SELECT AVG(...))`
- “El máximo de su grupo”: comparar con una subconsulta “por departamento/sede” (correlacionada)

Regla práctica:
- Subconsulta devuelve **un valor** (AVG/MAX) o **un conjunto** (IN (SELECT ...))

---

## 9) JOIN vs JOIN FETCH (y el N+1)

### JOIN (normal)
- Sirve para filtrar y combinar, pero no asegura carga de relaciones

### JOIN FETCH
- Trae la relación ya cargada (evita consultas extra al iterar)

✅ Útil cuando haces:
- listar empleados y luego imprimir su departamento
- listar sedes y luego recorrer departamentos

⚠️ Con colecciones, usa `DISTINCT` para evitar duplicados.

---

## 10) UPDATE / DELETE masivos: lo imprescindible

### Requisitos
- Transacción obligatoria: `tx.begin()` → `executeUpdate()` → `tx.commit()`
- `executeUpdate()` devuelve número de filas afectadas

### Importante
- No pasan por el contexto de persistencia (objetos en memoria pueden quedar “desactualizados”)
- No ejecutan cascadas de JPA (si dependes de ellas, ojo)

### Orden de borrado
Si hay FK:
1) borrar dependientes (p.ej. `EmpleadoDatosProf`)
2) borrar principal (p.ej. `Empleado`)

---

## 11) Checklist rápido antes de ejecutar

- ¿Devuelvo entidades? → TypedQuery
- ¿Devuelvo columnas/AGG? → Query y `Object[]`
- ¿Uso parámetros? → `:param` + `setParameter`
- ¿Es UPDATE/DELETE? → transacción + `executeUpdate()`
- ¿Voy a iterar relaciones? → considerar `JOIN FETCH`
- ¿Puede devolver 0 filas? → mejor `getResultList()` que `getSingleResult()`

---

## 12) Qué imprimir en cada caso (práctico)

### Entidades
- imprime `toString()` o campos clave

### Object[]
- Define un formato fijo por ejercicio:
    - `fila[0]` → nombre
    - `fila[1]` → depto
    - `fila[2]` → sede
- No asumas tipos sin revisar:
    - `COUNT` suele ser `Long`
    - `AVG` suele ser `Double`
    - Sueldos pueden ser `Double`/`BigDecimal` según el mapeo
