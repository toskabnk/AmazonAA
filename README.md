# Actividad de Aprendizaje de Acceso a Datos

### Requisitos obligatorios:
- [x] El modelo de datos estará compuesto de, al menos, 5 clases y tendrán que existir relaciones entre ellas. Cada clase tendrá, al menos, 6 atributos (String, int, float, boolean y algún tipo para almacenar fechas). Cada clase tendrá, al menos, 2 atributos obligatorios y algún otro con algún tipo de restricción de formato/validación.
- [x] Se tendrá que poder realizar, el menos, las operaciones CRUD sobre cada una de las clases. Se controlarán, al menos, los errores 400, 404 y 500
- [x] Añade opciones de filtrado para al menos una operación en cada clase en donde se puedan indicar hasta 3 campos diferentes (solo aplicable para operaciones GET)
- [x] Prepara una colección Postman que permita probar todas las operaciones desarrolladas
- [x] Configura en el proyecto la librería logback para que la aplicación web cuente con un log. Añade trazas en el código de forma que permita seguir el rastro de ejecución en el log (para todas las operaciones que se puedan realizar y también para los casos en los que se recojan errores)

### Requisitos opcionales:
- [ ] Añade una operación PATCH para cada una de las clases del modelo
- [x] Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Escribe el fichero README.md para explicar cómo poner en marcha el proyecto. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo
- [ ] Añade 3 nuevos endpoints a la aplicación (sin repetir método) que realicen nuevas operaciones con los datos y que requieran el uso de DTOs y/o utilizar las relaciones entre las clases
- [x] Securiza algunas de tus operaciones de la API con un token JWT
- [ ] Añade 3 operaciones que utilicen consultas JPQL para extraer la información de la base de datos
- [ ] Añade 3 operaciones que utilicen consultas SQL nativas para extraer la información de la base de datos
- [x] Añade 2 clases más al modelo de datos con sus respectivas operaciones CRUD (inclúyelas también en la colección Postman)
- [ ] Parametriza la colección Postman para que pueda ser ejecutada con el Runner de Postman y realizar una prueba completa de la API

### Como ejecutar la aplicacion:
- Tener instalado y configurado correctamente Java 17 y Maven
- (Opcional) Tener instalado Postman y haber importado la coleccion
- Descarga el codigo fuente del proyecto
- Importa el codigo en el IDE de tu preferencia
- Ejecuta en el terminal de tu IDE el comando `mvn spring-boot:run`
- Si todo ha ido correctamente, la aplicacion deberia estar funcionando correctamente y puedes probar los endpoints de la API en Postman o cualquier otra aplicacion similar