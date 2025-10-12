# ANTEPROXECTO

## 1. Descrición do proxecto
**Cocina con Catalina** é unha app Android escrita en **Kotlin** que permite achegar a cociña asiática (tradicional ou de inspiración) aos usuarios. Trátase dun repositorio cunha amigable interface de usuario, que permite a consulta, modificación e selección de receitas favoritas para cada usuario, gardándoas de forma persistente nunha base de datos en servidor, para que se poida acceder dende calquera dispositivo Android cunha conta de usuario.

### 1.1. Xustificación do proxecto
A idea orixinal xorde pola miña propia experiencia. En contadas ocasións non tiven a man o meu libro de receitas cando quería cociñar algo, ou ben preguntábanme pola receita dalgún dos meus pratos, e tiña que recorrer a consultar e mandar receitas unha a unha ás distintas persoas que mas pedían. Hoxe en día, recetarios hai moitos, pero aproveitando o auxe da cultura xaponesa e coreana que vemos na actualidade tanto a nivel de gastronomía, turismo e cultura (audiovisual, deportiva, etc.) aproveitei para darlle ese punto diferenciador á miña app. Ademais o feito de poder adaptar os ingredientes da receita, e gardalos, dentro da mesma, é un elemento diferenciador fronte ás miles de receitas en blogs de cociña que podemos atopar en Pinterest, WordPress, ou mesmo nos libros de cociña clásicos.

### 1.2. Estudo de necesidades
No mercado actual existen múltiples plataformas e blogs relacionados e con contido gastronómico, pero a maioría delas teñen anuncios moi invasivos que desenvolven nunha experiencia de usuario negativa, xa que con cada scroll aparece un anuncio do Shein diferente, e con cada nova vista, un novo anuncio que te ocupa toda a pantaia ou tes que esperar para ver o contido. Se ben é certo que na actualidade o acceso a calquera información é moito máis sinxelo, **Cocina con Catalina** achégate de forma unificada e amigable, un repositorio especializado en temática asiática, con receitas sacadas tanto das **series coreanas** en auxe, como da gastronomía á que podemos acceder debido ás grandes cadeas de comida deste tipo, e ademais mesturándoas con receitas de colleita propia, que aúnan sabor asiático coa gastronomía habitual europea. O feito da **modificación persistente** dos ingredientes, tamén é salientable, posto que normalmente o acceso é sinxelo, máis recordar o que cambiarías para a próxima, soe acabar en páxinas perdidas entre libros de receitas, e que cando querías consultalas non as teñas accesibles ou incluso esqueceras ou as perdeses.
Ademáis **Cocina con Catalina** non é só un repositorio de receitas, é todo o que envolve o pracer da comida e do cociñado. Se ben é certo que hai moitos recetarios de cociña asiática polo mundo adiante, esta app aúnate cociña e unha interface inspirada nos colores de Asia, o vermello, símbolo de prosperidade e boa sorte, xunto cunha estética simple e minimalista que te leva ao oriente. Pero na cociña non todo é a técnica culinaria e a medición dos ingredientes, o cociñado é unha experiencia por sí mesma e por iso, podes acceder dende a app a túa conta de Spotify para escoitar as túas playlists (xa sexan sons asiáticos como a última do Reno Renardo) para que desfrutes non só da comida, se non de todo o que implica a súa preparación.
O caso é que a maioría das aplicación de receitas asiáticas, ou están plagadas de anuncios ou teñes unha interface moi simple, que non fai que o usuario se meta de cheo na cultura, gastronomía e disfrute que esta app si que ofrece.

### 1.3. Persoas destinatarias
Esta app está pensada para todo aquel **entusiasta da cociña asiática**. Non existe un público en particular, dende grandes a pequenos, as receitas (algunhas con maior dificultade que outras) poden realizarse en calquera casa.

### 1.4. Modelo de negocio
O modelo de negocio elixido é o **Freemium** con anuncios (ver mockups das pantallas, incluso, poderíase engadir anuncio no banner de Cocina con Catalina, se fose preciso), para así poder albergar a maior cantidade de usuarios posibles. Máis adiante desbloquearánse a posibilidade de incorporar link para comprar os ingredientes das receitasfuncionalidades só para usuarios de pago: quitar anuncios, video-receitas, compartir receitas ou mercar os produtos directamente dende a app, entre outros.

### 1.5. Funcionalidades do proxecto (obxectivos e alcance)
* Xestión de **usuarios** mediante conta de usuario para garantir a persistencia dos seus favoritos e modificacións de receitas.
* **Repositorio de receitas** en servidor para que estea sempre dispoñible.
* Xestión de **favoritos** de cada usuario, que poderá gardar ou borrar dentro desta listaxe.
* **Personalización das cantidades de ingredientes** de cada receita, e persistencia dos cambios gardados.
* **Reprodución de música dende Spotify** (require conta na plataforma) para mellorar a experiencia do usuario. Ao permitir o acceso ás súas playlists persoais, a aplicación garante que cada persoa poida gozar da súa música favorita mentres prepara as receitas, facendo a actividade máis agradable e envolvente. Porque o cociñado é unha experiencia e a música é algo que nos acompaña día a día nas nosas rutinas, amenizando e mellorando as nosas rutinas, dende pasear, ir ao ximnasio, ou cociñar.

---

## 2. Recursos

* **Linguaxes de programación**: Kotlin.
* **Técnicas**: Corrutinas para a xestión de operacións asíncronas e mantemento dunha interface fluída e reactiva.
* **Librerías**: JetPack Compose (Navigation e ViewModel) e SDK de Spotify.
* **Bases de datos**: FireStore (NoSQL).
* **Servizos usados**: Firestore Authentication, Firebase Storage.
* **APIs**: SDK de Spotify, servizos Cloud de Google mediante Firebase e API de Youtube Android Player para os vídeos para os premiun.
* **Presuposto**: orzamento inicial require dun pago único de 25 $ ( uns 22€ actuais) para a tarifa de rexistro de Google Play Console. Os custos de servidor e base de datos (Firebase) serán cero inicialmente, xa que o plan gratuíto (Spark) cobre as necesidades básicas de almacenamento e operacións. No futuro, co crecemento dos usuarios ou uso de funcións avanzadas, os servizos de Firebase pasarán ao plan de pago por uso (Blaze), onde o prezo escalarase en función do consumo. 

TÁBOAS DE CUSTOS SERVIZOS FIREBASE:

Authentication: 
|:---|:---|:---| 
|Concepto | Gratuito (/mes, Spark) | De pago (/mes, Blaze) |
|Inicio de sesión con email/contraseña	| 10.000 usuarios/mes	| +0,01 USD por cada verificación extra |
|Inicio de sesión con Google |tamén 10.000/mes aprox.	| logo depende do proveedor |
Namentres non se teña máis de 10.000 inicios de sesión únicos ao mes, non se pagará nada nesta parte

Firestore Database:
|Concepto | Gratuito (/diario, Spark) | De pago (/diario, Blaze) |
|:---|:---|:---| 
|Lecturas de documentos	| 50.000 lecturas/día	| $0.06 por 100.000 lecturas adicionais |
|Escrituras de documentos |	20.000 escrituras/día |	$0.18 por 100.000 escrituras adicionais |
|Almacenamento |	1 GiB total	| $0.18 USD por GB adicional/mes | 
|Descarga de datos	| Incluido dentro de lecturas	| — |
Se un usuario medio pode abrir unhas 10 receitas ao día, poderíamos ter uns 5.000 usuarios diarios no formato grtauito (50.000 lecturas/ 10 lecturas por usuario = 5.000 usuarios activos gratis)

Cloud Storage :
|Almacenamento	| 1 GB total |	$0.026 USD por GB/mes |
|:---|:---|:---| 
|Descargas |	1 GB de descarga/mes	| $0.12 USD por GB extra |
|Subidas	| Gratis	| Gratis |
Se partimos de que una imaxen para una app móvil, podería pesar uns 500KB, poderíamos ter unhas 2.000 imaxes almacenadas no plan gratuito.
1 GB/mes sería o equivalente a visualizar esas 2.000 imaxes nese peso.

De todas formas activaránse as alertas de facturación en Google Cloud Platform para ter o gasto mensual vixiado.
Empregaremos regas de seguridade e cache na app para reducir as lecturas innecesarias


EN RESUMO:
| Concepto	| Custo mensual estimado	| Comentarios |
|:---|:---|:---| 
| Authentication |	$0	| Dentro deos 10.000 inicios gratuitos |
| Firestore Database |	$0 – $10 USD	| So se se superan as lecturas gratuitas |
| Cloud Storage |	$0 – $5 USD	| Dependendo do tamaño e número de imaxes
| Marxe de seguridade |	$5 – $10 USD |	Pra posibles picos de tráfico inesperados |
|** Total estimado mensual** |	**$0 – $25 USD** |	Plan realista pra unha app en crecemento |

En total serían os 25€ dos rexistro en Google Play Console, que só se pagan unha única vez, e logo como moito, un 25€ mensuais dependendo dos usuarios da plataforma.


---

## 3. Análise de requirimentos do sistema

### 3.1. Funcionalidades
As operacións a realizar dentro da app son:

1.  **Rexistrar un novo usuario**:
    * *Entrada*: Nome, correo e contrasinal.
    * *Proceso*: Validación dos datos de entrada e confirmación de contrasinal, logo gardarase en Firebase xerando un novo documento.
    * *Saída*: ID de usuario e token de sesión de usuario.

2.  **Login de usuario previamente rexistrado**:
    * **2.1. Mediante correo e contrasinal**:
        * *Entrada*: Correo e contrasinal previamente rexistrados.
        * *Proceso*: Autenticación mediante Firebase Authentication.
        * *Saída*: ID de usuario e acceso ao repositorio.
    * **2.2. Mediante credenciais de Google**:
        * *Entrada*: Conta de usuario de Google.
        * *Proceso*: Autenticación mediante Google.
        * *Saída*: ID de usuario e acceso ao repositorio.

3.  **Consulta/Lectura de receita**:
    * *Entrada*: ID da receita a consultar.
    * *Proceso*: Consulta e recuperación de datos da base de datos.
    * *Saída*: Información da receita (título, imaxe, alérxenos, ingredientes e pasos).

4.  **Engadir/quitar de favoritos**:
    * *Entrada*: ID do usuario e da receita.
    * *Proceso*: Engadir/Eliminar da listaxe de receitas gardados no documento de usuario en Firestore.
    * *Saída*: Mensaxe de confirmación e eliminación ou agregación da receita na listaxe.

5.  **Modificación de receitas**:
    * *Entrada*: ID de usuario e da receita.
    * *Proceso*: Inserción dos valores a actualizar nos campos de cantidade e unidade da listaxe de ingredientes da receita a consultar.
    * *Saída*: Novo obxecto do documento `ReceitasModificadas` dentro da colección do usuario.

6.  **Engadir receitas (só admin)**:
    * *Entrada*: ID usuario (admin) e os datos para a nova receita (Nome, imaxe, descrición, alérxenos, listado de ingredientes e pasos a seguir).
    * *Proceso*: Inserción dos datos anteriormente comentados e xeración da nova receita no repositorio `ReceitasOrixinais` de Firebase.
    * *Saída*: Mensaxe de confirmación/erro e visualización da nova receita dispoñible.

7.  **Reprodución de música mediante Spotify**:
    * *Entrada*: Conta de usuario de Spotify.
    * *Proceso*: Autenticación de usuario e acceso ás súas *playlist*.
    * *Saída*: Estado da reprodución e botóns de control da mesma.

8.  **Peche de sesión de usuario**:
    * *Entrada*: Id usuario.
    * *Proceso*: Saír da sesión do usuario e pechar o acceso á súa colección en Firebase.
    * *Saída*: Pantalla de Login.

### 3.2. Tipos de usuarios
A app funciona con dous tipos de usuario:

* **admin**: eu, Catarina. Quen pode agregar máis `ReceitasOrixinais` e xestiona o mantemento da app.
* **regular**: tódolos demais usuarios rexistrados na app. Poden consultar, modificar e gardar en favoritos as diferentes receitas.

### 3.3. Normativa

Declaración de Cumprimento Normativo:

O proxecto da app de receitas **Cocina con Catalina** garante o cumprimento total co Regulamento Xeral de Protección de Datos (**GDPR**) e a Lei Orgánica 3/2018 (**LOPDGDD**). Este cumprimento certifícase mediante a xestión da autenticación e o almacenamento en **Firebase**, asegurando os principios de Seguridade e Minimización conforme ás **directrices de Google** e o **Privacy by Design.**

* Termos e Condicións (Obrigatorios para o Rexistro)
Os seguintes documentos deben ser aceptados expresamente polo usuario para que o consentimento sexa válido:

| Documento Obrigatorio | Cláusulas Esenciais | Foco Legal nas Integracións (YouTube/Spotify) |
| :---: | :---: | :---: |
| **Aviso Legal** | Identificación completa do **Responsable do Tratamento** (eu, como propietaria da app). | Declaración de que a *app* só actúa como **interfaz/acceso** aos contidos de terceiros. |
| **Política de Privacidade** | **Finalidade** (execución do servizo), **Base Legal** (Consentimento/Contrato), **Datos recollidos** e **Garantía de Dereitos ARSULIPO**. | Mención obrigatoria a **Google/Firebase** como **Encarregado do Tratamento**. Advertencia explícita de que a interacción con **YouTube/Spotify** réxese polas súas políticas externas de privacidade. |
| **Política de *Cookies*** | Explicación do uso de identificadores e seguimento. | Debe incluír o uso de **tokens de autenticación e *Device IDs*** xerados por **Firebase** para xestionar a sesión e a seguridade do usuario. |

* Mecanismos de Cumprimento e Firebase
Estes son os mecanismos técnicos e procedementais que garanten a adecuación á lexislación:

Requiriranse o **mínimo de datos sensibles** para o rexistro (email, nome de usuario e contrasinal), cumprindo coa Minimización de Datos.

O usuario debe aceptar os **Termos e Condicións** para o rexistro, o que proporciona o Consentimento Expreso.

A xestión de datos realízase íntegramente mediante **Firebase Authentication e Firestore**, xunto cos mecanismos de seguridade de Google.

Xestión de Terceiros e APIs:

Información e advertencia explícita sobre a **política de datos de YouTube** ao reproducir vídeos.

Obtención de **consentimento específico** para conectar e acceder a calquera dato da conta de Spotify, separado do consentimento xeral de rexistro.

Garántese o **Dereito de Supresión** tanto en Firebase Authentication como en Firestore para unha baixa completa.

* Principios Legais Aplicados (GDPR/LOPDGDD)
Markdown

| Principio Legal (GDPR/LOPDGDD) | Implicación Legal | Mecanismo Específico con Firebase |
| :---: | :---: | :---: |
| **Minimización de Datos** | A recollida limítase aos datos esenciais. | **Firebase Authentication** impón a minimización por deseño (*Privacy by Design*). |
| **Seguridade e Confidencialidade** | As credenciais de acceso deben estar protexidas. | **Firebase Authentication** xestiona automaticamente o **cifrado, *hashing* e *salting*** dos contrasinais, asegurando un alto estándar de seguridade. |
| **Transferencia Internacional de Datos (TID)** | Os datos poden ser almacenados fóra da UE. | **Google (Firebase/Firestore)** é o **Encarregado do Tratamento**, e as TID amparanse nas **Cláusulas Contractuais Tipo** aprobadas pola UE. |
| **Dereito de Supresión (Esquecemento)** | O usuario debe poder borrar todos os seus datos de forma total. | A *app* debe garantirá a baixa completa en **Firebase Auth e Firestore**. |

---

## 4. Deseño
A app traballa cunha base de datos NoSQL (**Google Cloud Firestore**) xa que ofrece a flexibilidade que precisan os datos da app.

### 4.1. Deseño da arquitectura do sistema
Ao traballar con **Firestore** (NoSQL) traballamos con dúas coleccións principais: `receitas` e `usuarios`, onde cada usuario, dentro da súa colección, poderá almacenar e xerar tantas subcoleccións `receitasFavoritas` (seleccionadas polo usuario) e `receitasModificadas` (aquelas cuxas cantidades dos ingredientes fosen modificadas) como guste e totalmente persoais de cada usuario.

Quedando a estrutura da app da seguinte forma:

| COLECCIÓN | TIPO | USO | ACCESO |
| :--- | :--- | :--- | :--- |
| receitas | Colección principal | Colección de receitas orixinais que engaden únicamente os usuarios que son admin | Público: lectura (salvo admin, que tamén posúe escritura) |
| usuarios | Colección principal | Almacena os diferentes perfís de usuario | Privado |
| receitasFavoritas | Subcolección de usuario | Colección de receitas seleccionadas como favoritas do usuario | Privado, só para o usuario |
| receitasModificadas | Subcolección de usuario | Colección de receitas nas que as cantidades dos ingredientes foron modificadas para adaptar a receita aos gustos do usuario en cuestión | Privado, só para o usuario |

Desta forma podemos tamén priorizar na búsqueda de consultas para que comecen polas que xeralmente o usuario consultará máis a miúdo, que serán as favoritas ou modificadas polo mesmo. No caso de non atopar coincidencias, entón pasaríase a buscar na colección principal de receitas. Mediante as **regras de seguridade** de Firebase poderemos xogar coa diferenza na lectura e escritura da colección de receitas, sendo a lectura pública, pero a escritura soamente para o usuario admin. Ademais dentro das subcoleccións de cada usuario, o mesmo usuario poderá tanto ler como escribir as que teña almacenadas, non sendo así coas do resto de usuarios, cando menos na versión gratuíta.

**Diagrama de compoñentes**:
![Diagrama de compoñentes](documentacion/img/Componentes.png)

**Diagrama de secuencia**:
![Diagrama de secuencia](documentacion/img/secuecnia.png)

**Diagrama de despregamento**:
![Diagrama de despliegue](documentacion/img/despliefe.png)

### 4.2. Deseño da persistencia de datos
Para a persistencia dos datos traballaremos en totalidade co gardado dos mesmos na nube, así o usuario sempre poderá acceder ás súas receitas favoritas e modificadas sempre que teña un dispositivo Android dispoñible (e a súa conta de usuario), garantizando a independencia do dispositivo físico e o acceso aos seus datos. Ademais aproveitamos que **Firestore** evítanos que teñamos que xestionar servidores, encargándose el mesmo desa dispoñibilidade dos datos.

**Firebase Authentication** encargarase da xestión segura dos contrasinais en formato *hash*. O usuario poderá autenticarse por medio de correo e contrasinal ou ben con credenciais de Google.

E con **Storage** Poderemos almacenar e xestionar as URL públicas para as diferentes imaxes ou vídeos das receitas.

**Modelo lóxico**:
![Modelo lóxico](documentacion/img/logico.png)

**Diagrama de casos de uso**:
![Diagrama de casos de uso](documentacion/img/casosuso.png)

### 4.3. Deseño da interface de usuario

#### Pantallas:

* **Modo claro**:
    ![HomeScreen](documentacion/img/HomeScreen.png){width=250}
    ![LoginScreen](documentacion/img/LoginScreen.png){width=250}
    ![RegisterScreen](documentacion/img/RegisterScreen.png){width=250}
    ![ListHostRecipeScreen](documentacion/img/ListHostRecipeScreen.png){width=250}
    ![ItemRecipeScreen](documentacion/img/ItemRecipeScreen.png){width=250}
    ![ModifyrecipeScreen](documentacion/img/ModifyRecipeScreen.png){width=250}
    ![FavouritesScreen](documentacion/img/FavouritesScreen.png){width=250}
    ![AddRecipeScreen](documentacion/img/AddRecipeScreen.png){width=250}

* **Modo escuro**:
    ![HomeScreen](documentacion/img/HomeScreenDark.png){width=250}
    ![LoginScreen](documentacion/img/LoginScreenDark.png){width=250}
    ![RegisterScreen](documentacion/img/RegisterScreenDark.png){width=250}
    ![ListHostRecipeScreen](documentacion/img/ListHostRecipeScreenDark.png){width=250}
    ![ItemRecipeScreen](documentacion/img/ItemRecipeScreenDark.png){width=250}
    ![ModifyrecipeScreen](documentacion/img/ModifyRecipeScreenDark.png){width=250}
    ![FavouritesScreen](documentacion/img/FavouritesScreenDark.png){width=250}
    ![AddRecipeScreen](documentacion/img/AddRecipeScreenDark.png){width=250}

![Navegación](documentacion/img/CocinaConcatalina_Nav.drawio.png)

Pódense consultar todas as funcionalidades da navegación no seguinte enlace a [Figma - Cocina con Catalina](https://www.figma.com/design/8qsePYT8JKnqSSCSa2tkNm/Cocina-con-Catalina?node-id=0-1&m=dev&t=3Km70Lbld7n7X4mr-1).

