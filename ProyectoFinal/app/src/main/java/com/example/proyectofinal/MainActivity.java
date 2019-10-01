package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyectofinal.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Actividad que maneja los fragmentos a mostrar
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Auto-inicio de sesión de un usuario
     * @param savedInstanceState estado de la instancia anterior
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        if(getIntent().getExtras() != null) {
            if(getIntent().getExtras().get("key") != null) {
                if(getIntent().getExtras().get("key").toString().equals("CartDelete")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                }
            }
        }

        // Menu de navegacion inferior
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_nav);
        bottom_navigation.setOnNavigationItemSelectedListener(navListener);

        Paper.init(this);

        String userEmailKey = Paper.book().read(Prevalent.emailKey);
        String passwordEmailKey = Paper.book().read(Prevalent.passwordKey);

        // Comprobamos la sesion iniciada anteriormente por el usuario
        if (userEmailKey != "" && passwordEmailKey != "") {
            if (!TextUtils.isEmpty(userEmailKey) || !TextUtils.isEmpty(passwordEmailKey)) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(userEmailKey, passwordEmailKey)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Usuario recordado", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Falló la autenticación automática", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        // Descomentar solo para insertar productos en la Firebase Database
        //insertProductsDatabase();
    }

    /**
     * Manejamos el Listener del menú inferior
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        /**
         * Mostramos el fragmento seleccionado por el usuario
         * @param menuItem menu de navegación inferior
         * @return devolvemos true siempre
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = new HomeFragment();

            switch (menuItem.getItemId()) {
                case R.id.nav_inicio:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_buscar:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_categorias:
                    selectedFragment = new CategoriesFragment();
                    break;
                case R.id.nav_carrito:
                    selectedFragment = new CartFragment();
                    break;
                case R.id.nav_perfil:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    /**
     * Creamos e insertamos los productos en la base de datos
     */
    private void insertProductsDatabase() {

        List<Product> productList = new ArrayList<>();

        productList.add(new Product(0,
                "MSI Z390-A PRO",
                129.90f,
                "La placa gaming MSI Z390-A PRO tiene socket Intel 1151 y soporta procesadores Intel de 8ª y 9ª generación.",
                "",
                "Processor\n" +
                        "Supports 9th/ 8th Gen Intel® Core™ / Pentium® Gold / Celeron® processors for LGA 1151 socket\n" +
                        "Chipset\n" +
                        "Intel® Z390 Chipset\n" +
                        "Memory\n" +
                        "4 x DDR4 memory slots, support up to 64GB1",
                "MSI",
                4.8f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Placas%20Base%2FMSI%20Z390-A%20PRO.jpg?alt=media&token=99c8d996-a253-47f0-9d03-649e605f91a4",
                true,
                "Placas Base"));

        productList.add(new Product(1,
                "Gigabyte B450M DS3H",
                66.99f,
                "Las placas base GIGABYTE serie 400 maximizan el potencial de su PC con la tecnología AMD StoreMI. StoreMI acelera los dispositivos de almacenamiento tradicionales para reducir los tiempos de arranque y mejorar la experiencia general del usuario. Esta utilidad fácil de usar combina la velocidad de las SSD con la alta capacidad de las unidades de disco duro en una sola unidad, mejora las velocidades de lectura / escritura del dispositivo para que coincida con las SSD, aumenta el rendimiento de los datos a un valor increíble y transforma las PC de uso cotidiano a un sistema impulsado por el rendimiento.\n" +
                        "\n" +
                        "Además, cuenta con Smart Fan 5, con esta refrigeración los usuarios pueden asegurarse de que su PC gaming puede mantener un rendimiento estable mientras se mantiene refrigerado. Smart Fan 5 permite a los usuarios intercambiar sus conectores para los ventiladores para reflejar diferentes sensores térmicos en diferentes ubicaciones de la placa base. Además con Smart Fan 5 los conectores híbridos para el ventilador soportan ventiladores PWM y de voltaje, esto hace que la placa base este más adaptada a montar refrigeración líquida.",
                " ",
                "Procesador\n" +
                        "AM4 Socket:\n" +
                        "AMD Ryzen™ 2nd Generation processors\n" +
                        "AMD Ryzen™ with Radeon™ Vega Graphics processors\n" +
                        "AMD Ryzen™ 1st Generation processors\n" +
                        "Chipset\n" +
                        "AMD B450\n" +
                        "Memoria\n" +
                        "4 x DDR4 DIMM sockets supporting up to 64 GB of system memory.\n" +
                        "Arquitectura de memoria Dual Channel\n" +
                        "Support for DDR4 3200(O.C.)/2933/2667/2400/2133 MHz memory modules\n" +
                        "Soporte para módulos de memoria ECC Un-buffered UDIMM 1Rx8/2Rx8 (operando en modo no-ECC)\n" +
                        "Soporte para módulos de memoria non-ECC Un-buffered DIMM 1Rx8/2Rx8\n" +
                        "Soporte para módulos de memoria Extreme Memory Profile (XMP)",
                "Gigabyte",
                4.5f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Placas%20Base%2FGigabyte%20B450M%20DS3H.jpg?alt=media&token=1509f423-3e6a-4bc6-9ff4-88ef7b516fc5",
                true,
                "Placas Base"));

        productList.add(new Product(2,
                "Asus PRIME B450M-A",
                75.99f,
                "Las placas base ASUS Prime B450 Series proporcionan la base sólida necesaria para su primera construcción, además de la flexibilidad para crecer con sus ambiciones. Hemos fusionado todo lo bueno que se empaqueta en los procesadores AMD Ryzen con el diseño e ingeniería esencial de ASUS, por lo que se beneficia de las tecnologías líderes en la industria, que incluyen el ajuste automático del sistema, controles integrales de refrigeración y audio inmersivo integrado. Cuando construye con una placa madre, desarrolla de forma inteligente, fácil y asequible.",
                "",
                "Características:\n" +
                        "Fan Xpert 2+: controles flexibles para la máxima refrigeración y silencio, más detección de temperatura GPU para juegos.\n" +
                        "Conectividad ultrarrápida: flexibilidad máxima con USB 3.1 Gen 2 y M.2 nativo. \n" +
                        "Cabezal ASUS Aura Sync: conector integrado para tiras LED RGB, fácilmente sincronizado con una cartera cada vez mayor de hardware compatible con Aura Sync.\n" +
                        "5X Protection III: múltiples protecciones de hardware para la protección integral del sistema",
                "Asus",
                4.6f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Placas%20Base%2FAsus%20PRIME%20B450M-A.jpg?alt=media&token=9b3eb2f4-5abd-463a-8684-e00b24937f35",
                true,
                "Placas Base"));

        productList.add(new Product(3,
                "MSI Mpg Z390 Gaming Pro Carbon",
                179.90f,
                "La placa gaming MSI Mpg Z390 Gaming Pro Carbon tiene socket Intel 1151 y soporta procesadores Intel de 8ª y 9ª generación.",
                "",
                "Especificaciones:\n" +
                        "Processor\n" +
                        "Supports 9th / 8th Gen Intel® Core™ / Pentium® Gold / Celeron® processors for LGA 1151 socket\n" +
                        "Chipset\n" +
                        "Intel® Z390 Chipset\n" +
                        "Memory\n" +
                        "4 x DDR4 memory slots, support up to 64GB\n" +
                        "Supports DDR4 4400(OC)/ 4300(OC)/ 4266(OC)/ 4200(OC)/ 4133(OC)/ 4000(OC)/ 3866(OC)/ 3733(OC)/ 3600(OC)/ 3466(OC)/ 3400(OC)/ 3333(OC)/ 3300(OC)/ 3200(OC)/ 3000(OC) / 2800(OC)/ 2666/ 2400/ 2133 MHz\n" +
                        "Supports Dual-Channel mode\n" +
                        "Supports non-ECC, un-buffered memory\n" +
                        "Supports Intel® Extreme Memory Profile (XMP)",
                "MSI",
                4.9f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Placas%20Base%2FMSI%20Mpg%20Z390%20Gaming%20Pro%20Carbon.jpg?alt=media&token=74e1cd5f-e96e-4858-821f-035ac3238dfd",
                true,
                "Placas Base"));

        productList.add(new Product(4,
                "Gigabyte GA-B360M DS3H",
                71.99f,
                "Admite procesadores Intel® Core ™ de octava generación\n" +
                        "Nuevo diseño híbrido de PWM digital\n" +
                        "Memoria Intel® Optane ™\n" +
                        "Dual Channel Non-ECC Unbuffered DDR4\n" +
                        "M.2 ultra rápida con PCIe Gen3 x4 y interfaz SATA\n" +
                        "CEC 2019 Ready, ahorre energía tan fácil como One Click\n" +
                        "Capacitadores de audio de alta calidad y con protección de ruido con separación de audio led\n" +
                        "GIGABYTE Exclusive 8118 Gaming LAN con gestión de ancho de banda",
                "",
                "Procesador\n" +
                        "Support for 8th Generation Intel® Core™ i7 processors/Intel® Core™ i5 processors/Intel® Core™ i3 processors/Intel® Pentium® processors/ Intel® Celeron® processors in the LGA1151 package\n" +
                        "Caché L3 varía según la CPU\n" +
                        "(Por favor, acuda a \"lista de soporte de CPU\" para más información.)\n" +
                        "Chipset\n" +
                        "Intel® B360 Express Chipset\n" +
                        "Memoria\n" +
                        "4 x sockets DDR4 DIMM con soporte de hasta 64 GB de memoria del sistema\n" +
                        "Arquitectura de memoria Dual Channel\n" +
                        "Support for DDR4 2666/2400/2133 MHz memory modules\n" +
                        "Soporte para módulos de memoria ECC Un-buffered UDIMM 1Rx8/2Rx8 (operando en modo no-ECC)\n" +
                        "Soporte para módulos de memoria non-ECC Un-buffered DIMM 1Rx8/2Rx8\n" +
                        "Soporte para módulos de memoria Extreme Memory Profile (XMP)\n" +
                        "* To support 2666 MHz or XMP memory, you must install an 8th Generation Intel® Core™ i7/i5 processor.",
                "Gigabyte",
                4.5f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Placas%20Base%2FGigabyte%20GA-B360M%20DS3H.jpg?alt=media&token=1d9f02a7-1c25-41e7-894d-7db32dae6b4a",
                false,
                "Placas Base"));

        productList.add(new Product(5,
                "Intel Core i5-8400 2.8GHz BOX",
                195.99f,
                "Te presentamos el Intel Core i5-8400, un procesador de 8ª Generación y socket Intel 1151.",
                "",
                "General information\n" +
                        "\n" +
                        "Type CPU / Microprocessor Market segment Desktop\n" +
                        "Family Intel Core i5\n" +
                        "Model number ? i5-8400\n" +
                        "Frequency ? 2800 MHz\n" +
                        "Turbo frequency 4000 MHz (1 core)\n" +
                        "3900 MHz (2, 3 or 4 cores)\n" +
                        "3800 MHz (5 or 6 cores)\n" +
                        "Low power frequency 800 MHz\n" +
                        "Bus speed ? 8 GT/s DMI\n" +
                        "Clock multiplier ? 28\n" +
                        "Package 1151-land Flip-Chip Land Grid Array\n" +
                        "Socket Socket 1151 / H4 / LGA1151\n" +
                        "Size 1.48\" x 1.48\" / 3.75cm x 3.75cm ",
                "Intel",
                4.9f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Procesadores%2FIntel%20Core%20i7-9700K%203.6Ghz.jpg?alt=media&token=0ea20d88-99eb-415c-90ef-abce9c424dea",
                true,
                "Procesadores"));

        productList.add(new Product(6,
                "Intel Core i9-9900K 3.6Ghz",
                489f,
                "Sólo compatible con sus placas base basadas en chipset de la serie 300, el procesador Intel Core i9-9900K 3.6 GHz Eight-Core LGA 1151 está diseñado para juegos, creación y productividad.\n" +
                        "\n" +
                        "Tiene una velocidad de reloj base de 3.6 GHz y viene con características como la compatibilidad con Intel Optane Memory, el cifrado AES-NI, la tecnología Intel vPro, Intel TXT, la Protección de dispositivos Intel con Boot Guard, la tecnología de virtualización Intel VT-d para E / S dirigida y la tecnología Intel Hyper-Threading para tareas múltiples de 16 vías.\n" +
                        "\n" +
                        "Con la tecnología Intel Turbo Boost Max 3.0, la frecuencia máxima de turbo que este procesador puede alcanzar es de 5.0 GHz. Además, este procesador cuenta con 8 núcleos con 16 subprocesos en un zócalo LGA 1151, tiene 16 MB de memoria caché y 24 líneas PCIe. Tener 8 núcleos permite que el procesador ejecute varios programas simultáneamente sin ralentizar el sistema, mientras que los 16 subprocesos permiten que una secuencia de instrucciones ordenada básica pase o sea procesada por un solo núcleo de CPU. Este procesador también admite memoria RAM DDR4-2666 de doble canal y utiliza tecnología de novena generación.",
                "",
                "General\n" +
                        "CPU Model Intel Core i9-9900K\n" +
                        "CPU Socket LGA 1151\n" +
                        "Unlocked Yes\n" +
                        "Performance\n" +
                        "Number of Cores 8\n" +
                        "Number of Threads 16\n" +
                        "Base Clock Speed 3.6 GHz\n" +
                        "Maximum Boost Speed 5.0 GHz\n" +
                        "L3 Cache 16 MB\n" +
                        "Memory Support\n" +
                        "Memory Support DDR4 2666 MHz\n" +
                        "Channel Architecture Dual Channel",
                "Intel",
                4.8f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Procesadores%2FIntel%20Core%20i9-9900K%203.6Ghz.jpg?alt=media&token=dc6e37a0-e319-42e3-ac0c-9a5763593f07",
                true,
                "Procesadores"));

        productList.add(new Product(7,
                "Procesador AMD Ryzen 5 2600 3.4 Ghz",
                139.50f,
                "Te presentamos el AMD Ryzen 5 2600, un procesador que cuenta con 6 núcleos, socket AMD AM4 y arquitectura de 64 bits. Y es que la forma de contratacar de AMD ha sido contundente, lo nuevos Ryzen no solo demuestran mayor efectividad si no tambien un consumo mucho más contenido que sus predecesores. Los procesadores AMD Ryzen están diseñados para satisfacer las necesidades de los usuarios más avanzados y exigentes. Para minimizar la latencia de respuesta AMD lanza la tecnología Turbo Core que dará la mayor frecuencia del núcleo cuando lo necesita.",
                "",
                "Procesador\n" +
                        "Familia de procesador: AMD Ryzen 5\n" +
                        "Frecuencia del procesador: 3,4 GHz\n" +
                        "Número de núcleos de procesador: 6\n" +
                        "Socket de procesador: Zócalo AM4\n" +
                        "Componente para: PC\n" +
                        "Litografía del procesador: 12 nm\n" +
                        "Caja: Si\n" +
                        "Modelo del procesador: 2600\n" +
                        "Número de filamentos de procesador: 12\n" +
                        "Modo de procesador operativo: 32-bit, 64 bits\n" +
                        "Caché del procesador: 16 MB\n" +
                        "Tipo de cache en procesador: L3\n" +
                        "Frecuencia del procesador turbo: 3,9 GHz\n" +
                        "Memoria\n" +
                        "Canales de memoria que admite el procesador: Dual\n" +
                        "Tipos de memoria que admite el procesador: DDR4-SDRAM\n" +
                        "Velocidad de reloj de memoria que admite el procesador: 2933 MHz\n" +
                        "Control de energía\n" +
                        "Potencia de diseño térmico (TDP): 65 W",
                "AMD",
                4.9f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Procesadores%2FProcesador%20AMD%20Ryzen%205%202600X%203.6%20Ghz.jpg?alt=media&token=73ef6b37-96a5-4d61-a43e-86ad1cd023b4",
                true,
                "Procesadores"));

        productList.add(new Product(8,
                "AMD Ryzen 5 3600 3.6GHz BOX",
                216.70f,
                "Fabricados para rendir. Diseñados para ganar. Más velocidad. Más memoria. Mayor ancho de banda. Exígelos al máximo, exprime hasta la última gota de rendimiento, llévalos al límite. Los procesadores AMD Ryzen™ de 3ª generación se diseñaron para superar todas las expectativas y marcar un nuevo camino en materia de procesadores de alto rendimiento.\n" +
                        "\n" +
                        "Los procesadores AMD Ryzen™ de 3.ª generación nacen de la tecnología de fabricación más avanzada del mundo para garantizar un rendimiento ganador y un sistema con un funcionamiento asombrosamente refrigerado y silencioso.\n" +
                        "\n" +
                        "Los procesadores Ryzen de 3ª generación integran la primera plataforma de conectividad PCIe® 4.0 del mundo para poner en tus manos las tecnologías de motherboards, tarjetas gráficas y almacenamiento más avanzadas que existen.",
                "",
                "Características:\n" +
                        "Plataforma Socket AM4 Preparada Para AMD Ryzen™. La plataforma informática estándar de AMD, actualizada con el nuevo chipset X570 compatible con PCIe® 4.0.\n" +
                        "AMD Ryzen™ 5 Para Juegos. Experiencias de élite en títulos competitivos.\n" +
                        "AMD Ryzen™ 5 Para Transmisión. Juego y captura en simultáneo a partir de un sistema potente.\n" +
                        "AMD Ryzen™ 5 Para Realidad Virtual. Experimenta contenido de realidad virtual de última generación sin interrupciones y con capacidad de respuesta.\n" +
                        "AMD Ryzen™ 5 Para Todos los Creadores. Potencia y rendimiento para desarrolladores y fanáticos.",
                "AMD",
                3.4f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Procesadores%2FAMD%20Ryzen%205%203600%203.6GHz%20BOX.jpg?alt=media&token=cbd504ad-f423-46d6-8485-042fb8a88389",
                false,
                "Procesadores"));

        productList.add(new Product(9,
                "Intel Core i7-9700K 3.6Ghz",
                399.99f,
                "Sólo compatible con sus placas base basadas en chipset de la serie 300, el procesador Intel Core i7-9700K 12M cache, hasta 4.90 GHz está diseñado para juegos, creación y productividad.\n" +
                        "\n" +
                        "Tiene una velocidad de reloj base de 3.6 GHz y viene con características como la compatibilidad con Intel Optane Memory, el cifrado AES-NI, la tecnología Intel vPro, Intel TXT, la Protección de dispositivos Intel con Boot Guard, la tecnología de virtualización Intel VT-d para E / S.\n" +
                        "\n" +
                        "Con la tecnología Intel Turbo Boost Max 3.0, la frecuencia máxima de turbo que este procesador puede alcanzar es de 4.9 GHz. Además, este procesador cuenta con 8 núcleos con 6 subprocesos en un zócalo LGA 1151, tiene 12 MB de memoria caché y 16 líneas PCIe. Tener 8 núcleos permite que el procesador ejecute varios programas simultáneamente sin ralentizar el sistema, mientras que los 6 subprocesos permiten que una secuencia de instrucciones ordenada básica pase o sea procesada por un solo núcleo de CPU. Este procesador también admite memoria RAM DDR4-2666 de doble canal y utiliza tecnología de novena generación.",
                "",
                "Especificaciones\n" +
                        "General\n" +
                        "CPU Model Intel Core i7-9700K\n" +
                        "CPU Socket LGA 1151\n" +
                        "Unlocked Yes\n" +
                        "Performance\n" +
                        "Number of Cores 8\n" +
                        "Number of Threads 8\n" +
                        "Base Clock Speed 3.6 GHz\n" +
                        "Maximum Boost Speed 4.9 GHz\n" +
                        "L3 Cache 12 MB\n" +
                        "Memory Support\n" +
                        "Memory Support DDR4 2666 MHz\n" +
                        "Channel Architecture Dual Channel\n" +
                        "Power\n" +
                        "Thermal Design Power (TDP) 95 W\n" +
                        "Thermal Solution None",
                "Intel",
                4.3f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Procesadores%2FIntel%20Core%20i7-9700K%203.6Ghz.jpg?alt=media&token=0ea20d88-99eb-415c-90ef-abce9c424dea",
                true,
                "Procesadores"));

        productList.add(new Product(10,
                "Kingston A400 SSD 240GB",
                31.99f,
                "La unidad A400 de estado sólido de Kingston ofrece enormes mejoras en la velocidad de respuesta, sin actualizaciones adicionales del hardware. Brinda lapsos de arranque, carga y de transferencia de archivos increíblemente más breves en comparación con las unidades de disco duro mecánico.\n" +
                        "\n" +
                        "Apoyada en su controlador de la más reciente generación, que ofrece velocidades de lectura y escritura de hasta 500 MB/s y 450 MB/s, respectivamente, esta unidad SSD es 10 veces más rápida que los discos duros tradicionales y provee un mejor rendimiento, velocidad de respuesta ultrarrápida en el procesamiento multitareas y una aceleración general del sistema. Además son más fiables y duraderas que las unidades de disco duro, y están disponibles en varias capacidades que van de 120 GB hasta 480 GB.",
                "",
                "Características:\n" +
                        "Arranques, cargas y transferencias de archivos todos con mayor rapidez\n" +
                        "Más fiable y duradera que las unidades de disco duro\n" +
                        "Varias capacidades, para almacenar las aplicaciones o sustituir del todo unidades de disco duro.",
                "Kingston",
                4.9f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Discos%20Duros%2F1282720.jpg?alt=media&token=70772f52-954b-4cb5-aa38-47a3254bb58b",
                true,
                "Discos Duros"));

        productList.add(new Product(11,
                "Seagate BarraCuda 3.5\" 1TB SATA3",
                38.73f,
                "Versátiles. Rápidos. Fiables. La unidad de disco duro más increíble que haya conocido.",
                "",
                "Características:\n" +
                        "Versátiles, rápidas y fiables BarraCuda lidera la industria con las capacidades más altas para ordenadores de sobremesa y portátiles. Las unidades de hasta 10 TB hacen que la cartera de productos BarraCuda sea una excelente opción para actualizar su infraestructura tecnológica sea cual sea su presupuesto. La contundente unidad BarraCuda Pro asocia la capacidad de almacenamiento líder del sector con velocidades de giro de 7.200 rpm para conseguir un rendimiento y unos tiempos de carga eficientes al jugar o realizar cargas de trabajo intensas. BarraCuda Pro también cuenta con una garantía limitada de 5 años.\n" +
                        "Impresionante versatilidad Aproveche su almacenamiento al máximo con las unidades de disco duro BarraCuda. BarraCuda crece con usted: para los ordenadores repletos de fotos y recuerdos y para los ordenadores que necesitan más potencia para jugar a videojuegos.\n" +
                        "Las unidades de disco duro de BarraCuda de 3,5 pulgadas ofrecen un gran rendimiento. Su fiabilidad inquebrantable está basada en más de 20 años de innovación BarraCuda. La combinación versátil de capacidad y opciones de niveles de precio hacen que las unidades sean accesibles para cualquier presupuesto. Cuenta con tecnología de almacenamiento en caché de múltiples niveles Multi-Tier Caching Technology™ para ofrecer un rendimiento excelente en sus unidades de disco duro.\n" +
                        "Tecnología de almacenamiento en caché de varios niveles Todas las unidades de disco duro de la familia BarraCuda cuentan con la tecnología Multi-Tier Caching (MTC, por sus siglas en inglés). La tecnología MTC transporta su equipo PC a nuevos niveles de rendimiento, de modo que pueda cargar aplicaciones y archivos más rápido que nunca. Al aplicar capas inteligentes de NAND Flash, tecnologías DRAM y caché, BarraCuda brinda un rendimiento mejorado de operaciones de lectura y escritura al optimizar el flujo de datos.",
                "Seagate",
                3.5f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Discos%20Duros%2Fnew111412.jpg?alt=media&token=fad15fb6-84c5-40b4-a736-2b809c0be804",
                true,
                "Discos Duros"));

        productList.add(new Product(12,
                "Gigabyte GeForce GTX 1660 OC 6GB GDDR5",
                344.90f,
                "Recién llegada, la tarjeta gráfica Gigabyte GeForce GTX 1660 OC es la nueva familia de tarjetas gráficas Nvidia, una potente gráfica que viene para competir con las mejores. Con un gran elenco de novedosas tecnologías, la GTX 1660 se convierte en la puerta de entrada a la realidad virtual y a los gráficos en alta definición.",
                "",
                "Especificaciones Gigabyte GeForce GTX 1660  OC\n" +
                        "Graphics Processing: GeForce® GTX 1660\n" +
                        "Core Clock: 1830 MHz (Reference card is 1785 MHz)\n" +
                        "CUDA® Cores: TBD\n" +
                        "Memory Clock: 8002 MHz\n" +
                        "Memory Size: 6 GB\n" +
                        "Memory Type: GDDR5\n" +
                        "Memory Bus: 192 bit\n" +
                        "Memory Bandwidth (GB/sec): 192 GB/s\n" +
                        "Card Bus: PCI-E 3.0 x 16\n" +
                        "Digital max resolution: 7680x4320@60Hz",
                "Gigabyte",
                4.7f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Tarjetas%20Gr%C3%A1ficas%2F6.jpg?alt=media&token=dd4e2e11-566b-4d95-82c9-eb750cebe8a1",
                true,
                "Tarjetas Gráficas"));

        productList.add(new Product(13,
                "Sapphire Pulse Radeon RX 580 8GB GDDR5",
                195.90f,
                "Te presentamos la tarjeta gráfica Sapphire Pulse Radeon RX 580 8GB GDDR5. SAPPHIRE Pulse tiene como objetivo proporcionar a los usuarios de PC una opción y una gran relación calidad-precio.A partir de la SAPPHIRE Pulse Radeon RX 580, este modelo de overclock de fábrica está dedicado a los clientes que buscan una tarjeta potente, a prueba de futuro que reproduce títulos modernos con máxima fidelidad en 1440p @ 60 FPS.La tarjeta cuenta con una GPU Polaris con generación 2304 4 ª de procesadores de procesamiento de gráficos Core Next, impulsado a 1366 MHz y 8 GB de VRAM.El sistema SAPPHIRE Dual-X, probado en la industria, sirve como solución de refrigeración, con ventiladores de doble cojinete de bolas y la función SAPPHIRE Quick Connect para una fácil limpieza y sustitución.",
                "",
                "Especificaciones Sapphire Pulse Radeon RX 580 8GB GDDR5\n" +
                        "GPU\n" +
                        "2304 Stream Processors\n" +
                        "14 nm FinFET\n" +
                        "4th Graphics Core Next (GCN)\n" +
                        "1366 MHz Reloj del GPU Boost\n" +
                        "Memoria\n" +
                        "256 bit Bus de Memoria\n" +
                        "GDDR5 Tipo de Memoria\n" +
                        "2000 MHz Reloj de la Memoria\n" +
                        "Tamaño 8 GB",
                "Sapphire",
                3.7f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Tarjetas%20Gr%C3%A1ficas%2F1.jpg?alt=media&token=a605c02a-dc57-4477-8008-1c3c60fa83d9",
                true,
                "Tarjetas Gráficas"));

        productList.add(new Product(14,
                "Corsair Vengeance LPX DDR4 3000 PC4-24000 16GB 2x8GB CL15",
                86.42f,
                "La memoria Vengeance LPX se ha diseñado para overclocking de alto rendimiento. El disipador de calor, fabricado en aluminio puro, permite una disipación térmica más rápida; la placa impresa de ocho capas administra el calor y proporciona una capacidad superior para incrementar el overclocking. Cada circuito integrado está seleccionado individualmente para el máximo potencial de rendimiento. El formato DDR4 está optimizado para las placas base Intel de la serie X99 más recientes y ofrece frecuencias más elevadas, mayor ancho de banda y menor consumo energético que los módulos DDR3. Se ha verificado la compatibilidad de los módulos Vengeance LPX DDR4 para toda las placas base de la serie X99, lo que asegura un rendimiento rápido y fiable. Compatibilidad con XMP 2.0 para un overclocking automático sin problemas. Y están disponibles en distintos colores para combinar con su placa base, sus componentes, o sencillamente con su estilo personal.",
                "",
                "Características\n" +
                        "Compatibilidad probada en las placas base de la serie X99/100, lo que asegura un rendimiento rápido y fiable: Parte de nuestro exhaustivo proceso de pruebas incluye la verificación del rendimiento y de la compatibilidad de prácticamente todas las placas base de la serie X99/100 del mercado, y de algunas que no lo son.\n" +
                        "Diseñados para overclocking de alto rendimiento: Los módulos Vengeance LPX están fabricado con placas impresas de ocho capas y circuitos integrados de memoria rigurosamente seleccionados. El eficaz disipador del calor proporciona una refrigeración eficaz para mejorar el potencial de overclocking.\n" +
                        "Disipador de calor de aluminio de puro para una disipación térmica más rápida y funcionamiento a menor temperatura: La sobrecarga del overclocking está limitada por la temperatura de funcionamiento. El diseño único del disipador de calor de Vengeance LPX retira de manera óptima el calor de los circuitos integrados y lo conduce a la trayectoria de refrigeración de su sistema, con lo que puede pedirle todavía más. El disipador de calor no solo hace que la memoria Vengeance LPX funcione mejor... su formato agresivo, y sin embargo refinado, se adapta perfectamente en los sistemas de presentación.",
                "Corsair",
                4.3f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Memoria%20RAM%2Fcorsair-vengeance-lpx-ddr4-3000mhz-pc-24000-8gb-2x4-black.jpg?alt=media&token=8740cc6b-c97f-4a5f-a909-446d1c796542",
                true,
                "Memoria RAM"));

        productList.add(new Product(15,
                "Kingston HyperX Fury Black DDR4 2400 PC4-19200 8GB CL15",
                39.50f,
                "HyperX® FURY DDR4 detecta los componentes de tu sistema y se realiza automáticamente el overclocking a la frecuencia más alta, para lograr un aumento del rendimiento Plug & Play. Esta rentable actualización te permitirá aumentar la configuración de tus favoritos de FPS, MMO y MOBA, así como disfrutar de un mejor rendimiento de renderización y edición de vídeos. HyperX FURY DDR4 se encuentra y permanece fría gracias a su disipador de calor elegante y de bajo perfil y el bajo consumo energético de 1,2 V de la memoria DDR4. Probada al 100% a elevadas velocidades, la memoria HyperX FURY DDR4 es tu opción de actualización sin preocupaciones.",
                "",
                "Características\n" +
                        "Overclocking automático. Saca el máximo partido a tu nueva memoria desde el principio gracias al overclocking automático Plug & Play de la memoria FURY DDR4.\n" +
                        "Compatibilidad con Intel XMP. Basta con realizar el overclocking de los módulos seleccionando un perfil, sin tener que hacer ajustes manuales de tiempos en la BIOS.\n" +
                        "DDR4 de consumo energético reducido. La baja tensión estándar de 1,2 V de la DDR4 significa que funcionará de forma fría y eficiente.\n" +
                        "Diseño FURY. Diseño asimétrico exclusivo con un disipador de calor de bajo perfil.",
                "Kingston",
                3.5f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Memoria%20RAM%2Fkingston-hyperx-fury-ddr4-2133-pc4-17000-4gb-cl14.jpg?alt=media&token=4a9fb3b2-113d-44d4-826b-439f2a1c1447",
                true,
                "Memoria RAM"));

        productList.add(new Product(16,
                "Creative Sound Blaster Audigy FX PCI Express",
                31.00f,
                "Tarjeta de sonido PCIe con SBX Pro Studio\n" +
                        "\n" +
                        "Sound Blaster Audigy Fx es la actualización perfecta desde el audio básico de la placa base al legendario sonido de Sound Blaster. Optimizada con la tecnología SBX Pro Studio, ofrece sonido cinemático 5.1 de gran alta calidad para películas, música y juegos. Incluye el Panel de control de Sound Blaster Audigy Fx, que brinda el control absoluto de la configuración de tu SBX Pro Studio.",
                "",
                "Características\n" +
                        "\n" +
                        "La actualización perfecta de Sound Blaster\n" +
                        "Sound Blaster Audigy Fx es una tarjeta de sonido de media altura basada en la tecnología SBX Pro Studio. De forma instantánea, convierte tu sistema en un sistema de entretenimiento 5.1 que ofrece sonido envolvente cinemático. ¡Perfecto para sacar el máximo partido a tus películas, canciones y juegos! Sound Blaster Audigy Fx también incorpora un conversor de digital a analógico (DAC) de 192 kHz (reproducción) de 24 bits, una relación de señal-ruido (SNR) de 106 dB y un amplificador de auriculares de 600 ohmios de alta calidad para una supervisión de excelente calidad.\n" +
                        "Disfruta del sonido cinemático multicanal\n" +
                        "La tarjeta de sonido PCIe de media altura es del tamaño perfecto para tus PCs de cine en casa. Ahora puedes actualizar tu experiencia cinemática con la fidelidad de audio de Sound Blaster. Sound Blaster Audigy Fx incluye salidas de canal 5.1 para la conexión sencilla a los altavoces multicanal existentes.\n" +
                        "Línea independiente y entradas de micrófono\n" +
                        "Sound Blaster Audigy Fx incorpora conectores independientes de entrada de línea y micrófono, que permiten la conexión de dos fuentes diferentes de audio a tu PC.\n" +
                        "Puedes conectar el reproductor de MP3 y cantar, ¡a la vez que grabas tu sesión de canto para compartirla con tus amigos!\n" +
                        "Controla y personaliza tu audio\n" +
                        "Sound Blaster Audigy Fx incluye el conjunto de tecnologías SBX Pro Studio, ¡diseñado para marcar la diferencia cinemática en los PC de cine en casa! También puedes ajustar el nivel de inmersión que desees, simplemente adaptando los deslizadores del Panel de control Audigy Fx.",
                "Creative",
                2.7f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Tarjetas%20de%20Sonido%2Fcreative-sound-blaster-audigy-fx-pci-express.jpg?alt=media&token=f19092c4-a7a8-49bd-b183-42fde8ac0d92",
                true,
                "Tarjetas de Sonido"));


        productList.add(new Product(17,
                "Creative Sound Blaster Z 5.1 PCIe",
                76.20f,
                "El sistema de audio completo para tu escritorio: Sound Blaster Z\n" +
                        "\n" +
                        "Sound Blaster Z, que forma parte de las tarjeta de sonido de alto rendimiento de la serie Z de Sound Blaster® PCI-Express, es una solución para juegos y entretenimiento global que incorpora las funciones de la tarjeta Sound Blaster Zx e incluye un conjunto de micrófono de formación de haces de calidad. ",
                "",
                "Características:\n" +
                        "SNR de 116 dB. Una relación señal/ruido (o SNR) de 116 dB significa que el audio será un 99,99% puro, 34,4 veces mejor que el audio de la placa base.\n" +
                        "Tecnologías SBX Pro Studio. Las tecnologías de sonido SBX Pro Studio™ reproducen el sonido con un nivel de realismo sin precedentes, con increíbles efectos envolventes 3D para altavoces y auriculares.\n" +
                        "Tecnología CrystalVoice Voz mejorada y más clara. Exprésate y deja que te oigan de forma nítida en conferencias de vídeo, juegos multijugador y chats en línea.\n" +
                        "Comunicaciones nítidas. El micrófono Sound Blaster con formación de haces está diseñado y preparado para llamadas de voz manos libres. Utilizado con CrystalVoice™ Focus, este micrófono estéreo crea una zona acústica y suprime el ruido ambiental para que se te oiga con una claridad asombrosa.\n" +
                        "Procesador de audio Sound Core3D. Sound Core3D™ es un procesador de voz y sonido de alto rendimiento para acelerar las tecnologías de audio y voz avanzadas. Hemos diseñado el procesador de audio de núcleo cuádruple Sound Core3D™ para descargar los efectos de SBX® Pro Studio Pro™ y CrystalVoice™ de la unidad CPU. Esto te permitirá jugar mejor, en una velocidad de fotogramas mayor y con una calidad de audio y voz superior.\n" +
                        "Cambio de auricular a altavoz. Ya no tendrás que volver a desconectar los auriculares. Con el Panel de control de Sound Blaster, pasa de auricular a altavoz en un cambio rápido.\n" +
                        "Dolby® Digital Live y DTS Connect. Conéctalo al decodificador o al sistema de cine en casa a través de un único cable digital y disfruta de un sonido envolvente 5.1 óptimo desde cualquier fuente.\n" +
                        "No vuelva a desconectar los auriculares. Con el Panel de Control de las series Sound Blaster Z, podrá alternar entre los auriculares y los altavoces con un sólo toque de botón.",
                "Creative",
                4.3f,
                "https://firebasestorage.googleapis.com/v0/b/proyectofinal-5e356.appspot.com/o/Tarjetas%20de%20Sonido%2Fcreative-sound-blaster-z.jpg?alt=media&token=6ea4971e-6f5e-4449-848d-17e527f18608",
                true,
                "Tarjetas de Sonido"));




        for(int i = 0; i < productList.size(); i++)
        {
            DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
            productsRef.child(String.valueOf(productList.get(i).getId())).setValue(productList.get(i));
        }
    }
}