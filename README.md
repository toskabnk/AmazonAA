# Actividad de Aprendizaje de Acceso a Datos

## Primera Evaluacion
### Requisitos obligatorios:
- [x] El modelo de datos estará compuesto de, al menos, 5 clases y tendrán que existir relaciones entre ellas. Cada clase tendrá, al menos, 6 atributos (String, int, float, boolean y algún tipo para almacenar fechas). Cada clase tendrá, al menos, 2 atributos obligatorios y algún otro con algún tipo de restricción de formato/validación.
- [x] Se tendrá que poder realizar, el menos, las operaciones CRUD sobre cada una de las clases. Se controlarán, al menos, los errores 400, 404 y 500
- [x] Añade opciones de filtrado para al menos una operación en cada clase en donde se puedan indicar hasta 3 campos diferentes (solo aplicable para operaciones GET)
- [x] Prepara una colección Postman que permita probar todas las operaciones desarrolladas
- [x] Configura en el proyecto la librería logback para que la aplicación web cuente con un log. Añade trazas en el código de forma que permita seguir el rastro de ejecución en el log (para todas las operaciones que se puedan realizar y también para los casos en los que se recojan errores)

### Requisitos opcionales:
- [ ] Añade una operación PATCH para cada una de las clases del modelo
- [x] Utiliza la herramienta Git (y GitHub) durante todo el desarrollo de la aplicación. Escribe el fichero README.md para explicar cómo poner en marcha el proyecto. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo
- [x] Añade 3 nuevos endpoints a la aplicación (sin repetir método) que realicen nuevas operaciones con los datos y que requieran el uso de DTOs y/o utilizar las relaciones entre las clases
- [x] Securiza algunas de tus operaciones de la API con un token JWT
- [ ] Añade 3 operaciones que utilicen consultas JPQL para extraer la información de la base de datos
- [x] Añade 3 operaciones que utilicen consultas SQL nativas para extraer la información de la base de datos
- [x] Añade 2 clases más al modelo de datos con sus respectivas operaciones CRUD (inclúyelas también en la colección Postman)
- [ ] Parametriza la colección Postman para que pueda ser ejecutada con el Runner de Postman y realizar una prueba completa de la API

## Segunda evaluacion
### Requisitos obligatorios
- [x] Diseña la API y escribe el fichero OpenAPI 3.0 de la API. Incluye, al menos, los casos de éxito (20X), 400, 404 y los 500.
- [x] Diseña una API Virtual de forma que existan, al menos, 3 Casos de Uso para cada operación (uno de OK y otro para KO).
- [x] Prepara una colección Postman de prueba para la API diseñada y otra que permita probar todos los Casos de Uso de la API virtual
- [x] Diseña, al menos, 3 operaciones para que funcionen de forma reactiva con WebFlux (https://github.com/toskabnk/AmazonAAReactive/releases/tag/v1.0).
- [x] Ajusta el desarrollo de tu proyecto para que cumpla todas las decisiones de diseño adoptadas en los puntos anteriores

### Requisitos opcionales
- [x] Si tu API está securizada, añade la información necesaria al fichero OpenAPI 3.0
- [x] Añade alguna operación en la que se envien o reciban ficheros
- [x] Parametriza ambas colecciones Postman de forma que sea fácil cambiar el host, puerto o basePath de la API
- [ ] Añade al fichero de especificación de la API (OpenAPI 3.0) un par de ejemplos para cada operación
- [x] Utiliza las herramientas Git y GitHub durante todo el desarrollo de la aplicación. Utiliza el gestor de Issues para los problemas/fallos que vayan surgiendo.

# Como ejecutar la aplicacion:
- Tener instalado y configurado correctamente Java 17 y Maven
- (Opcional) Tener instalado Postman y haber importado la coleccion
- Descarga el codigo fuente del proyecto
- Importa el codigo en el IDE de tu preferencia
- Ejecuta en el terminal de tu IDE el comando `mvn spring-boot:run`
- Los metodos GET de Product y Review son publicos. Asi como `/token` y `/register`. Todo lo demas necesita autorizacion
- Si todo ha ido correctamente, la aplicacion deberia estar funcionando correctamente, primero registrate usando `/register` y luego consigue tu token con `/token`
- Añade el token a la cabecera Authorization de las peticiones añadiendo `Bearer MI_TOKEN`, si usas POSTMAN, solo tienes que guardar el token una variable global llamada `token`, la cabecere de Autenticacion se añadira automaticamente.
- Ya puedes probar los endpoints de la API en Postman o cualquier otra aplicacion similar