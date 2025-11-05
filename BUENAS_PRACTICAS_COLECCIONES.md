# PARTE D — Buenas prácticas mínimas

## 1. Inmutabilidad donde aplique

### Vistas de solo lectura
```java
// ✅ BUENO: Exponer vistas inmutables
public List<PathInfo> getAllPaths() {
    return Collections.unmodifiableList(allPaths);
}

public Map<String, PathInfo> getPathIndex() {
    return Collections.unmodifiableMap(pathIndex);
}

// ❌ MALO: Exponer referencias mutables directamente
public List<PathInfo> getAllPaths() {
    return allPaths; // Permite modificación externa
}
```

### Copias defensivas
```java
// ✅ BUENO: Crear copias defensivas en constructores
public PathInfo(String id, List<Point> path) {
    this.id = id;
    this.path = Collections.unmodifiableList(new ArrayList<>(path)); // Copia defensiva
    this.length = path.size();
}

// ❌ MALO: Almacenar referencia directa
this.path = path; // Cambios externos afectan el objeto
```

### Implementación en MatrixPathFinderModern
- ✅ `PathInfo.path`: Lista inmutable usando `Collections.unmodifiableList()`
- ✅ Getters: Retornan vistas inmutables con `Collections.unmodifiableList/Set/Map()`
- ✅ Constructor de `PathInfo`: Crea copia defensiva antes de hacer inmutable

## 2. Tipos genéricos correctos

### ✅ Uso correcto de genéricos
```java
// ✅ BUENO: Tipos genéricos explícitos
private final List<PathInfo> allPaths;
private final Set<Point> visitedCells;
private final Map<String, PathInfo> pathIndex;
private final Map<Integer, List<PathInfo>> pathsByLength;

// ❌ MALO: Uso de tipos crudos (raw types)
private final List allPaths; // Warning: raw type
private final Set visitedCells;
```

### Evitar warnings de compilación
- ✅ Todos los tipos de colecciones usan genéricos: `List<PathInfo>`, `Set<Point>`, `Map<K,V>`
- ✅ Sin uso de tipos crudos (`List`, `Set`, `Map` sin parámetros de tipo)
- ✅ Tipos genéricos anidados correctamente: `Map<Integer, List<PathInfo>>`

## 3. Comparadores consistentes con equals/hashCode

### Point: equals() y hashCode() implementados correctamente
```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Point point = (Point) obj;
    return row == point.row && col == point.col;
}

@Override
public int hashCode() {
    return row * 1000 + col; // Hash consistente con equals
}
```

### PathInfo: equals() y hashCode() basados en ID
```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathInfo pathInfo = (PathInfo) o;
    return Objects.equals(id, pathInfo.id);
}

@Override
public int hashCode() {
    return Objects.hash(id); // Consistente con equals
}
```

### Regla de oro
- ✅ Si `a.equals(b)` → `a.hashCode() == b.hashCode()`
- ✅ Dos objetos iguales según `equals()` tienen el mismo `hashCode()`
- ✅ Esto es **crítico** para `HashSet` y `HashMap` funcionen correctamente

### Comparadores que respetan equals
```java
// ✅ BUENO: Comparador que respeta equals
Comparator.comparingInt(PathInfo::getLength)
    .thenComparing(PathInfo::getTimestamp, Comparator.reverseOrder())

// Si dos PathInfo tienen misma longitud, se ordenan por timestamp
// No viola la consistencia con equals()
```

## 4. Evitar O(n) accidental

### ❌ Operaciones O(n) problemáticas evitadas:

```java
// ❌ MALO: ArrayList.add(0, element) - O(n)
for (int i = 0; i < n; i++) {
    list.add(0, element); // Cada inserción mueve todos los elementos
}

// ✅ BUENO: LinkedList.addFirst() - O(1)
for (int i = 0; i < n; i++) {
    linkedList.addFirst(element); // O(1) constante
}

// ❌ MALO: List.contains() - O(n)
if (list.contains(element)) { ... } // Búsqueda lineal

// ✅ BUENO: Set.contains() - O(1)
if (set.contains(element)) { ... } // Búsqueda hash constante

// ❌ MALO: Búsqueda lineal en List
for (PathInfo path : allPaths) {
    if (path.getId().equals(id)) return path; // O(n)
}

// ✅ BUENO: Búsqueda indexada en Map
return pathIndex.get(id); // O(1) amortizado
```

### Optimizaciones aplicadas:
1. **HashSet para visitedCells**: `contains()` es O(1) vs O(n) en List
2. **HashMap para pathIndex**: `get()` es O(1) vs búsqueda lineal O(n)
3. **TreeMap para pathsByLength**: Agrupación eficiente O(n log n) vs O(n²)
4. **ArrayList para allPaths**: Acceso por índice O(1), inserción al final O(1) amortizado

## 5. Liberación/alcance: colecciones locales vs campos

### ✅ Colecciones locales (stack scope)
```java
// Colección temporal que se libera automáticamente
public Set<Point> unionOfVisitedCells(List<List<Point>> paths) {
    return paths.stream()  // Stream temporal
            .flatMap(List::stream)
            .collect(Collectors.toSet()); // Nueva colección retornada
}
```

### ✅ Campos de instancia (objeto scope)
```java
// Colecciones que viven mientras el objeto existe
private final List<PathInfo> allPaths;
private final Set<Point> visitedCells;
private final Map<String, PathInfo> pathIndex;
```

### ✅ Evitar fugas de referencias mutables
```java
// ✅ BUENO: Retornar vista inmutable
public Map<String, PathInfo> getPathIndex() {
    return Collections.unmodifiableMap(pathIndex);
    // El cliente no puede modificar el mapa interno
}

// ❌ MALO: Retornar referencia directa
public Map<String, PathInfo> getPathIndex() {
    return pathIndex; // Permite modificación externa → fuga de referencia
}
```

### Gestión de memoria:
- ✅ Colecciones locales se liberan automáticamente cuando salen de scope
- ✅ Campos `final` evitan reasignación accidental
- ✅ Vistas inmutables previenen modificaciones externas
- ✅ Sin referencias circulares que impidan garbage collection

## Resumen de buenas prácticas aplicadas

| Práctica | Estado | Ejemplo |
|----------|--------|---------|
| **Inmutabilidad** | ✅ | `Collections.unmodifiableList()` en getters |
| **Copias defensivas** | ✅ | Constructor `PathInfo` crea copia |
| **Tipos genéricos** | ✅ | Todos los tipos parametrizados |
| **equals/hashCode** | ✅ | `Point` y `PathInfo` implementados |
| **Evitar O(n) accidental** | ✅ | `HashSet` y `HashMap` para búsquedas |
| **Liberación de memoria** | ✅ | Scope apropiado, vistas inmutables |

## Ejemplo completo de clase con buenas prácticas

```java
public class MatrixPathFinderModern {
    // ✅ Campos finales inmutables donde sea posible
    private final List<PathInfo> allPaths;
    private final Set<Point> visitedCells;
    private final Map<String, PathInfo> pathIndex;
    
    public MatrixPathFinderModern(char[][] matrix) {
        // ✅ Inicialización en constructor
        this.allPaths = new ArrayList<>();
        this.visitedCells = new HashSet<>();
        this.pathIndex = new HashMap<>();
    }
    
    // ✅ Getter inmutable
    public List<PathInfo> getAllPaths() {
        return Collections.unmodifiableList(allPaths);
    }
    
    // ✅ Operación O(1) usando índice
    public PathInfo findPathById(String id) {
        return pathIndex.get(id); // O(1) vs O(n)
    }
    
    // ✅ Stream API para operaciones funcionales
    public List<PathInfo> filterPathsByLength(int min, int max) {
        return allPaths.stream()
                .filter(p -> p.getLength() >= min && p.getLength() <= max)
                .collect(Collectors.toList()); // Nueva lista, no modifica original
    }
}
```
