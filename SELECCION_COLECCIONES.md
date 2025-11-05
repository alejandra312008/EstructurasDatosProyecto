# PARTE A — Selección y modelado con colecciones

## Diagrama de selección: Entidad → Colección

| Entidad | Colección elegida | Justificación técnica |
|---------|------------------|----------------------|
| **Rutas encontradas** | `ArrayList<PathInfo>` | Acceso aleatorio rápido O(1) por índice. Inserciones al final frecuentes O(1) amortizado. Iteración eficiente. |
| **Celdas visitadas (unicidad)** | `HashSet<Point>` | Búsqueda de existencia O(1) amortizado. Evita duplicados automáticamente. No requiere orden. |
| **Índice de rutas por ID** | `HashMap<String, PathInfo>` | Acceso O(1) amortizado por clave. Búsqueda rápida de rutas por identificador. Reemplaza búsqueda lineal O(n). |
| **Agrupación por longitud** | `TreeMap<Integer, List<PathInfo>>` | Orden natural por longitud. Acceso O(log n) para consultas por rango. Iteración ordenada eficiente. |
| **Historial de visitas ordenado** | `LinkedHashSet<Point>` | Conserva orden de inserción. Unicidad garantizada. Búsqueda O(1). Útil para mostrar orden de exploración. |

## Justificación detallada por categoría

### 1. LISTAS

#### ArrayList
- **Uso**: Almacenar todas las rutas encontradas (`List<PathInfo>`)
- **Razón**: 
  - Acceso aleatorio por índice: O(1)
  - Inserción al final: O(1) amortizado
  - Iteración eficiente (mejor cache locality)
  - Tamaño conocido previamente en mayoría de casos
- **Alternativa rechazada**: `LinkedList`
  - Acceso aleatorio: O(n)
  - Mayor overhead de memoria (nodos)
  - No necesitamos inserciones frecuentes en medio

### 2. CONJUNTOS

#### HashSet
- **Uso**: Conjunto de celdas visitadas para evitar duplicados
- **Razón**:
  - Búsqueda de existencia: O(1) amortizado
  - Operaciones `add()`, `contains()`, `remove()`: O(1)
  - No requiere orden específico
- **Alternativa rechazada**: `TreeSet`
  - Búsqueda: O(log n) vs O(1)
  - Overhead adicional de ordenamiento no necesario

#### LinkedHashSet (opcional)
- **Uso**: Historial de visitas cuando se necesita orden de inserción
- **Razón**:
  - Conserva orden de inserción
  - Búsqueda O(1) como HashSet
  - Útil para visualización de orden de exploración

### 3. MAPAS

#### HashMap
- **Uso**: Índice `id → PathInfo` para búsqueda rápida
- **Razón**:
  - Acceso por clave: O(1) amortizado
  - Reemplaza búsqueda lineal O(n) en `ArrayList`
  - No requiere orden de claves
- **Impacto**: Búsqueda de ruta por ID ahora es O(1) vs O(n) anterior

#### TreeMap
- **Uso**: Agrupación de rutas por longitud
- **Razón**:
  - Orden natural por longitud (clave Integer)
  - Acceso O(log n) para consultas por rango
  - Iteración ordenada eficiente
  - Permite consultas como "rutas con longitud entre X e Y"

#### LinkedHashMap (alternativa para cache LRU)
- **Uso**: Cache de rutas más recientes (no implementado en este caso)
- **Razón**: Conserva orden de inserción, útil para políticas LRU

## Ventajas de la selección

### Antes (sin índices):
```java
// Búsqueda lineal O(n)
PathInfo findPath(String id) {
    for (PathInfo path : allPaths) {
        if (path.getId().equals(id)) return path;
    }
    return null;
}
```

### Después (con HashMap):
```java
// Búsqueda indexada O(1)
PathInfo findPath(String id) {
    return pathIndex.get(id); // O(1)
}
```

## Complejidad comparativa

| Operación | Sin índice (List) | Con índice (Map) | Mejora |
|-----------|------------------|------------------|--------|
| Buscar ruta por ID | O(n) | O(1) | n veces más rápido |
| Verificar celda visitada | O(n) | O(1) | n veces más rápido |
| Agrupar por longitud | O(n log n) | O(n) | log n veces más rápido |

## Casos de uso específicos

1. **Búsqueda frecuente de rutas por ID**: `HashMap` obligatorio
2. **Evitar celdas duplicadas en BFS**: `HashSet` esencial
3. **Mostrar rutas ordenadas por longitud**: `TreeMap` para agrupación
4. **Acceso aleatorio a rutas por índice**: `ArrayList` eficiente

## Criterios de elección aplicados

1. **Patrón de acceso**: 
   - Lectura intensiva → `ArrayList`
   - Búsqueda por clave → `HashMap`/`HashSet`
   - Orden requerido → `TreeMap`/`TreeSet`

2. **Operaciones frecuentes**:
   - `contains()` → Set/Map
   - `get(index)` → ArrayList
   - `put(key, value)` → HashMap

3. **Tamaño de datos**:
   - Pequeño (< 1000): Cualquiera funciona
   - Medio (10^4-10^5): Elegir según operación más frecuente
   - Grande (> 10^5): HashMap/HashSet crítico

4. **Requirement de orden**:
   - No → HashSet/HashMap
   - Sí → TreeSet/TreeMap o LinkedHashSet/LinkedHashMap
