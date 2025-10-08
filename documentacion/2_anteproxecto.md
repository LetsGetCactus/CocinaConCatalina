# ANTEPROXECTO


1. Descrición do proxecto
**Cocina con Catalina** é unha app Android escrita en Kotlin que permite achegar a cociña asiática (tradicional ou de inspiración) aos usuarios. Trátase dun repositorio cunha amigable interface de usuario, que permite a consulta, modificación e selección de receitas favoritas para cada usuario, gardándoas de forma persistente nunha base de datos en servidor, para que se poida acceder dende calquera dispositivo Android cunha conta de usuario.

    1.1. Xustificación do proxecto
        A idea orixinal xorde pola miña propia experiencia. EN contadas ocasión non tiven a mano o meu libro de receitas cando quería cociñar algo, ou ben preguntábanme pola receita dalgún dos meus pratos, e tiña que recurrir a consultar e mandar receitas unha a unha ás distintas persoas que mas pedían. Hoxe en día, recetarios hay moitos, pero aproveitando o auxe da cultura xaponesa e koreana que vemos na actualidade tanto a nivel de gastronomía, turismo e cultura ( audiovisual, deportiva,etc) aproveitei para darlle ese punto diferenciador á miña app. Ademáis o feito de poder adaptar os ingredientes da receita, e gardalos, dentro da mesma, é un elemento diferenciador fronte as miles de recietas el blogs de cociña que podemos atopar en Pinterest, WordPress, ou mesmo nos libros de cociña clásicos.

    1.2. Estudo de necesidades
        No mercado actual existen múltiples plataformas e blogs relacionados e con contenido gastronómico. Se ben é certo, que na actualidade o acceso a calquera información é moito máis sinxelo, **Cocina con Catalina** achégate de forma unificada e sinxela, un repositorio especializado en temática asiática, con recetas sacadas tanto das series koreanas, como da gastronomía a que podemos acceder debido ás grandes cadeas de comida deste tipo, e ademáis misturándoas con receitas de cosecha propia, que aúnan sabor asiático coa gastronomía habitual europea.
        O feito da modificación persistente dos ingredientes, tamén é salientable, posto que normalmente o acceso é sinxelo, máis recordar o que cambiarías para a próxima, soe acabar en páxinas perdidas entre libros de recetas, e que cando querias consultalas nonn as teñas accesibles ou incluso esquecerálas ou as perdeses.

    1.3. Persoas destinatarias
        Esta app está pensada para todo aquel entusiasta da cociña asiática. Non existe un público en particular, dende grandes a pequenos, as receitas (algunhas con maior dificultade que outras) poden realizarse en calquera casa.


    1.4. Modelo de negocio
        O modelo de negocio elixido é o Freemiun , para albergar a maior cantidade de usuarios posibles.
        Máis adiante desploquearánse funcionalidades só para usuarios de pago: compartir receitas, engadir as súas propias, entre outros


    1.5. Funcionalidades do proxecto (obxectivos e alcance)
 - Xestión de usuarios mediante conta de usuario para garantir a persistencia dos seus favoritos e modificacións de receitas
 - Repositorio de receitas en servidor para que esté sempre disponible
 - Xestión de favoritos de cada usuario, que poderá gardar ou borrar dentro desta listaxe
 - Personalización das cantidades de ingredientes de cada receta, e persistencia dos cambios gardados
 - Reprodución de música dende Spotify (require conta na plataforma)
 


2. Recursos

- Linguaxes de programación: Kotlin
- Técnicas: Corrutinas para a xestión de operacións asíncronas e mantemento duna intereface fluída e reactiva
- Librerías: JetPack Compose (Navigation e ViewModel) e SDK de Spotify
- Bases de datos: FireStore (NoSQL) 
- Servizos usados: Firestore Atuthentication, Firebase Storage
- APIs: SDK de Spotify e servizos Cloud de Google mediante Firebase
- Presuposto: Usaránse os plans gratuítos, polo que non hay custos.


3. Análise de requirimentos do sistema


    3.1. Funcionalidades
        As operacións a realizar dentro da app son:

       a. Rexistrar un novo usuario: 
            - Entrada: Nome, correo e contrasinal
            - Proceso: Validación dos datos de entrada e confirmación de contrasinal, logo gardarase en Firebase xerando un novo documento
            - Salida: ID de usuario e token de sesión de usuario

       b. Login de usuario previamente rexistrado:
        Existen dúas posibilidades: 
        b.1. Mediante correo e contrasinal
            - Entrada: Correo e contrasinal previamente rexistrados
            - Proceso: Autenticación mediante Firebase Authentication
            - Salida: ID de usuario e acceso ao reposito
        b.2. Mediante credenciais de Google
            - Entrada: Conta de usuario de Google
            - Proceso: Autenticación mediante Google
            - Salida: ID de usuario e acceso ao reposito

       c. Consulta/Lectura de receita:
            - Entrada: ID da receita a consultar
            - Proceso: Consulta e recuperación de datos da base de datos
            - Saída: Información da receita (título, imaxe, alérxenos, ingredientes e pasos)

       d. Engadir/quitar de favoritos:
            - Entrada: ID do usuario e da receita
            - Proceso: Engadir/ Eliminar da listaxe de receitas gargados no documento de usuario en Firestore
            - Saída: Mensaxe de confirmación e eliminación ou agregación da receta na listaxe

       e. Modificación de receitas:
            - Entrada: ID de usuario e da receita
            - Proceso: Inserción dos valores a actualizar nos campos de cantidade e unidade da listaxe de ingredientes da receta a consultar
            - Saída: Novo obxecto do documento ReceitasModificadas dentro da colección do usuario

       f. Engadir receitas (só admin)
            - Entrada: ID usuario (admin) e os datos para a nova receita (Nome, imaxe, descripción, alérxenos, listado de ingredientes e pasos a seguir)
            - Proceso: Inserción dos datos anteriormente comentados e xeración da nova receita no repositorio ReceitasOrixinais de Firebase
            - Saída: Mensaxe de confirmación /erro e visualización da nova receita dispoñible

       g. Reprodución de música mediante Spotify
            - Entrada: Conta de usuario de Spotify
            - Proceso: Autenticación de usuario e acceso ás súas playlist
            - Saída: Estado da reproducción e botóns de control da mesma
 
       h. Peche de sesión de usuario:
            - Entrada: Id usuario
            - Proceso: Saír da sesión do usuario e pechar o acceso á súa colección en Firebase
            - Saída: Pantalla de Login


    3.2. Tipos de usuarios
        A app funciona con dous tipos de usuario:
            - admin: eu, Catarina. Quen pode agregar máis ReceitasOrixinais e xestiona o mantemento da app
            - regular: todolos demáis usuarios rexistrados na app. Poden consultar, modificar e gardar en favoritos as diferentes receitas


    >TODO
    3.3. Normativa
        Investigarase que normativa vixente afecta ao desenvolvemento do proxecto e de que maneira.
        O proxecto debe adaptarse ás esixencias legais dos territorios onde vai operar.

        Normas obrigatorias a considerar:


        LOPDPGDD: Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales (ámbito nacional).

        GDPR: General Data Protection Regulation (se aplica se a aplicación opera a nivel europeo).

        Na documentación debe afirmarse que o proxecto cumpre coa normativa vixente.

        Apartados obrigatorios para cumprir a lexislación, que se atoparán dentro dos Términos e condición que os usuarios deberán aceptar para poder rexistrarse:

        Mecanismos de cumplimento:
            - Requeriránse o mínimo de datos sensibles para o rexistro do usuario
            - O usuario debe aceptar os Términos e condicións para o rexitro (necesario para o uso da app) que recolle: 
                - Aviso legal.
                - Política de privacidade.
                - Política de cookies.
            
            -A xestión de datos realízase íntegramente mediante Firebase Authentication e Firestore, xunto cos mecanismos de seguridade de Google (ver diagrama de despliegue, punto 4.1)

4. Deseño
        A app traballa con unha base de datos NoSQL (Google Cloud Firestore) xa que ofrece a flexibilidade que precisan os datos da app. 
    4.1. Deseño da arquitectura do sistema
        Ao traballar con **Firestore** (NoSQL) traballamos con dúas coleccións principais: receitas e usuarios, onde cada usuario, dentro da súa colección, poderá almacenar e xerar tantas subcoleccións "receitas favoritas" (seleccionadas polo usuario) e "receitas modificadas"(aquelas cuxas cantidades dos ingredientes fosen modificadas) como goste e totalmente personales de cada usuario.
        Quedando a estrututra da app da seguinte forma:

| COLECCION | TIPO | USO | ACCESO |
| :--- | :--- | :--- | :--- |
| receitas | Colección principal | Colección de receitas orixinais que engaden únicamente os usuarios que son admin | Público: lectura (salvo admin, que tamén posúe escritura) |
| usuarios | Colección principal | Almacena os diferentes perfís de usuario | Privado |
| receitasFavoritas | Subcolección de usuario | Privado, só para o usuario | Colección de receitas seleccionadas como favoritas do usuario |
| receitasModificadas | Subcolección de usuario|Privado, só para o usuario|Colección de receitas nas que as cantidades dos ingredientes foron modificadas para adpatar a receita aos gustos do usuario en cuestión |

        Desta forma podemos tamén priorizar na búsqueda de consultas para que comezen polas que xeralmente o usuario consultará máis a miúdo, que seran as favoritas ou modificadas polo mesmo. 
        No caso de non atopar coincidencias, entón pasaríase a buscar na colección principal de receitas 
        Mediante as **regras de seguridade** de Firebase poderemos xogar coa diferencia na lectura e escritura da colección de receitas,
        sendo a lectura pública, pero a escritura soamente para o usuario admin. Ademáis dentro das subcoleccións de cada usuario, o mesmo usuario poderá  tanto ler como escribir as que teña almacenadas, non sendo así coas do resto de usuarios,cando menos na versión gratuíta.
    
        Diagrama de compoñentes:
![Diagrama de compoñentes](documentacion/img/Componentes.png)
        Diagrama de secuencia:
![Diagrama de secuencia](documentacion/img/secuecnia.png)
        Diagrama de despliegue:
![Diagrama de despliegue](documentacion/img/despliefe.png)

    4.2. Deseño da persistencia de datos
        Para a persistencia dos datos traballaremos en totalidade co gardado dos mesmos na nube, así o usuario sempre poderá acceder ás súas receitas favoritas e modificadas sempre que teña un dispositivo Android dispoñible (e a súa conta de usuario), pero garantizando a independencia do dispositivo físico e o acceso aos seus datos .
        Ademáis aproveitamos que Firestore evítanos que teñamos que xestionar servidores, encargándose el mesmo desa dispoñinilidade dos datos.

        **Firebase Authentication** encargaráse da xestión segura dos contrasinais en formato hash. O usuario poderá autenticarse por medio de correo e contrasinal ou ben con credenciais de Google.

        E con **Storage** Poderemos almacenar e xestionar as URL públicas para as diferentes imaxes ou vídeo das receitas.

        Modelo lóxico:
![Modelo lóxico](documentacion/img/logico.png)
        Diagrama de casos de uso:
![Diagrama de casos de uso](documentacion/img/casosuso.png)

    4.3. Deseño da interface de usuario
        Pantallas:
            - Modo claro:

![HomeScreen](documentacion/img/HomeScreen.png){width=250}
![LoginScreen](documentacion/img/LoginScreen.png){width=250}
![RegisterScreen](documentacion/img/RegisterScreen.png){width=250}
![ListHostRecipeScreen](documentacion/img/ListHostRecipeScreen.png){width=250}
![ItemRecipeScreen](documentacion/img/ItemRecipeScreen.png){width=250}
![ModifyrecipeScreen](documentacion/img/ModifyRecipeScreen.png){width=250}
![FavouritesScreen](documentacion/img/FavouritesScreen.png){width=250}
![AddRecipeScreen](documentacion/img/AddRecipeScreen.png){width=250}

            - Modo escuro:

![HomeScreen](documentacion/img/HomeScreenDark.png){width=250}
![LoginScreen](documentacion/img/LoginScreenDark.png){width=250}
![RegisterScreen](documentacion/img/RegisterScreenDark.png){width=250}
![ListHostRecipeScreen](documentacion/img/ListHostRecipeScreenDark.png){width=250}
![ItemRecipeScreen](documentacion/img/ItemRecipeScreenDark.png){width=250}
![ModifyrecipeScreen](documentacion/img/ModifyRecipeScreenDark.png){width=250}
![FavouritesScreen](documentacion/img/FavouritesScreenDark.png){width=250}
![AddRecipeScreen](documentacion/img/AddRecipeScreenDark.png){width=250}

![Navegación](documentacion/img/CocinaConcatalina_Nav.drawio.png)
Pódense consultar todas as funcionalidades da navegación no seguinte enlace a ![Figma - Cocina con Catalina](https://www.figma.com/design/8qsePYT8JKnqSSCSa2tkNm/Cocina-con-Catalina?node-id=0-1&m=dev&t=3Km70Lbld7n7X4mr-1)




O anteproxecto entregado polo alumnado deberá ser aprobado polo Equipo Docente do Ciclo.