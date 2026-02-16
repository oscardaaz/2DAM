# guia-plantillas-jpql-inversa-intercalado.md

Guía + plantillas JPA (Inversa) — Formato intercalado  
✅ Sin soluciones JPQL (solo huecos `/* JPQL */`)

---

## 1) Helper para imprimir `Object[]` (proyecciones)

Cuando tu JPQL selecciona campos sueltos (nombre, sede, avg, count…), el resultado suele ser `List<Object[]>`.  
Este helper te imprime una fila sin pelearte con índices.

```java
private void printRow(Object[] row) {
    for (int i = 0; i < row.length; i++) {
        System.out.print((i == 0 ? "" : " | ") + row[i]);
    }
    System.out.println();
}
```

## 2) SELECT que devuelve lista de entidades (TypedQuery)

Úsalo cuando tu JPQL devuelve entidades completas: Sede, Departamento, Empleado, Proyecto, EmpleadoDatosProf.
Devuelve List<T> con getResultList().

Nota: aquí uso un método genérico (Java válido) para que el bloque ```java se coloree bien.

```java
public <T> void tpl_selectListaEntidades(Class<T> tipo) {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL: devuelve entidad completa */";
        TypedQuery<T> q = em.createQuery(jpql, tipo);

        // Parámetros (si hay)
        // q.setParameter("param", valor);

        List<T> lista = q.getResultList();
        for (T x : lista) {
            System.out.println(x);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_selectListaEntidades: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 3) SELECT que devuelve 1 entidad (getSingleResult)

Úsalo cuando el resultado debe ser único (por ejemplo, búsqueda por DNI/PK).
Incluye control de NoResultException y NonUniqueResultException.

```java
public <T> void tpl_selectEntidadUnica(Class<T> tipo /*, params */) {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL: 1 resultado */";
        TypedQuery<T> q = em.createQuery(jpql, tipo);

        // q.setParameter("param", valor);

        T result = q.getSingleResult();
        System.out.println(result);

    } catch (jakarta.persistence.NoResultException e) {
        System.out.println("No hay resultados.");
    } catch (jakarta.persistence.NonUniqueResultException e) {
        System.out.println("Hay más de un resultado (no es único).");
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_selectEntidadUnica: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 4) SELECT de proyecciones (Query + List<Object[]>)

Úsalo cuando seleccionas campos concretos (por ejemplo: nomEmp, nomDepto, nomSede).
Devuelve List<Object[]>.

```java
public void tpl_selectProyeccion() {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL: SELECT campo1, campo2, ... */";
        Query q = em.createQuery(jpql);

        // q.setParameter("param", valor);

        @SuppressWarnings("unchecked")
        List<Object[]> filas = q.getResultList();

        for (Object[] row : filas) {
            printRow(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_selectProyeccion: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 5) SELECT agregado único (COUNT/AVG/SUM sin GROUP BY)

Cuando solo necesitas un valor (ej. contar empleados), usa getSingleResult().
Suele devolverte un Long, Double, BigDecimal… según lo que agregues y tu mapeo.

```java
public void tpl_selectAgregadoUnico() {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL: SELECT COUNT(...) / AVG(...) / SUM(...) ... */";
        Query q = em.createQuery(jpql);

        // q.setParameter("param", valor);

        Object valor = q.getSingleResult();
        System.out.println("Agregado = " + valor);

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_selectAgregadoUnico: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 6) SELECT con GROUP BY (devuelve filas Object[])

Cuando agrupas (por sede, por departamento…), normalmente devuelves clave + agregado.
Eso es List<Object[]>.

```java
public void tpl_selectGroupBy() {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL: SELECT clave, AGG(...) ... GROUP BY clave */";
        Query q = em.createQuery(jpql);

        // q.setParameter("param", valor);

        @SuppressWarnings("unchecked")
        List<Object[]> filas = q.getResultList();

        for (Object[] row : filas) {
            printRow(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_selectGroupBy: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 7) UPDATE masivo (transacción + executeUpdate())

Para UPDATE masivos:

Transacción obligatoria

executeUpdate() devuelve nº de filas afectadas

```java
public void tpl_updateMasivo(/* params */) {
EntityManager em = null;
EntityTransaction tx = null;

    try {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        String jpql = "/* JPQL UPDATE ... */";
        Query q = em.createQuery(jpql);

        // q.setParameter("param", valor);

        int filas = q.executeUpdate();
        tx.commit();

        System.out.println("Filas actualizadas: " + filas);

    } catch (Exception e) {
        e.printStackTrace();
        if (tx != null && tx.isActive()) tx.rollback();
        System.out.println("Error en tpl_updateMasivo: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 8) DELETE masivo (transacción + executeUpdate())

Para DELETE masivos:

Transacción obligatoria

executeUpdate() devuelve nº de filas borradas

```java
public void tpl_deleteMasivo(/* params */) {
EntityManager em = null;
EntityTransaction tx = null;

    try {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        String jpql = "/* JPQL DELETE ... */";
        Query q = em.createQuery(jpql);

        // q.setParameter("param", valor);

        int filas = q.executeUpdate();
        tx.commit();

        System.out.println("Filas borradas: " + filas);

    } catch (Exception e) {
        e.printStackTrace();
        if (tx != null && tx.isActive()) tx.rollback();
        System.out.println("Error en tpl_deleteMasivo: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 9) DELETE en orden (dependientes → principal)

Si hay FK (por ejemplo: datos profesionales dependen de empleado), borra en 2 pasos:

dependientes

principal

```java
public void tpl_deleteEnOrden(/* params */) {
EntityManager em = null;
EntityTransaction tx = null;

    try {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        String jpql1 = "/* DELETE dependientes */";
        String jpql2 = "/* DELETE principal */";

        Query q1 = em.createQuery(jpql1);
        Query q2 = em.createQuery(jpql2);

        // q1.setParameter("param", valor);
        // q2.setParameter("param", valor);

        int depBorrados = q1.executeUpdate();
        int prinBorrados = q2.executeUpdate();

        tx.commit();

        System.out.println("Dependientes borrados: " + depBorrados);
        System.out.println("Principales borrados: " + prinBorrados);

    } catch (Exception e) {
        e.printStackTrace();
        if (tx != null && tx.isActive()) tx.rollback();
        System.out.println("Error en tpl_deleteEnOrden: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 10) JOIN FETCH (evitar N+1)

Si vas a listar entidades y luego imprimir datos de relaciones (departamento, sede, etc.),
usa JOIN FETCH para evitar que se disparen queries extra al iterar.

Nota: uso método genérico (Java válido) para mantener el resaltado.

```java
public <T> void tpl_joinFetch(Class<T> tipo) {
EntityManager em = null;
try {
em = emf.createEntityManager();

        String jpql = "/* JPQL con JOIN FETCH ... (y DISTINCT si hace falta) */";
        TypedQuery<T> q = em.createQuery(jpql, tipo);

        List<T> lista = q.getResultList();
        for (T x : lista) {
            System.out.println(x);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_joinFetch: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```

## 11) JPQL dinámico (filtros opcionales)

Útil si haces un “buscador” (filtros opcionales).
Construyes la query con StringBuilder y solo añades condiciones si hay valor.

```java
public void tpl_queryDinamica(String nomSede, String nomDepto, Double minSueldo) {
EntityManager em = null;
try {
em = emf.createEntityManager();

        StringBuilder jpql = new StringBuilder();
        jpql.append("/* FROM ... WHERE 1=1 */");

        if (nomSede != null && !nomSede.isBlank()) {
            jpql.append(" /* AND ... = :nomSede */");
        }
        if (nomDepto != null && !nomDepto.isBlank()) {
            jpql.append(" /* AND ... = :nomDepto */");
        }
        if (minSueldo != null) {
            jpql.append(" /* AND ... >= :minSueldo */");
        }

        // Decide si devuelves entidad o proyección
        Query q = em.createQuery(jpql.toString());

        if (nomSede != null && !nomSede.isBlank()) q.setParameter("nomSede", nomSede);
        if (nomDepto != null && !nomDepto.isBlank()) q.setParameter("nomDepto", nomDepto);
        if (minSueldo != null) q.setParameter("minSueldo", minSueldo);

        @SuppressWarnings("unchecked")
        List<Object[]> filas = q.getResultList();

        for (Object[] row : filas) {
            printRow(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error en tpl_queryDinamica: " + e.getMessage());
    } finally {
        if (em != null) em.close();
    }
}
```
## FIN DEL ARCHIVO... CONTINUARA...