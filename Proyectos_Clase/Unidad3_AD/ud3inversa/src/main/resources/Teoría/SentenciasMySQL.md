# guia-mysql-inversa.md

Guía rápida MySQL para el proyecto **inversa**  
(consultas útiles para comprobar datos y depurar)

---

## 0) Entrar y seleccionar BD

```mysql
SHOW DATABASES;
USE inversa;
SHOW TABLES;
```

## 1) Ver estructura y claves
```sql
DESCRIBE sede;
DESCRIBE departamento;
DESCRIBE empleado;
DESCRIBE empleado_datos_prof;
DESCRIBE proyecto;
DESCRIBE proyecto_sede;
Ver claves foráneas (según motor / versión):
SELECT
  TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'inversa' AND REFERENCED_TABLE_NAME IS NOT NULL;
```

## 2) Consultas básicas (SELECT *)

```sql
SELECT * FROM sede;
SELECT * FROM departamento;
SELECT * FROM empleado;
SELECT * FROM empleado_datos_prof;
SELECT * FROM proyecto;
SELECT * FROM proyecto_sede;
```

## 3) JOINs típicos del modelo

### 3.1 Empleados con su departamento y sede

```sql
SELECT e.dni, e.nom_emp, d.nom_depto, s.nom_sede
FROM empleado e
JOIN departamento d ON e.id_depto = d.id_depto
JOIN sede s ON d.id_sede = s.id_sede
ORDER BY s.nom_sede, d.nom_depto, e.nom_emp;
```

### 3.2 Empleados con categoría y sueldo

```sql
SELECT e.dni, e.nom_emp, edp.categoria, edp.sueldo_bruto_anual
FROM empleado e
JOIN empleado_datos_prof edp ON edp.dni = e.dni
ORDER BY edp.sueldo_bruto_anual DESC;
```
### 3.3 Departamentos y nº de empleados

```sql
SELECT d.nom_depto, COUNT(e.dni) AS num_empleados
FROM departamento d
LEFT JOIN empleado e ON e.id_depto = d.id_depto
GROUP BY d.id_depto, d.nom_depto
ORDER BY num_empleados DESC;
```

### 3.4 Departamentos sin empleados

```sql
SELECT d.*
FROM departamento d
LEFT JOIN empleado e ON e.id_depto = d.id_depto
WHERE e.dni IS NULL;
```

## 4) Proyectos y sedes

### 4.1 Proyectos con sus sedes
```sql
SELECT p.nom_proy, s.nom_sede, ps.f_inicio, ps.f_fin
FROM proyecto p
JOIN proyecto_sede ps ON ps.id_proy = p.id_proy
JOIN sede s ON s.id_sede = ps.id_sede
ORDER BY p.id_proy, s.nom_sede;
```

### 4.2 Proyectos sin sede asignada
```sql
SELECT p.*
FROM proyecto p
LEFT JOIN proyecto_sede ps ON ps.id_proy = p.id_proy
WHERE ps.id_proy IS NULL;
```

### 4.3 Nº de sedes por proyecto
```sql
SELECT p.nom_proy, COUNT(ps.id_sede) AS num_sedes
FROM proyecto p
LEFT JOIN proyecto_sede ps ON ps.id_proy = p.id_proy
GROUP BY p.id_proy, p.nom_proy
ORDER BY num_sedes DESC;
```

## 5) Filtros típicos (LIKE / BETWEEN / IN)

### 5.1 Buscar empleado por patrón de nombre
```sql
SELECT * FROM empleado
WHERE nom_emp LIKE '%Ana%';
```

### 5.2 Sueldos entre dos valores
```sql
SELECT * FROM empleado_datos_prof
WHERE sueldo_bruto_anual BETWEEN 35000 AND 45000
ORDER BY sueldo_bruto_anual;
```

### 5.3 Categorías en una lista
```sql
SELECT * FROM empleado_datos_prof
WHERE categoria IN ('A1', 'A2', 'B1');
```

## 6) Agregaciones (AVG / MAX / MIN)

### 6.1 Sueldo medio global
```sql
SELECT AVG(sueldo_bruto_anual) AS sueldo_medio
FROM empleado_datos_prof;
```

### 6.2 Sueldo medio por departamento
```sql
SELECT d.nom_depto, AVG(edp.sueldo_bruto_anual) AS sueldo_medio
FROM empleado e
JOIN departamento d ON d.id_depto = e.id_depto
JOIN empleado_datos_prof edp ON edp.dni = e.dni
GROUP BY d.id_depto, d.nom_depto
ORDER BY sueldo_medio DESC;
```

### 7) UPDATE / DELETE (ojo con FKs)

### 7.1 Subir sueldo un % por categoría
```sql
UPDATE empleado_datos_prof
SET sueldo_bruto_anual = sueldo_bruto_anual * (1 + 10/100)
WHERE categoria = 'B1';
```
### 7.2 Borrado “en orden” (primero dependiente, luego principal)
#### Ejemplo: borrar un empleado por DNI:

```sql
DELETE FROM empleado_datos_prof WHERE dni = '12345678A';
DELETE FROM empleado WHERE dni = '12345678A';
```

## 8) Trucos de depuración

### 8.1 Contar filas rápido
```sql
SELECT 'sede' AS tabla, COUNT(*) AS filas FROM sede
UNION ALL
SELECT 'departamento', COUNT(*) FROM departamento
UNION ALL
SELECT 'empleado', COUNT(*) FROM empleado
UNION ALL
SELECT 'empleado_datos_prof', COUNT(*) FROM empleado_datos_prof
UNION ALL
SELECT 'proyecto', COUNT(*) FROM proyecto
UNION ALL
SELECT 'proyecto_sede', COUNT(*) FROM proyecto_sede;
```

### 8.2 Ver empleados “sin datos prof” (si existieran)
```sql
SELECT e.*
FROM empleado e
LEFT JOIN empleado_datos_prof edp ON edp.dni = e.dni
WHERE edp.dni IS NULL;
```